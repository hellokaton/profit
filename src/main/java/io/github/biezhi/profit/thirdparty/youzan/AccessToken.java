package io.github.biezhi.profit.thirdparty.youzan;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author biezhi
 * @date 2017/12/17
 */
@Data
public class AccessToken {

    private String access_token;
    private Long   expires_in;
    private String scope;

    @SerializedName("error_description")
    private String errorDescription;
    private String error;
}
