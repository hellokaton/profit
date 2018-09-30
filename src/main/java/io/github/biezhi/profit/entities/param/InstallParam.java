package io.github.biezhi.profit.entities.param;

import lombok.Data;

/**
 * 安装参数
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Data
public class InstallParam {

    private String username;
    private String password;

    private String theme;
    private String platform;
    private String youzanClientId;
    private String youzanClientSecret;
    private String youzanShopId;
    private String payJSMchid;
    private String payJSSecret;

    private boolean isUpdate;

}
