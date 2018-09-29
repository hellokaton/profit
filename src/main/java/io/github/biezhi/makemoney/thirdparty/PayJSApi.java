package io.github.biezhi.makemoney.thirdparty;

import io.github.biezhi.makemoney.entities.request.CreateOrderRequest;
import io.github.biezhi.makemoney.entities.response.PayOrderResponse;

/**
 * @author biezhi
 * @date 2018/9/29
 */
public class PayJSApi implements PayApi {
    @Override
    public PayOrderResponse createQrCode(CreateOrderRequest createOrderRequest) {
        return null;
    }

    @Override
    public boolean orderPaySuccess(String tradeNo) {
        return false;
    }
}
