package io.github.biezhi.profit.thirdparty.youzan;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author ph0ly
 * @time 2016-11-29
 */
@Data
public class Response<T> {

    private T response;

    @SerializedName("error_response")
    private T errorResponse;

}
