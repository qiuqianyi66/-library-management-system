package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.FineRecord;
import org.springframework.stereotype.Repository;

/**
 * 罚款记录 数据层
 *
 * @author ruoyi
 */
@Repository
public interface FineMapper
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
     * 查询本月已缴纳罚款总额
     *
     * @return 本月罚款收入
     */
    public java.math.BigDecimal selectMonthlyFineIncome();

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
}
