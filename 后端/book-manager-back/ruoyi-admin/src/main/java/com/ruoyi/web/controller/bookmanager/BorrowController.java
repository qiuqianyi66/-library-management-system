package com.ruoyi.web.controller.bookmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.BorrowRecord;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.BorrowService;

/**
 * 借阅管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/bookmanager/borrow")
public class BorrowController extends BaseController
{
    @Autowired
    private BorrowService borrowService;

    /**
     * 获取借阅列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BorrowRecord record)
    {
        startPage();
        List<BorrowRecord> list = borrowService.selectBorrowList(record);
        return getDataTable(list);
    }

    /**
     * 根据借阅编号获取详细信息
     */
    @GetMapping(value = { "/", "/{borrowId}" })
    public AjaxResult getInfo(@PathVariable(value = "borrowId", required = false) Long borrowId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(borrowId))
        {
            BorrowRecord record = borrowService.selectBorrowById(borrowId);
            ajax.put(AjaxResult.DATA_TAG, record);
        }
        return ajax;
    }

    /**
     * 借阅图书
     */
    @PostMapping("/borrowBook")
    public AjaxResult borrowBook(@RequestBody BorrowRecord record)
    {
        record.setCreateBy(getUsername());
        return toAjax(borrowService.borrowBook(record));
    }

    /**
     * 归还图书
     */
    @PutMapping("/returnBook/{borrowId}")
    public AjaxResult returnBook(@PathVariable Long borrowId)
    {
        return toAjax(borrowService.returnBook(borrowId));
    }

    /**
     * 续借图书
     */
    @PutMapping("/renewBook/{borrowId}")
    public AjaxResult renewBook(@PathVariable Long borrowId)
    {
        return toAjax(borrowService.renewBook(borrowId));
    }
}
