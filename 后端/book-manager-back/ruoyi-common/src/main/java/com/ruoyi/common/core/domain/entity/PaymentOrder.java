package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 支付订单对象 tb_payment_order
 *
 * @author ruoyi
 */
public class PaymentOrder
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 罚款ID */
    private Long fineId;

    /** 支付金额 */
    private BigDecimal amount;

    /** 支付方式 */
    private String payMethod;

    /** 支付状态（0待支付 1已支付 2已关闭） */
    private String payStatus;

    /** 交易流水号 */
    private String tradeNo;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    public PaymentOrder()
    {
    }

    public PaymentOrder(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getFineId()
    {
        return fineId;
    }

    public void setFineId(Long fineId)
    {
        this.fineId = fineId;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public String getPayMethod()
    {
        return payMethod;
    }

    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public String getPayStatus()
    {
        return payStatus;
    }

    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }

    public String getTradeNo()
    {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
            .append("orderId", getOrderId())
            .append("fineId", getFineId())
            .append("amount", getAmount())
            .append("payMethod", getPayMethod())
            .append("payStatus", getPayStatus())
            .append("tradeNo", getTradeNo())
            .append("createTime", getCreateTime())
            .append("payTime", getPayTime())
            .toString();
    }
}
