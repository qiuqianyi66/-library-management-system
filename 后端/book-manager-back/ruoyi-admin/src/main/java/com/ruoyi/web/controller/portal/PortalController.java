package com.ruoyi.web.controller.portal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Book;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import com.ruoyi.common.core.domain.entity.FineRecord;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.BookService;
import com.ruoyi.system.service.BorrowService;
import com.ruoyi.system.service.FineService;

/**
 * 门户/读者自助服务
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/portal")
public class PortalController extends BaseController
{
    @Autowired
    private FineService fineService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    /**
     * 我的借阅
     * 根据当前登录用户关联的读者ID查询
     */
    @GetMapping("/myBorrow")
    public TableDataInfo myBorrow()
    {
        startPage();
        Long userId = getUserId();
        BorrowRecord query = new BorrowRecord();
        query.setReaderId(userId.intValue());
        List<BorrowRecord> list = borrowService.selectBorrowList(query);
        return getDataTable(list);
    }

    /**
     * 我的罚款
     * 根据当前登录用户关联的读者ID查询
     */
    @GetMapping("/myFines")
    public TableDataInfo myFines()
    {
        startPage();
        Long userId = getUserId();
        List<FineRecord> list = fineService.selectFineByReaderId(userId.intValue());
        return getDataTable(list);
    }

    /**
     * 图书检索
     */
    @GetMapping("/searchBooks")
    public TableDataInfo searchBooks(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String type)
    {
        startPage();
        Book query = new Book();
        if (StringUtils.isNotEmpty(keyword))
        {
            query.setBookName(keyword);
        }
        if (StringUtils.isNotEmpty(type))
        {
            query.setType(type);
        }
        List<Book> list = bookService.selectBookList(query);
        return getDataTable(list);
    }

    /**
     * 自助续借
     */
    @PostMapping("/renewBook/{borrowId}")
    public AjaxResult renewBook(@PathVariable("borrowId") Long borrowId)
    {
        return toAjax(borrowService.renewBook(borrowId));
    }
}
