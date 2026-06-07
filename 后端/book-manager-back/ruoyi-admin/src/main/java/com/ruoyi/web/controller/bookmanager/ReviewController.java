package com.ruoyi.web.controller.bookmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.BookReview;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ReviewService;

/**
 * 图书评论管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/bookmanager/review")
public class ReviewController extends BaseController
{
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/list")
    public TableDataInfo list(BookReview review)
    {
        startPage();
        List<BookReview> list = reviewService.selectReviewList(review);
        return getDataTable(list);
    }

    @GetMapping(value = { "/", "/{reviewId}" })
    public AjaxResult getInfo(@PathVariable(value = "reviewId", required = false) Long reviewId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(reviewId))
        {
            BookReview review = reviewService.selectReviewById(reviewId);
            ajax.put(AjaxResult.DATA_TAG, review);
        }
        return ajax;
    }

    @PostMapping
    public AjaxResult add(@RequestBody BookReview review)
    {
        review.setCreateBy(getUsername());
        return toAjax(reviewService.insertReview(review));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody BookReview review)
    {
        review.setUpdateBy(getUsername());
        return toAjax(reviewService.updateReview(review));
    }

    @DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable Long[] reviewIds)
    {
        return toAjax(reviewService.deleteReviewByIds(reviewIds));
    }
}
