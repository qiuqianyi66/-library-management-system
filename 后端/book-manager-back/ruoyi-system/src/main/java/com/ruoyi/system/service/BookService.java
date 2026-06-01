package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.Book;

/**
 * 图书 业务层
 *
 * @author ruoyi
 */
public interface BookService
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
     * 校验图书名称是否唯一
     *
     * @param book 图书信息
     * @return 结果
     */
    public boolean checkBookNameUnique(Book book);

    /**
     * 校验图书是否允许操作
     *
     * @param book 图书信息
     */
    public void checkBookAllowed(Book book);

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
     * 修改图书状态
     *
     * @param book 图书信息
     * @return 结果
     */
    public int updateBookStatus(Book book);

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
}
