package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 罚款记录对象 tb_fine_record
 *
 * @author ruoyi
 */
public class FineRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 罚款ID */
    private Long fineId;

    /** 借阅ID */
    @Excel(name = "借阅ID")
    private Long borrowId;

    /** 读者ID */
    @Excel(name = "读者ID")
    private Integer readerId;

    /** 罚款金额 */
    @Excel(name = "罚款金额")
    private BigDecimal amount;

    /** 罚款状态（0未缴纳 1已缴纳） */
    @Excel(name = "罚款状态", readConverterExp = "0=未缴纳,1=已缴纳")
    private String status;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String payMethod;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    public FineRecord()
    {
    }

    public FineRecord(Long fineId)
    {
        this.fineId = fineId;
    }

    public Long getFineId()
    {
        return fineId;
    }

    public void setFineId(Long fineId)
    {
        this.fineId = fineId;
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

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getPayMethod()
    {
        return payMethod;
    }

    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public Date getPayTime()
    {
        return payTime;
    }

    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this, org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE)
            .append("fineId", getFineId())
            .append("borrowId", getBorrowId())
            .append("readerId", getReaderId())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("payMethod", getPayMethod())
            .append("payTime", getPayTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
