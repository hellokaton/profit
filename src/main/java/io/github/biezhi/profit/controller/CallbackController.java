package io.github.biezhi.profit.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.http.Request;
import io.github.biezhi.profit.enums.Platform;
import io.github.biezhi.profit.service.MakeMoneyService;
import lombok.extern.slf4j.Slf4j;

/**
 * 第三方支付回调
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Slf4j
@Path(value = "callback", restful = true)
public class CallbackController {

    @Inject
    private MakeMoneyService makeMoneyService;

    @PostRoute("youzan")
    public String youzan(Request request) {
        String body = request.bodyToString();
        log.info("收到有赞支付回调: {}", body);
        makeMoneyService.execCallback(Platform.YOUZAN, body);
        return "success";
    }

    @PostRoute("payjs")
    public String payjs(Request request) {
        String body = request.bodyToString();
        log.info("收到PAYJS支付回调1: {}", body);
        return "success";
    }

}
