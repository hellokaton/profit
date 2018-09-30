package io.github.biezhi.profit.utils;

import com.blade.kit.Hashids;
import com.blade.kit.StringKit;
import com.blade.mvc.WebContext;
import io.github.biezhi.profit.bootstrap.Constant;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * 全局工具类
 *
 * @author biezhi
 * @date 2018/9/28
 */
@UtilityClass
public class Utils {

    public static final String CLASSPATH = new File(Utils.class.getResource("/").getPath()).getPath() + File.separatorChar;

    public static final Set<String> BLACK_LIST = new HashSet<>();

    private static final Hashids HASHIDS = new Hashids("2018", 10);

    /**
     * 生成订单号
     *
     * @return
     */
    public static String genOrderID() {
        return HASHIDS.encode(2, 0, 1, 8, System.currentTimeMillis()).toLowerCase();
    }

    /**
     * 是否已经安装过，检查是否存在 install.lock
     *
     * @return
     */
    public static boolean isInstall() {
        return Files.exists(Paths.get(CLASSPATH + "install.lock"));
    }

    public static boolean isLogin() {
        return null != WebContext.request().session().attribute(Constant.LOGIN_SESSION_KEY);
    }

    public static boolean isBlackIP(String address) {
        return false;
    }

    public static String decode(String msg) {
        try {
            return URLDecoder.decode(msg, "utf-8");
        } catch (Exception e) {
            return msg;
        }
    }

    public static Integer parseInt(String value, int defaultValue) {
        try {
            if(StringKit.isEmpty(value)){
                return defaultValue;
            }
            return Integer.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double parseDouble(String value, double defaultValue) {
        try {
            if(StringKit.isEmpty(value)){
                return defaultValue;
            }
            return Double.valueOf(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
