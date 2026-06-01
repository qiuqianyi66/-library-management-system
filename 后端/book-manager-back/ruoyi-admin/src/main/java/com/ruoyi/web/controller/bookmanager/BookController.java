package com.ruoyi.web.controller.bookmanager;

import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Book;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.BookService;

/**
 * 图书信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/bookmanager/book")
public class BookController extends BaseController
{
    @Autowired
    private BookService bookService;

    /**
     * 获取图书列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Book book)
    {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 根据图书编号获取详细信息
     */
    @GetMapping(value = { "/", "/{bookId}" })
    public AjaxResult getInfo(@PathVariable(value = "bookId", required = false) Long bookId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(bookId))
        {
            Book book = bookService.selectBookById(bookId);
            ajax.put(AjaxResult.DATA_TAG, book);
        }
        return ajax;
    }

    /**
     * 新增图书
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Book book)
    {
        if (!bookService.checkBookNameUnique(book))
        {
            return error("新增图书'" + book.getBookName() + "'失败，图书名称已存在");
        }
        book.setCreateBy(getUsername());
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改图书
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Book book)
    {
        bookService.checkBookAllowed(book);
        if (!bookService.checkBookNameUnique(book))
        {
            return error("修改图书'" + book.getBookName() + "'失败，图书名称已存在");
        }
        book.setUpdateBy(getUsername());
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable Long[] bookIds)
    {
        return toAjax(bookService.deleteBookByIds(bookIds));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody Book book)
    {
        bookService.checkBookAllowed(book);
        book.setUpdateBy(getUsername());
        return toAjax(bookService.updateBookStatus(book));
    }
}
