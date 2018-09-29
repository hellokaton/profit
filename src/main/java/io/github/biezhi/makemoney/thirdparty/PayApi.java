package io.github.biezhi.makemoney.thirdparty;

import io.github.biezhi.makemoney.entities.request.CreateOrderRequest;
import io.github.biezhi.makemoney.entities.response.PayOrderResponse;
import io.github.biezhi.makemoney.enums.Platform;

/**
 * 支付平台接口抽象
 *
 * @author biezhi
 * @date 2018/9/29
 */
public interface PayApi {

    /**
     * 创建支付二维码
     *
     * @param createOrderRequest
     * @return
     */
    PayOrderResponse createQrCode(CreateOrderRequest createOrderRequest);

    /**
     * 查询订单是否已经支付成功
     *
     * @param tradeNo
     * @return
     */
    boolean orderPaySuccess(String tradeNo);

    static PayApi getPayApi(String plaform){
        if(Platform.YOUZAN.toString().equalsIgnoreCase(plaform)){
            return new YouzanPayApi();
        }
        if(Platform.PAYJS.toString().equalsIgnoreCase(plaform)){
            return new PayJSApi();
        }
        return null;
    }
}
