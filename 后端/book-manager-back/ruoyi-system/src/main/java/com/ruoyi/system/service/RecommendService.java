package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

/**
 * 图书推荐 业务层
 *
 * @author ruoyi
 */
public interface RecommendService
{
    /**
     * 基于协同过滤：猜你喜欢（Item-based）
     * 根据读者借阅历史推荐图书
     */
    public List<Map<String, Object>> recommendForReader(Integer readerId);

    /**
     * 看过这本书的人也看过
     */
    public List<Map<String, Object>> recommendByBook(Integer bookId, Integer readerId);

    /**
     * 热门推荐（读者未借过的）
     */
    public List<Map<String, Object>> recommendHotUnborrowed(Integer readerId);
}
