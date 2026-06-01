package com.ruoyi.web.controller.bookmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.FineRecord;
import com.ruoyi.common.core.domain.entity.PaymentOrder;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.FineService;

/**
 * 罚款与支付
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/bookmanager/fine")
public class FineController extends BaseController
{
    @Autowired
    private FineService fineService;

    /**
     * 获取罚款列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FineRecord fineRecord)
    {
        startPage();
        List<FineRecord> list = fineService.selectFineList(fineRecord);
        return getDataTable(list);
    }

    /**
     * 根据罚款ID获取详细信息
     */
    @GetMapping(value = "/{fineId}")
    public AjaxResult getInfo(@PathVariable("fineId") Long fineId)
    {
        AjaxResult ajax = AjaxResult.success();
        FineRecord fineRecord = fineService.selectFineById(fineId);
        ajax.put(AjaxResult.DATA_TAG, fineRecord);
        return ajax;
    }

    /**
     * 发起支付
     */
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody FineRecord fineRecord)
    {
        if (StringUtils.isNull(fineRecord.getFineId()))
        {
            return error("罚款ID不能为空");
        }
        if (StringUtils.isEmpty(fineRecord.getPayMethod()))
        {
            return error("支付方式不能为空");
        }
        PaymentOrder order = fineService.createPaymentOrder(fineRecord.getFineId(), fineRecord.getPayMethod());
        return success(order);
    }

    /**
     * 支付回调
     */
    @PostMapping("/payCallback")
    public AjaxResult payCallback(@RequestBody PaymentOrder paymentOrder)
    {
        if (StringUtils.isNull(paymentOrder.getOrderId()))
        {
            return error("订单ID不能为空");
        }
        if (StringUtils.isEmpty(paymentOrder.getTradeNo()))
        {
            return error("交易流水号不能为空");
        }
        fineService.payCallback(paymentOrder.getOrderId(), paymentOrder.getTradeNo());
        return success();
    }
}
