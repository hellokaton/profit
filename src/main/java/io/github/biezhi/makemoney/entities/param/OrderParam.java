package io.github.biezhi.makemoney.entities.param;

import lombok.Data;

/**
 * @author biezhi
 * @date 2018/9/28
 */
@Data
public class OrderParam extends PageParam {

    private Integer status;
    private String  channel;
    private String  platform;
}
