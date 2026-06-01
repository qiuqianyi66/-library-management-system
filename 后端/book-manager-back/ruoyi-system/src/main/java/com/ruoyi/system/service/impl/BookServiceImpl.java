package com.ruoyi.system.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.Book;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.BookMapper;
import com.ruoyi.system.service.BookService;

/**
 * 图书 业务层处理
 *
 * @author ruoyi
 */
@Service
public class BookServiceImpl implements BookService
{
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;

    /**
     * 根据条件分页查询图书列表
     *
     * @param book 图书信息
     * @return 图书信息集合信息
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        return bookMapper.selectBookList(book);
    }

    /**
     * 通过图书ID查询图书
     *
     * @param bookId 图书ID
     * @return 图书对象信息
     */
    @Override
    public Book selectBookById(Long bookId)
    {
        return bookMapper.selectBookById(bookId);
    }

    /**
     * 校验图书名称是否唯一
     *
     * @param book 图书信息
     * @return 结果
     */
    @Override
    public boolean checkBookNameUnique(Book book)
    {
        Long bookId = StringUtils.isNull(book.getBookId()) ? -1L : book.getBookId();
        Book info = bookMapper.checkBookNameUnique(book.getBookName());
        if (StringUtils.isNotNull(info) && info.getBookId().longValue() != bookId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验图书是否允许操作
     *
     * @param book 图书信息
     */
    @Override
    public void checkBookAllowed(Book book)
    {
        if (StringUtils.isNotNull(book.getBookId()))
        {
            Book info = bookMapper.selectBookById(book.getBookId());
            if (StringUtils.isNull(info))
            {
                throw new ServiceException("图书不存在");
            }
        }
    }

    /**
     * 新增保存图书信息
     *
     * @param book 图书信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertBook(Book book)
    {
        int rows = bookMapper.insertBook(book);
        return rows;
    }

    /**
     * 修改保存图书信息
     *
     * @param book 图书信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateBook(Book book)
    {
        return bookMapper.updateBook(book);
    }

    /**
     * 修改图书状态
     *
     * @param book 图书信息
     * @return 结果
     */
    @Override
    public int updateBookStatus(Book book)
    {
        return bookMapper.updateBook(book);
    }

    /**
     * 通过图书ID删除图书
     *
     * @param bookId 图书ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteBookById(Long bookId)
    {
        return bookMapper.deleteBookById(bookId);
    }

    /**
     * 批量删除图书信息
     *
     * @param bookIds 需要删除的图书ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteBookByIds(Long[] bookIds)
    {
        for (Long bookId : bookIds)
        {
            checkBookAllowed(new Book(bookId));
        }
        return bookMapper.deleteBookByIds(bookIds);
    }
}
