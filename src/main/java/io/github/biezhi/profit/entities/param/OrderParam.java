package io.github.biezhi.profit.entities.param;

import lombok.Data;

/**
 * 订单查询参数
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Data
public class OrderParam {

    private String  p;
    private Integer status;
    private String  channel;
    private String  platform;
}
