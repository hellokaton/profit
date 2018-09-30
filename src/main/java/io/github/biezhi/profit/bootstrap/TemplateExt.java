package io.github.biezhi.profit.bootstrap;

import com.blade.kit.DateKit;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 模板引擎函数扩展
 *
 * @author biezhi
 * @date 2018/9/29
 */
public final class TemplateExt {

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static String date(Date date) {
        return datetime(date, "yyyy-MM-dd");
    }

    /**
     * 日期时间格式化
     *
     * @param date
     * @return
     */
    public static String datetime(Date date) {
        return datetime(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期时间格式化
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String datetime(Date date, String pattern) {
        return DateKit.toString(date, pattern);
    }

    /**
     * 货币格式化
     *
     * @param money
     * @return
     */
    public static String money(BigDecimal money) {
        if (null == money) {
            return "0";
        }
        Double       realMoney = money.divide(new BigDecimal("100")).doubleValue();
        NumberFormat format    = NumberFormat.getCurrencyInstance(Locale.CHINA);
        return format.format(realMoney).replaceFirst("￥", "");
    }

    public static String money(String money) {
        return money(new BigDecimal(money));
    }

}
