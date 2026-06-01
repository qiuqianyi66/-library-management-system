package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.BorrowMapper;
import com.ruoyi.system.service.BorrowService;
import com.ruoyi.system.service.ConfigService;

/**
 * 借阅管理 业务层处理
 *
 * @author ruoyi
 */
@Service
public class BorrowServiceImpl implements BorrowService
{
    private static final Logger log = LoggerFactory.getLogger(BorrowServiceImpl.class);

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private ConfigService configService;

    /**
     * 根据条件分页查询借阅列表
     *
     * @param record 借阅信息
     * @return 借阅信息集合
     */
    @Override
    public List<BorrowRecord> selectBorrowList(BorrowRecord record)
    {
        return borrowMapper.selectBorrowList(record);
    }

    /**
     * 通过借阅ID查询借阅记录
     *
     * @param borrowId 借阅ID
     * @return 借阅信息
     */
    @Override
    public BorrowRecord selectBorrowById(Long borrowId)
    {
        return borrowMapper.selectBorrowById(borrowId);
    }

    /**
     * 借阅图书
     *
     * @param record 借阅信息
     * @return 结果
     */
    @Override
    @Transactional
    public int borrowBook(BorrowRecord record)
    {
        Integer readerId = record.getReaderId();
        Integer bookId = record.getBookId();

        // 校验读者状态：查询读者是否有逾期未还的借阅记录
        List<BorrowRecord> activeRecords = borrowMapper.selectActiveByReaderId(readerId);
        for (BorrowRecord active : activeRecords)
        {
            if ("overdue".equals(active.getStatus()))
            {
                throw new ServiceException("读者存在逾期未还的图书，无法继续借阅");
            }
        }

        // 校验读者借阅数量是否已达上限
        String maxCountStr = configService.selectConfigByKey("borrow.max_count");
        int maxCount = StringUtils.isNotEmpty(maxCountStr) ? Integer.parseInt(maxCountStr) : 5;
        int currentBorrowingCount = 0;
        for (BorrowRecord active : activeRecords)
        {
            if ("borrowing".equals(active.getStatus()))
            {
                currentBorrowingCount++;
            }
        }
        if (currentBorrowingCount >= maxCount)
        {
            throw new ServiceException("读者当前借阅数量已达上限（" + maxCount + "本），无法继续借阅");
        }

        // 校验图书是否有库存（通过查询图书是否已被他人借阅中）
        BorrowRecord activeBookRecord = borrowMapper.selectActiveByBookId(bookId);
        if (activeBookRecord != null)
        {
            throw new ServiceException("该图书已被借出，暂时无法借阅");
        }

        // 设置借阅信息
        Date now = new Date();
        record.setBorrowDate(now);
        record.setStatus("borrowing");
        record.setRenewCount(0);
        record.setFineAmount(BigDecimal.ZERO);

        // 计算应还日期 = 当前日期 + 默认借阅天数
        String defaultDaysStr = configService.selectConfigByKey("borrow.default_days");
        int defaultDays = StringUtils.isNotEmpty(defaultDaysStr) ? Integer.parseInt(defaultDaysStr) : 30;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, defaultDays);
        record.setDueDate(calendar.getTime());

        return borrowMapper.insertBorrow(record);
    }

    /**
     * 归还图书
     *
     * @param borrowId 借阅ID
     * @return 结果
     */
    @Override
    @Transactional
    public int returnBook(Long borrowId)
    {
        BorrowRecord record = borrowMapper.selectBorrowById(borrowId);
        if (record == null)
        {
            throw new ServiceException("借阅记录不存在");
        }
        if ("returned".equals(record.getStatus()))
        {
            throw new ServiceException("该图书已归还");
        }

        Date now = new Date();
        record.setReturnDate(now);
        record.setStatus("returned");

        // 计算逾期罚款
        if (now.after(record.getDueDate()))
        {
            long diffMillis = now.getTime() - record.getDueDate().getTime();
            long overdueDays = diffMillis / (1000 * 60 * 60 * 24);

            String finePerDayStr = configService.selectConfigByKey("fine.per_day");
            BigDecimal finePerDay = BigDecimal.valueOf(
                    StringUtils.isNotEmpty(finePerDayStr) ? Double.parseDouble(finePerDayStr) : 0.5
            );

            BigDecimal fineAmount = finePerDay.multiply(BigDecimal.valueOf(overdueDays));
            record.setFineAmount(fineAmount);

            log.info("还书逾期: borrowId={}, overdueDays={}, fineAmount={}", borrowId, overdueDays, fineAmount);
        }

        int rows = borrowMapper.updateBorrow(record);
        return rows;
    }

    /**
     * 续借图书
     *
     * @param borrowId 借阅ID
     * @return 结果
     */
    @Override
    @Transactional
    public int renewBook(Long borrowId)
    {
        BorrowRecord record = borrowMapper.selectBorrowById(borrowId);
        if (record == null)
        {
            throw new ServiceException("借阅记录不存在");
        }
        if (!"borrowing".equals(record.getStatus()))
        {
            throw new ServiceException("当前借阅状态不允许续借");
        }

        // 校验续借次数
        String renewMaxCountStr = configService.selectConfigByKey("borrow.renew_max_count");
        int renewMaxCount = StringUtils.isNotEmpty(renewMaxCountStr) ? Integer.parseInt(renewMaxCountStr) : 2;
        int currentRenewCount = record.getRenewCount() != null ? record.getRenewCount() : 0;
        if (currentRenewCount >= renewMaxCount)
        {
            throw new ServiceException("续借次数已达上限（" + renewMaxCount + "次），无法再次续借");
        }

        // 延长应还日期
        String renewDaysStr = configService.selectConfigByKey("borrow.renew_days");
        int renewDays = StringUtils.isNotEmpty(renewDaysStr) ? Integer.parseInt(renewDaysStr) : 15;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(record.getDueDate());
        calendar.add(Calendar.DAY_OF_MONTH, renewDays);
        record.setDueDate(calendar.getTime());

        // 增加续借次数
        record.setRenewCount(currentRenewCount + 1);

        int rows = borrowMapper.updateBorrow(record);
        return rows;
    }
}
