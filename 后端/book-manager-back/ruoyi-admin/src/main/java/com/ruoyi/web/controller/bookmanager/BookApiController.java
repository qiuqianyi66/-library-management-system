package com.ruoyi.web.controller.bookmanager;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.BookApiService;

/**
 * 外部图书 API（对接 Google Books）
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/bookmanager/bookapi")
public class BookApiController extends BaseController
{
    @Autowired
    private BookApiService bookApiService;

    /**
     * 根据 ISBN 查询外部图书信息
     */
    @GetMapping("/fetchByIsbn/{isbn}")
    public AjaxResult fetchByIsbn(@PathVariable String isbn)
    {
        Map<String, Object> info = bookApiService.fetchBookByIsbn(isbn);
        if (info.isEmpty())
        {
            return error("未找到该ISBN对应的图书信息");
        }
        return AjaxResult.success(info);
    }
}
