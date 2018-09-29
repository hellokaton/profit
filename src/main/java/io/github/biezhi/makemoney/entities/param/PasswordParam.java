package io.github.biezhi.makemoney.entities.param;

import lombok.Data;

/**
 * @author biezhi
 * @date 2018/9/29
 */
@Data
public class PasswordParam {

    private String oldPass;
    private String newPass;

}
