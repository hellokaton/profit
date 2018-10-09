package io.github.biezhi.profit.controller;

import com.blade.exception.ValidatorException;
import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.ui.RestResponse;
import io.github.biezhi.anima.page.Page;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Order;
import io.github.biezhi.profit.entities.param.OrderParam;
import io.github.biezhi.profit.entities.response.PayOrderResponse;
import io.github.biezhi.profit.enums.CheckOrderCode;
import io.github.biezhi.profit.enums.OrderStatus;
import io.github.biezhi.profit.service.MakeMoneyService;
import io.github.biezhi.profit.verification.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.github.biezhi.profit.bootstrap.Constant.MAX_WAIT_COUNT;
import static io.github.biezhi.profit.bootstrap.Constant.TRADE_CHECK_COUNT;

/**
 * 打赏支付控制器
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Slf4j
@Path
public class IndexController {

    public static String THEME_NAME = "default";

    @Inject
    private MakeMoneyService makeMoneyService;

    /**
     * 打赏首页
     *
     * @param orderParam
     * @param request
     * @return
     */
    @GetRoute("/")
    public String index(@Param OrderParam orderParam, Request request) {
        Page<Order> orderPage = makeMoneyService.findOrders(orderParam);
        request.attribute("orderPage", orderPage);

        Map<String, String> options = makeMoneyService.findOptions();
        request.attribute("options", options);
        return "themes/" + THEME_NAME + "/index.html";
    }

    /**
     * 检查订单支付状态
     *
     * @param tradeNo
     * @return
     */
    @GetRoute("check_order")
    @JSON
    public RestResponse checkOrder(@Param String tradeNo) {
        if (!Constant.TRADE_NOS.contains(tradeNo)) {
            return CheckOrderCode.NOT_FOUND_ORDER.asResponse();
        }

        if (TRADE_CHECK_COUNT.containsKey(tradeNo)) {
            if (TRADE_CHECK_COUNT.get(tradeNo) > MAX_WAIT_COUNT) {
                return CheckOrderCode.TRADE_WAIT_TIMEOUT.asResponse();
            }
            TRADE_CHECK_COUNT.put(tradeNo, TRADE_CHECK_COUNT.get(tradeNo) + 1);
        } else {
            TRADE_CHECK_COUNT.put(tradeNo, 1);
        }
        boolean paySuccess = makeMoneyService.checkOrderPaySuccess(tradeNo);
        if (paySuccess) {
            makeMoneyService.updatePaySuccess(tradeNo, OrderStatus.PAY_SUCCESS.getStatus());
            return CheckOrderCode.TRADE_PAY_SUCCESS.asResponse();
        }
        return CheckOrderCode.WAITED.asResponse();
    }

    /**
     * 创建支付订单
     *
     * @param order
     * @param request
     * @return
     */
    @PostRoute("/pay_order")
    @JSON
    public RestResponse<PayOrderResponse> createOrder(Order order, Request request) {
        try {
            Validator.orderParam(order);
            if (null == request.header("") || null == request.userAgent()) {
                return RestResponse.fail("非法请求");
            }
            return RestResponse.ok(makeMoneyService.createOrder(order));
        } catch (ValidatorException e) {
            return RestResponse.fail(e.getMessage());
        } catch (Exception e) {
            log.error("支付出现异常", e);
            return RestResponse.fail(e.getMessage());
        }
    }

}
