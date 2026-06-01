package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 借阅记录对象 tb_borrow_record
 *
 * @author ruoyi
 */
public class BorrowRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 借阅ID */
    private Long borrowId;

    /** 读者ID */
    @Excel(name = "读者编号")
    private Integer readerId;

    /** 图书ID */
    @Excel(name = "图书编号")
    private Integer bookId;

    /** 借阅日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借阅日期", dateFormat = "yyyy-MM-dd")
    private Date borrowDate;

    /** 应还日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "应还日期", dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 实际归还日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还日期", dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** 借阅状态（borrowing-借阅中 returned-已归还 overdue-已逾期） */
    @Excel(name = "借阅状态", readConverterExp = "borrowing=借阅中,returned=已归还,overdue=已逾期")
    private String status;

    /** 续借次数 */
    @Excel(name = "续借次数")
    private Integer renewCount;

    /** 罚款金额 */
    @Excel(name = "罚款金额")
    private BigDecimal fineAmount;

    /** 读者姓名（非数据库字段） */
    private String readerName;

    /** 图书名称（非数据库字段） */
    private String bookName;

    public BorrowRecord()
    {
    }

    public BorrowRecord(Long borrowId)
    {
        this.borrowId = borrowId;
    }

    public Long getBorrowId()
    {
        return borrowId;
    }

    public void setBorrowId(Long borrowId)
    {
        this.borrowId = borrowId;
    }

    public Integer getReaderId()
    {
        return readerId;
    }

    public void setReaderId(Integer readerId)
    {
        this.readerId = readerId;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId(Integer bookId)
    {
        this.bookId = bookId;
    }

    public Date getBorrowDate()
    {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate)
    {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getReturnDate()
    {
        return returnDate;
    }

    public void setReturnDate(Date returnDate)
    {
        this.returnDate = returnDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getRenewCount()
    {
        return renewCount;
    }

    public void setRenewCount(Integer renewCount)
    {
        this.renewCount = renewCount;
    }

    public BigDecimal getFineAmount()
    {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount)
    {
        this.fineAmount = fineAmount;
    }

    public String getReaderName()
    {
        return readerName;
    }

    public void setReaderName(String readerName)
    {
        this.readerName = readerName;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("borrowId", getBorrowId())
            .append("readerId", getReaderId())
            .append("bookId", getBookId())
            .append("borrowDate", getBorrowDate())
            .append("dueDate", getDueDate())
            .append("returnDate", getReturnDate())
            .append("status", getStatus())
            .append("renewCount", getRenewCount())
            .append("fineAmount", getFineAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
