package io.github.biezhi.profit.entities.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author biezhi
 * @date 2018/9/28
 */
@Data
public class QRCodeResponse {

    /**
     * 二维码收银台Url
     */
    @SerializedName(value = "qr_url")
    private String qrUrl;

    /**
     * 二维码图标
     */
    @SerializedName(value = "qr_code")
    private String qrCode;

    /**
     * 二维码标识
     */
    @SerializedName(value = "qr_id")
    private Long qrId;

    private String payChannel;

}
