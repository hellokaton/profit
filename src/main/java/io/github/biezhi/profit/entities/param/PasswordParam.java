package io.github.biezhi.profit.entities.param;

import lombok.Data;

/**
 * 修改密码参数
 *
 * @author biezhi
 * @date 2018/9/29
 */
@Data
public class PasswordParam {

    private String oldPass;
    private String newPass;

}
