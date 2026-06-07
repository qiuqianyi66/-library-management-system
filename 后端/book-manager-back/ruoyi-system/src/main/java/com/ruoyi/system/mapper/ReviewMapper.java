package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.BookReview;
import org.springframework.stereotype.Repository;

/**
 * 图书评论 数据层
 *
 * @author ruoyi
 */
@Repository
public interface ReviewMapper
{
    /**
     * 根据条件分页查询评论列表
     */
    public List<BookReview> selectReviewList(BookReview review);

    /**
     * 通过评论ID查询评论
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
     * 删除评论
     */
    public int deleteReviewById(Long reviewId);

    /**
     * 批量删除评论
     */
    public int deleteReviewByIds(Long[] reviewIds);
}
