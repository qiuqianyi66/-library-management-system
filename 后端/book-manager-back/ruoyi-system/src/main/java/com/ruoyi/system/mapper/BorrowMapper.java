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

    // ========== 推荐系统查询 ==========

    /**
     * 查询读者借阅过的图书ID列表
     */
    public List<Integer> selectBookIdsByReaderId(Integer readerId);

    /**
     * 协同过滤：查询与指定图书共现频率最高的N本图书（排除读者已借的）
     */
    public List<Map<String, Object>> selectCoBorrowedBooks(Integer bookId, Integer readerId);

    /**
     * 查询读者未借阅过的热门图书
     */
    public List<Map<String, Object>> selectUnborrowedHotBooks(Integer readerId);

    // ========== 阅读报告查询 ==========

    /**
     * 查询读者近N天借阅趋势
     */
    public List<Map<String, Object>> selectReaderBorrowTrend(
            com.ruoyi.common.core.domain.entity.BorrowRecord record);

    /**
     * 查询读者借阅类型偏好
     */
    public List<Map<String, Object>> selectReaderTypePreference(Integer readerId);
}
