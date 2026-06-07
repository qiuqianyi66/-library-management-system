package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.BookReview;
import com.ruoyi.system.mapper.ReviewMapper;
import com.ruoyi.system.service.ReviewService;

/**
 * 图书评论 业务层处理
 *
 * @author ruoyi
 */
@Service
public class ReviewServiceImpl implements ReviewService
{
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<BookReview> selectReviewList(BookReview review)
    {
        return reviewMapper.selectReviewList(review);
    }

    @Override
    public BookReview selectReviewById(Long reviewId)
    {
        return reviewMapper.selectReviewById(reviewId);
    }

    @Override
    public List<BookReview> selectReviewByBookId(Long bookId)
    {
        return reviewMapper.selectReviewByBookId(bookId);
    }

    @Override
    public Double selectAvgRatingByBookId(Long bookId)
    {
        return reviewMapper.selectAvgRatingByBookId(bookId);
    }

    @Override
    public Integer selectRatingCountByBookId(Long bookId)
    {
        return reviewMapper.selectRatingCountByBookId(bookId);
    }

    @Override
    @Transactional
    public int insertReview(BookReview review)
    {
        return reviewMapper.insertReview(review);
    }

    @Override
    @Transactional
    public int updateReview(BookReview review)
    {
        return reviewMapper.updateReview(review);
    }

    @Override
    @Transactional
    public int deleteReviewByIds(Long[] reviewIds)
    {
        return reviewMapper.deleteReviewByIds(reviewIds);
    }
}
