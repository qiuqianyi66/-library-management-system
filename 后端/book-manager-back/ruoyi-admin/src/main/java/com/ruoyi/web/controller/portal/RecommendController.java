package com.ruoyi.web.controller.portal;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.RecommendService;

/**
 * 门户 - 智能图书推荐
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/portal/recommend")
public class RecommendController extends BaseController
{
    @Autowired
    private RecommendService recommendService;

    /**
     * 猜你喜欢（基于当前登录读者的借阅历史）
     */
    @GetMapping("/forMe")
    public AjaxResult recommendForMe()
    {
        Integer readerId = getUserId().intValue();
        List<Map<String, Object>> list = recommendService.recommendForReader(readerId);
        return AjaxResult.success(list);
    }

    /**
     * 看过这本书的人也看过
     */
    @GetMapping("/byBook/{bookId}")
    public AjaxResult recommendByBook(@PathVariable Integer bookId)
    {
        Integer readerId = getUserId().intValue();
        List<Map<String, Object>> list = recommendService.recommendByBook(bookId, readerId);
        return AjaxResult.success(list);
    }

    /**
     * 热门推荐（读者未借过的）
     */
    @GetMapping("/hot")
    public AjaxResult recommendHot()
    {
        Integer readerId = getUserId().intValue();
        List<Map<String, Object>> list = recommendService.recommendHotUnborrowed(readerId);
        return AjaxResult.success(list);
    }
}
