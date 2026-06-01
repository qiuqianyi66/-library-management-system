package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowMapper
{
    int selectTodayBorrowCount();

    int selectTodayReturnCount();

    int selectCurrentBorrowCount();

    int selectOverdueCount();

    List<Map<String, Object>> selectBorrowTrendByDays(int days);

    List<Map<String, Object>> selectHotBooksTop10();

    public List<BorrowRecord> selectBorrowList(BorrowRecord record);

    public BorrowRecord selectBorrowById(Long borrowId);

    public List<BorrowRecord> selectActiveByReaderId(Integer readerId);

    public BorrowRecord selectActiveByBookId(Integer bookId);

    public int insertBorrow(BorrowRecord record);

    public int updateBorrow(BorrowRecord record);
}
