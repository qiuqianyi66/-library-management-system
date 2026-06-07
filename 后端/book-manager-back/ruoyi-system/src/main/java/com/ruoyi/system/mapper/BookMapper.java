package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.Book;
import org.springframework.stereotype.Repository;

/**
 * 图书表 数据层
 *
 * @author ruoyi
 */
@Repository
public interface BookMapper
{
    /**
     * 根据条件分页查询图书列表
     *
     * @param book 图书信息
     * @return 图书信息集合信息
     */
    public List<Book> selectBookList(Book book);

    /**
     * 通过图书ID查询图书
     *
     * @param bookId 图书ID
     * @return 图书对象信息
     */
    public Book selectBookById(Long bookId);

    /**
     * 新增图书信息
     *
     * @param book 图书信息
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改图书信息
     *
     * @param book 图书信息
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 通过图书ID删除图书
     *
     * @param bookId 图书ID
     * @return 结果
     */
    public int deleteBookById(Long bookId);

    /**
     * 批量删除图书信息
     *
     * @param bookIds 需要删除的图书ID
     * @return 结果
     */
    public int deleteBookByIds(Long[] bookIds);

    /**
     * 减少图书库存（借书时调用）
     *
     * @param bookId 图书ID
     * @return 结果
     */
    public int decrementStock(Integer bookId);

    /**
     * 增加图书库存（还书时调用）
     *
     * @param bookId 图书ID
     * @return 结果
     */
    public int incrementStock(Integer bookId);

    /**
     * 校验图书名称是否唯一
     *
     * @param bookName 图书名称
     * @return 结果
     */
    public Book checkBookNameUnique(String bookName);
}
