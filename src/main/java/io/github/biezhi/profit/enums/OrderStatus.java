package io.github.biezhi.profit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付订单状态
 *
 * @author biezhi
 * @date 2018/9/29
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    WAIT_PAY(0, "等待支付"),
    PAY_SUCCESS(1, "支付成功");

    private int    status;
    private String desc;

}
