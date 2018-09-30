package io.github.biezhi.profit.thirdparty;

import com.blade.exception.ValidatorException;
import com.blade.kit.StringKit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.biezhi.profit.bootstrap.Bootstrap;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.request.CreateOrderRequest;
import io.github.biezhi.profit.entities.response.PayOrderResponse;
import io.github.biezhi.profit.entities.response.QRCodeResponse;
import io.github.biezhi.profit.thirdparty.youzan.AccessToken;
import io.github.biezhi.profit.thirdparty.youzan.Response;
import io.github.biezhi.request.Request;
import lombok.extern.slf4j.Slf4j;

import static io.github.biezhi.profit.bootstrap.Constant.HTTP_TIME_OUT;

/**
 * 有赞云支付 API 实现
 *
 * @author biezhi
 * @date 2018/9/29
 */
@Slf4j
public class YouzanPayApi implements PayApi {

    private static final Gson   gson       = new Gson();
    private static final String YOUZAN_URL = "https://open.youzan.com/api/oauthentry";

    private AccessToken getAccessToken() {
        String body = Request.post("https://open.youzan.com/oauth/token")
                .userAgent("X-YZ-Client 2.0.0 - Java")
                .contentType("application/json", "utf-8")
                .form("client_id", Bootstrap.getGlobalConfig().get(Constant.SAFE_YOUZAN_CLIENT_ID))
                .form("client_secret", Bootstrap.getGlobalConfig().get(Constant.SAFE_YOUZAN_CLIENT_SECRET))
                .form("grant_type", "silent")
                .form("kdt_id", Bootstrap.getGlobalConfig().get(Constant.SAFE_YOUZAN_SHOP_ID))
                .connectTimeout(HTTP_TIME_OUT)
                .readTimeout(HTTP_TIME_OUT)
                .body();

        log.info("获取 AccessToken 响应: {}", body);
        try {
            return gson.fromJson(body, AccessToken.class);
        } catch (Exception e) {
            log.error("获取有赞云 AccessToken 失败", e);
            throw new ValidatorException("获取有赞云 AccessToken 失败");
        }
    }

    @Override
    public PayOrderResponse createQrCode(CreateOrderRequest createOrderRequest) {
        AccessToken accessToken = getAccessToken();
        if (StringKit.isNotEmpty(accessToken.getError())) {
            throw new ValidatorException(accessToken.getErrorDescription());
        }

        String body = Request
                .get(YOUZAN_URL + "/youzan.pay.qrcode/3.0.0/create")
                .userAgent("X-YZ-Client 2.0.0 - Java")
                .contentType("application/json", "utf-8")
                .form("access_token", accessToken.getAccess_token())
                .form("qr_type", createOrderRequest.getQrType())
                .form("qr_price", createOrderRequest.getPrice())
                .form("qr_name", createOrderRequest.getQrName())
                .connectTimeout(HTTP_TIME_OUT)
                .readTimeout(HTTP_TIME_OUT)
                .body();

        log.info("创建二维码响应: {}", body);
        Response<QRCodeResponse> resultResponse = gson.fromJson(body,
                new TypeToken<Response<QRCodeResponse>>() {
                }.getType());

        QRCodeResponse   response         = resultResponse.getResponse();
        PayOrderResponse payOrderResponse = new PayOrderResponse();
        payOrderResponse.setTradeNo(response.getQrId().toString());
        payOrderResponse.setQrImg(response.getQrCode());
        payOrderResponse.setQrUrl(response.getQrUrl());

        return payOrderResponse;
    }

    @Override
    public boolean orderPaySuccess(String tradeNo) {
        AccessToken accessToken = getAccessToken();
        if (StringKit.isNotEmpty(accessToken.getError())) {
            throw new ValidatorException(accessToken.getErrorDescription());
        }
        String body = Request
                .get(YOUZAN_URL + "/youzan.trades.qr/3.0.0/get")
                .userAgent("X-YZ-Client 2.0.0 - Java")
                .contentType("application/json", "utf-8")
                .form("access_token", accessToken.getAccess_token())
                .form("qr_id", tradeNo)
                .connectTimeout(HTTP_TIME_OUT)
                .readTimeout(HTTP_TIME_OUT)
                .body();

        log.info("根据QRID查询订单响应: {}", body);

        return body.contains("TRADE_RECEIVED");
    }

}
