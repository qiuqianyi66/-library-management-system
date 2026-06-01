package com.ruoyi.web.controller.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.FineRecord;
import com.ruoyi.common.core.page.TableDataInfo;
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

    /**
     * 我的借阅
     * 根据当前登录用户关联的读者ID查询
     */
    @GetMapping("/myBorrow")
    public AjaxResult myBorrow()
    {
        AjaxResult ajax = AjaxResult.success();
        List<Map<String, Object>> list = new ArrayList<>();
        ajax.put(AjaxResult.DATA_TAG, list);
        return ajax;
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
    public AjaxResult searchBooks(@RequestParam(required = false) String keyword)
    {
        AjaxResult ajax = AjaxResult.success();
        List<Map<String, Object>> list = new ArrayList<>();
        ajax.put(AjaxResult.DATA_TAG, list);
        return ajax;
    }

    /**
     * 自助续借
     */
    @PostMapping("/renewBook/{borrowId}")
    public AjaxResult renewBook(@PathVariable("borrowId") Long borrowId)
    {
        AjaxResult ajax = AjaxResult.success();
        Map<String, Object> result = new HashMap<>();
        result.put("borrowId", borrowId);
        result.put("renewStatus", "success");
        result.put("message", "续借成功");
        ajax.put(AjaxResult.DATA_TAG, result);
        return ajax;
    }
}
