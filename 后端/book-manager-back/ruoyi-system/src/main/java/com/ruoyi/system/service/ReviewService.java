package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.BookReview;

/**
 * 图书评论 业务层
 *
 * @author ruoyi
 */
public interface ReviewService
{
    /**
     * 分页查询评论列表
     */
    public List<BookReview> selectReviewList(BookReview review);

    /**
     * 通过评论ID查询
     */
    public BookReview selectReviewById(Long reviewId);

    /**
     * 查询某本书的评论列表
     */
    public List<BookReview> selectReviewByBookId(Long bookId);

    /**
     * 查询某本书的平均评分
     */
    public Double selectAvgRatingByBookId(Long bookId);

    /**
     * 查询某本书的评分人数
     */
    public Integer selectRatingCountByBookId(Long bookId);

    /**
     * 新增评论
     */
    public int insertReview(BookReview review);

    /**
     * 修改评论
     */
    public int updateReview(BookReview review);

    /**
     * 批量删除评论
     */
    public int deleteReviewByIds(Long[] reviewIds);
}
