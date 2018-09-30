package io.github.biezhi.profit.thirdparty;

import io.github.biezhi.profit.entities.request.CreateOrderRequest;
import io.github.biezhi.profit.entities.response.PayOrderResponse;

/**
 * PAYJS API 实现
 *
 * @author biezhi
 * @date 2018/9/29
 */
public class PayJSApi implements PayApi {

    @Override
    public PayOrderResponse createQrCode(CreateOrderRequest createOrderRequest) {
        // TODO
        return null;
    }

    @Override
    public boolean orderPaySuccess(String tradeNo) {
        // TODO
        return false;
    }
}
