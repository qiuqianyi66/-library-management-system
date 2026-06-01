package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.PaymentOrder;
import org.springframework.stereotype.Repository;

/**
 * 支付订单 数据层
 *
 * @author ruoyi
 */
@Repository
public interface PaymentOrderMapper
{
    /**
     * 新增支付订单
     *
     * @param paymentOrder 支付订单信息
     * @return 结果
     */
    public int insertOrder(PaymentOrder paymentOrder);

    /**
     * 修改支付订单
     *
     * @param paymentOrder 支付订单信息
     * @return 结果
     */
    public int updateOrder(PaymentOrder paymentOrder);

    /**
     * 通过订单ID查询支付订单
     *
     * @param orderId 订单ID
     * @return 支付订单信息
     */
    public PaymentOrder selectByOrderId(Long orderId);

    /**
     * 通过罚款ID查询支付订单列表
     *
     * @param fineId 罚款ID
     * @return 支付订单列表
     */
    public List<PaymentOrder> selectByFineId(Long fineId);
}
