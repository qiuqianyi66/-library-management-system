package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.mapper.BorrowMapper;
import com.ruoyi.system.service.RecommendService;

/**
 * 图书推荐 业务层处理
 * 基于协同过滤（Item-based Collaborative Filtering）
 *
 * @author ruoyi
 */
@Service
public class RecommendServiceImpl implements RecommendService
{
    private static final Logger log = LoggerFactory.getLogger(RecommendServiceImpl.class);

    private static final String RECOMMEND_CACHE_KEY = "recommend:reader:";
    private static final String CO_BORROW_CACHE_KEY = "recommend:co:";
    private static final long CACHE_TTL = 30; // 分钟

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Map<String, Object>> recommendForReader(Integer readerId)
    {
        String cacheKey = RECOMMEND_CACHE_KEY + readerId;
        List<Map<String, Object>> cached = redisCache.getCacheObject(cacheKey);
        if (cached != null && !cached.isEmpty())
        {
            return cached;
        }

        // 1. 获取读者借过的书
        List<Integer> bookIds = borrowMapper.selectBookIdsByReaderId(readerId);
        if (bookIds == null || bookIds.isEmpty())
        {
            // 新读者 → 推荐热门
            return recommendHotUnborrowed(readerId);
        }

        // 2. 对每本借过的书做协同过滤，合并结果
        java.util.Map<Integer, Integer> scoreMap = new java.util.HashMap<>();
        for (Integer bookId : bookIds)
        {
            List<Map<String, Object>> coBorrowed = borrowMapper.selectCoBorrowedBooks(bookId, readerId);
            for (Map<String, Object> item : coBorrowed)
            {
                Integer bid = ((Number) item.get("book_id")).intValue();
                Integer score = ((Number) item.get("score")).intValue();
                scoreMap.merge(bid, score, Integer::sum);
            }
        }

        // 3. 按总分排序取前6
        List<Map<String, Object>> result = new ArrayList<>();
        scoreMap.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(6)
            .forEach(entry -> {
                java.util.Map<String, Object> map = new java.util.HashMap<>();
                map.put("book_id", entry.getKey());
                map.put("score", entry.getValue());
                result.add(map);
            });

        // 4. 补充图书信息
        for (Map<String, Object> item : result)
        {
            Integer bid = ((Number) item.get("book_id")).intValue();
            com.ruoyi.system.mapper.BookMapper bookMapper =
                com.ruoyi.common.utils.spring.SpringUtils.getBean(com.ruoyi.system.mapper.BookMapper.class);
            com.ruoyi.common.core.domain.entity.Book book = bookMapper.selectBookById(bid.longValue());
            if (book != null)
            {
                item.put("book_name", book.getBookName());
                item.put("author", book.getAuthor());
                item.put("press", book.getPress());
            }
        }

        // 缓存结果
        redisCache.setCacheObject(cacheKey, result, CACHE_TTL, TimeUnit.MINUTES);

        return result;
    }

    @Override
    public List<Map<String, Object>> recommendByBook(Integer bookId, Integer readerId)
    {
        String cacheKey = CO_BORROW_CACHE_KEY + bookId + ":" + readerId;
        List<Map<String, Object>> cached = redisCache.getCacheObject(cacheKey);
        if (cached != null && !cached.isEmpty())
        {
            return cached;
        }

        List<Map<String, Object>> result = borrowMapper.selectCoBorrowedBooks(bookId, readerId);
        redisCache.setCacheObject(cacheKey, result, CACHE_TTL, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public List<Map<String, Object>> recommendHotUnborrowed(Integer readerId)
    {
        return borrowMapper.selectUnborrowedHotBooks(readerId);
    }
}
