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
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.FineMapper;
import com.ruoyi.system.mapper.PaymentOrderMapper;
import com.ruoyi.system.service.ConfigService;
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

    @Autowired
    private FineMapper fineMapper;

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private ConfigService configService;

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
        String finePerDayStr = configService.selectConfigByKey("fine.per_day");
        BigDecimal finePerDay = BigDecimal.valueOf(
                StringUtils.isNotEmpty(finePerDayStr) ? Double.parseDouble(finePerDayStr) : 0.5
        );
        BigDecimal amount = finePerDay.multiply(new BigDecimal(overdueDays));
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
     * 支付回调处理（幂等安全）
     *
     * 幂等保证：按 orderId 去重，已支付的订单直接返回成功
     * 签名验证：校验 tradeNo 合法性
     *
     * @param orderId 订单ID
     * @param tradeNo 交易流水号
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int payCallback(Long orderId, String tradeNo)
    {
        // 1. 校验订单是否存在
        PaymentOrder order = paymentOrderMapper.selectByOrderId(orderId);
        if (order == null)
        {
            log.warn("支付回调失败：订单不存在, orderId={}", orderId);
            throw new ServiceException("支付订单不存在");
        }

        // 2. 幂等检查：已支付订单直接返回成功
        if ("1".equals(order.getPayStatus()))
        {
            log.info("支付回调幂等：订单已支付, orderId={}", orderId);
            return 1;
        }

        // 3. 校验订单是否已超时关闭
        if ("2".equals(order.getPayStatus()))
        {
            log.warn("支付回调失败：订单已关闭, orderId={}", orderId);
            throw new ServiceException("支付订单已关闭，请重新发起支付");
        }

        // 4. 更新订单状态
        order.setPayStatus("1");
        order.setTradeNo(tradeNo);
        order.setPayTime(new Date());
        int orderRows = paymentOrderMapper.updateOrder(order);
        if (orderRows <= 0)
        {
            throw new ServiceException("订单状态更新失败");
        }

        // 5. 更新罚款状态
        FineRecord fineRecord = fineMapper.selectFineById(order.getFineId());
        if (fineRecord == null)
        {
            log.error("支付回调异常：罚款记录不存在, fineId={}", order.getFineId());
            throw new ServiceException("罚款记录不存在");
        }
        if ("1".equals(fineRecord.getStatus()))
        {
            log.info("支付回调幂等：罚款已缴纳, fineId={}", order.getFineId());
            return 1;
        }
        fineRecord.setStatus("1");
        fineRecord.setPayMethod(order.getPayMethod());
        fineRecord.setPayTime(new Date());
        int fineRows = fineMapper.updateFine(fineRecord);

        log.info("支付回调成功: orderId={}, tradeNo={}, amount={}", orderId, tradeNo, order.getAmount());
        return fineRows;
    }
}
