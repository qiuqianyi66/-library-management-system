package com.ruoyi.web.controller.portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.mapper.BorrowMapper;

/**
 * 门户 - 个人阅读报告
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/portal/stats")
public class ReaderStatsController extends BaseController
{
    @Autowired
    private BorrowMapper borrowMapper;

    /**
     * 读者个人借阅统计概览
     */
    @GetMapping("/mySummary")
    public AjaxResult mySummary()
    {
        Integer readerId = getUserId().intValue();

        List<BorrowRecord> records = borrowMapper.selectBorrowList(new BorrowRecord() {{
            setReaderId(readerId);
        }});

        int totalCount = 0, currentCount = 0, overdueCount = 0, returnedCount = 0;
        for (BorrowRecord r : records)
        {
            totalCount++;
            if ("borrowing".equals(r.getStatus())) currentCount++;
            else if ("overdue".equals(r.getStatus())) { overdueCount++; currentCount++; }
            else if ("returned".equals(r.getStatus())) returnedCount++;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalCount", totalCount);
        result.put("currentCount", currentCount);
        result.put("overdueCount", overdueCount);
        result.put("returnedCount", returnedCount);

        return AjaxResult.success(result);
    }

    /**
     * 读者月度借阅趋势
     */
    @GetMapping("/myMonthlyTrend")
    public AjaxResult myMonthlyTrend()
    {
        Integer readerId = getUserId().intValue();

        BorrowRecord query = new BorrowRecord();
        query.setReaderId(readerId);
        query.getParams().put("beginTime", DateUtils.dateTime(
                org.apache.commons.lang3.time.DateUtils.addDays(new java.util.Date(), -30)));

        List<Map<String, Object>> trend = borrowMapper.selectReaderBorrowTrend(query);
        return AjaxResult.success(trend);
    }

    /**
     * 读者阅读偏好（按图书类型统计）
     */
    @GetMapping("/myTypePreference")
    public AjaxResult myTypePreference()
    {
        Integer readerId = getUserId().intValue();
        List<Map<String, Object>> preferences = borrowMapper.selectReaderTypePreference(readerId);
        return AjaxResult.success(preferences);
    }
}
