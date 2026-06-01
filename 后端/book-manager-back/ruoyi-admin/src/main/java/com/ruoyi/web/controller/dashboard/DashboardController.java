package com.ruoyi.web.controller.dashboard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.mapper.BorrowMapper;
import com.ruoyi.system.mapper.FineMapper;

/**
 * 仪表盘数据
 *
 * @author ruoyi
 */
@Anonymous
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseController
{
    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private FineMapper fineMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取聚合统计数据
     */
    @GetMapping("/stats")
    public AjaxResult stats()
    {
        Map<String, Object> result = new HashMap<>();
        result.put("todayBorrowCount", borrowMapper.selectTodayBorrowCount());
        result.put("todayReturnCount", borrowMapper.selectTodayReturnCount());
        result.put("currentBorrowCount", borrowMapper.selectCurrentBorrowCount());
        result.put("overdueCount", borrowMapper.selectOverdueCount());
        result.put("monthlyFineIncome", fineMapper.selectMonthlyFineIncome());
        return AjaxResult.success(result);
    }

    /**
     * 获取近7天借阅趋势
     */
    @GetMapping("/borrowTrend")
    public AjaxResult borrowTrend()
    {
        List<Map<String, Object>> list = borrowMapper.selectBorrowTrendByDays(7);
        return AjaxResult.success(list);
    }

    /**
     * 获取热门图书TOP10
     */
    @GetMapping("/hotBooks")
    public AjaxResult hotBooks()
    {
        List<Map<String, Object>> hotBooks = redisCache.getCacheObject(CacheConstants.HOT_BOOK_KEY);
        if (hotBooks == null || hotBooks.isEmpty())
        {
            hotBooks = borrowMapper.selectHotBooksTop10();
            redisCache.setCacheObject(CacheConstants.HOT_BOOK_KEY, hotBooks, 30, TimeUnit.MINUTES);
        }
        return AjaxResult.success(hotBooks);
    }

    /**
     * 获取本月罚款收入统计
     */
    @GetMapping("/incomeStats")
    public AjaxResult incomeStats()
    {
        BigDecimal income = fineMapper.selectMonthlyFineIncome();
        Map<String, Object> result = new HashMap<>();
        result.put("monthlyFineIncome", income);
        return AjaxResult.success(result);
    }
}
