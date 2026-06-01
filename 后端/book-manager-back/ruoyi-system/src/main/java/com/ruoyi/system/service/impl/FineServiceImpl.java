package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.FineRecord;
import com.ruoyi.common.core.domain.entity.PaymentOrder;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.mapper.FineMapper;
import com.ruoyi.system.mapper.PaymentOrderMapper;
import com.ruoyi.system.service.FineService;

/**
 * 罚款 业务层处理
 *
 * @author ruoyi
 */
@Service
public class FineServiceImpl implements FineService
{
    private static final Logger log = LoggerFactory.getLogger(FineServiceImpl.class);

    private static final BigDecimal DAILY_FINE_AMOUNT = new BigDecimal("0.50");

    @Autowired
    private FineMapper fineMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Override
    public List<FineRecord> selectFineList(FineRecord fineRecord)
    {
        return fineMapper.selectFineList(fineRecord);
    }

    @Override
    public FineRecord selectFineById(Long fineId)
    {
        return fineMapper.selectFineById(fineId);
    }

    @Override
    public List<FineRecord> selectFineByReaderId(Integer readerId)
    {
        return fineMapper.selectFineByReaderId(readerId);
    }

    @Override
    @Transactional
    public int insertFine(FineRecord fineRecord)
    {
        return fineMapper.insertFine(fineRecord);
    }

    @Override
    @Transactional
    public int updateFine(FineRecord fineRecord)
    {
        return fineMapper.updateFine(fineRecord);
    }

    /**
     * 计算罚款金额
     * 根据逾期天数 × 每日罚款金额计算
     *
     * @param borrowId 借阅ID
     * @param readerId 读者ID
     * @param overdueDays 逾期天数
     * @return 罚款记录
     */
    @Override
    @Transactional
    public FineRecord calculateFine(Long borrowId, Integer readerId, int overdueDays)
    {
        if (overdueDays <= 0)
        {
            throw new ServiceException("逾期天数必须大于0");
        }
        BigDecimal amount = DAILY_FINE_AMOUNT.multiply(new BigDecimal(overdueDays));
        FineRecord fineRecord = new FineRecord();
        fineRecord.setBorrowId(borrowId);
        fineRecord.setReaderId(readerId);
        fineRecord.setAmount(amount);
        fineRecord.setStatus("0");
        fineMapper.insertFine(fineRecord);
        return fineRecord;
    }

    /**
     * 创建支付订单，生成唯一订单号
     *
     * @param fineId 罚款ID
     * @param payMethod 支付方式
     * @return 支付订单
     */
    @Override
    @Transactional
    public PaymentOrder createPaymentOrder(Long fineId, String payMethod)
    {
        FineRecord fineRecord = fineMapper.selectFineById(fineId);
        if (fineRecord == null)
        {
            throw new ServiceException("罚款记录不存在");
        }
        if ("1".equals(fineRecord.getStatus()))
        {
            throw new ServiceException("该罚款已缴纳");
        }
        PaymentOrder order = new PaymentOrder();
        order.setFineId(fineId);
        order.setAmount(fineRecord.getAmount());
        order.setPayMethod(payMethod);
        order.setPayStatus("0");
        order.setTradeNo("TR" + IdUtils.fastSimpleUUID());
        paymentOrderMapper.insertOrder(order);
        return order;
    }

    /**
     * 支付回调处理，更新罚款状态和订单状态
     *
     * @param orderId 订单ID
     * @param tradeNo 交易流水号
     * @return 结果
     */
    @Override
    @Transactional
    public int payCallback(Long orderId, String tradeNo)
    {
        PaymentOrder order = paymentOrderMapper.selectByOrderId(orderId);
        if (order == null)
        {
            throw new ServiceException("支付订单不存在");
        }
        if ("1".equals(order.getPayStatus()))
        {
            throw new ServiceException("订单已支付，请勿重复操作");
        }
        order.setPayStatus("1");
        order.setTradeNo(tradeNo);
        order.setPayTime(new Date());
        paymentOrderMapper.updateOrder(order);

        FineRecord fineRecord = fineMapper.selectFineById(order.getFineId());
        if (fineRecord == null)
        {
            throw new ServiceException("罚款记录不存在");
        }
        fineRecord.setStatus("1");
        fineRecord.setPayMethod(order.getPayMethod());
        fineRecord.setPayTime(new Date());
        return fineMapper.updateFine(fineRecord);
    }
}
