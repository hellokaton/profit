package io.github.biezhi.makemoney.utils;

import com.blade.kit.Hashids;
import com.blade.mvc.WebContext;
import io.github.biezhi.makemoney.bootstrap.Constant;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    public static Map<String, List<Long>> map = new ConcurrentHashMap<>();

    private static final Hashids HASHIDS = new Hashids("2018", 10);

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

    public static void addBlack(String ip) {
        BLACK_LIST.add(ip);
    }

    public static boolean isBlackIP(String address) {
        if (!BLACK_LIST.contains(address)) {
//            long time = System.currentTimeMillis();
//            if (map.containsKey(address)) {
//                map.get(address).add(time);
//            } else {
//                map.put(address, Arrays.asList(time));
//            }
            return false;
        }
        return true;
    }

    /**
     *   * utf-8 转换成 unicode
     *   * @author fanhui
     *   * 2007-3-15
     *   * @param inStr
     *   * @return
     *  
     */
    public static String utf8ToUnicode(String inStr) {
        char[]       myBuffer = inStr.toCharArray();
        StringBuffer sb       = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                //英文及数字等
                sb.append(myBuffer[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                //全角半角字符
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                //汉字
                short  s       = (short) myBuffer[i];
                String hexS    = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String encode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e){
            return str;
        }
    }

    public static String decode(String msg) {
        try {
            return URLDecoder.decode(msg, "utf-8");
        } catch (Exception e){
            return msg;
        }
    }

    public static Integer parseOrDefault(String value, int defaultValue){
        try {
            return Integer.valueOf(value);
        } catch (Exception e){
            return defaultValue;
        }
    }
}
