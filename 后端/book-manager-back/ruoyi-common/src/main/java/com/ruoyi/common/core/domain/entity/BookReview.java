package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书评论对象 tb_book_review
 *
 * @author ruoyi
 */
public class BookReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long reviewId;

    /** 图书ID */
    @Excel(name = "图书ID")
    private Long bookId;

    /** 读者ID */
    @Excel(name = "读者ID")
    private Integer readerId;

    /** 评分(1-5) */
    @Excel(name = "评分")
    private Integer rating;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    /** 状态（0隐藏 1显示） */
    @Excel(name = "状态", readConverterExp = "0=隐藏,1=显示")
    private String status;

    /** 图书名称（非数据库字段） */
    private String bookName;

    /** 读者姓名（非数据库字段） */
    private String readerName;

    public BookReview()
    {
    }

    public BookReview(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId()
    {
        return reviewId;
    }

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Long getBookId()
    {
        return bookId;
    }

    public void setBookId(Long bookId)
    {
        this.bookId = bookId;
    }

    public Integer getReaderId()
    {
        return readerId;
    }

    public void setReaderId(Integer readerId)
    {
        this.readerId = readerId;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getReaderName()
    {
        return readerName;
    }

    public void setReaderName(String readerName)
    {
        this.readerName = readerName;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE)
            .append("reviewId", getReviewId())
            .append("bookId", getBookId())
            .append("readerId", getReaderId())
            .append("rating", getRating())
            .append("content", getContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
