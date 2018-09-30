package io.github.biezhi.profit.thirdparty;

import io.github.biezhi.profit.entities.request.CreateOrderRequest;
import io.github.biezhi.profit.entities.response.PayOrderResponse;
import io.github.biezhi.profit.enums.Platform;

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

    /**
     * 根据支付平台获取支付 API 实现
     *
     * @param plaform
     * @return
     */
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
