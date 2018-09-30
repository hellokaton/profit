package io.github.biezhi.profit.entities.request;

import lombok.Data;

/**
 * @author biezhi
 * @date 2017/12/18
 */
@Data
public class CreateOrderRequest {

    /**
     * 金额，单位/元
     */
    private Integer price;
    private String  qrName = "打赏一下";
    private String  qrType = "QR_TYPE_NOLIMIT";

}
