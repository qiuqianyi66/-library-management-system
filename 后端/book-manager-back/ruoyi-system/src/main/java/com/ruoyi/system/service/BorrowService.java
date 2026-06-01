package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.BorrowRecord;

/**
 * 借阅管理 业务层
 *
 * @author ruoyi
 */
public interface BorrowService
{
    /**
     * 根据条件分页查询借阅列表
     *
     * @param record 借阅信息
     * @return 借阅信息集合
     */
    public List<BorrowRecord> selectBorrowList(BorrowRecord record);

    /**
     * 通过借阅ID查询借阅记录
     *
     * @param borrowId 借阅ID
     * @return 借阅信息
     */
    public BorrowRecord selectBorrowById(Long borrowId);

    /**
     * 借阅图书
     *
     * @param record 借阅信息
     * @return 结果
     */
    public int borrowBook(BorrowRecord record);

    /**
     * 归还图书
     *
     * @param borrowId 借阅ID
     * @return 结果
     */
    public int returnBook(Long borrowId);

    /**
     * 续借图书
     *
     * @param borrowId 借阅ID
     * @return 结果
     */
    public int renewBook(Long borrowId);
}
