package com.ruoyi.web.controller.portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.BookReview;
import com.ruoyi.system.service.ReviewService;

/**
 * 门户 - 图书评分与评论
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/portal/review")
public class PortalReviewController extends BaseController
{
    @Autowired
    private ReviewService reviewService;

    /**
     * 获取某本书的评论列表 + 评分统计
     */
    @GetMapping("/book/{bookId}")
    public AjaxResult getBookReviews(@PathVariable Long bookId)
    {
        List<BookReview> reviews = reviewService.selectReviewByBookId(bookId);
        Double avgRating = reviewService.selectAvgRatingByBookId(bookId);
        Integer ratingCount = reviewService.selectRatingCountByBookId(bookId);

        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        result.put("avgRating", avgRating);
        result.put("ratingCount", ratingCount);
        return AjaxResult.success(result);
    }

    /**
     * 提交评论
     */
    @PostMapping("/submit")
    public AjaxResult submitReview(@RequestBody BookReview review)
    {
        Long userId = getUserId();
        review.setReaderId(userId.intValue());
        review.setCreateBy(getUsername());
        review.setStatus("1");
        return toAjax(reviewService.insertReview(review));
    }
}
