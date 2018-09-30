package io.github.biezhi.profit.enums;

import com.blade.mvc.ui.RestResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 检查订单返回状态
 *
 * @author biezhi
 * @date 2018/9/29
 */
@Getter
@AllArgsConstructor
public enum CheckOrderCode {

    NOT_FOUND_ORDER(-1000, "不存在该订单"),
    WAITED(-1001, "继续等待"),
    TRADE_WAIT_TIMEOUT(300, "订单等待超时"),
    TRADE_PAY_SUCCESS(200, "订单支付成功");

    private int    code;
    private String msg;

    public RestResponse asResponse(){
        return RestResponse.fail(this.getCode(), this.getMsg());
    }
}
