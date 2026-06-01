package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.FineRecord;
import com.ruoyi.common.core.domain.entity.PaymentOrder;

/**
 * 罚款 业务层
 *
 * @author ruoyi
 */
public interface FineService
{
    /**
     * 根据条件分页查询罚款列表
     *
     * @param fineRecord 罚款信息
     * @return 罚款信息集合
     */
    public List<FineRecord> selectFineList(FineRecord fineRecord);

    /**
     * 通过罚款ID查询罚款记录
     *
     * @param fineId 罚款ID
     * @return 罚款对象信息
     */
    public FineRecord selectFineById(Long fineId);

    /**
     * 通过读者ID查询罚款记录
     *
     * @param readerId 读者ID
     * @return 罚款信息集合
     */
    public List<FineRecord> selectFineByReaderId(Integer readerId);

    /**
     * 新增罚款记录
     *
     * @param fineRecord 罚款信息
     * @return 结果
     */
    public int insertFine(FineRecord fineRecord);

    /**
     * 修改罚款记录
     *
     * @param fineRecord 罚款信息
     * @return 结果
     */
    public int updateFine(FineRecord fineRecord);

    /**
     * 计算罚款金额
     *
     * @param borrowId 借阅ID
     * @param overdueDays 逾期天数
     * @return 罚款记录
     */
    public FineRecord calculateFine(Long borrowId, Integer readerId, int overdueDays);

    /**
     * 创建支付订单
     *
     * @param fineId 罚款ID
     * @param payMethod 支付方式
     * @return 支付订单
     */
    public PaymentOrder createPaymentOrder(Long fineId, String payMethod);

    /**
     * 支付回调处理
     *
     * @param orderId 订单ID
     * @param tradeNo 交易流水号
     * @return 结果
     */
    public int payCallback(Long orderId, String tradeNo);
}
