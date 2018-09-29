package io.github.biezhi.makemoney.bootstrap;

import com.blade.kit.DateKit;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author biezhi
 * @date 2018/9/29
 */
public final class TemplateExt {

    public static String date(Date date){
        return datetime(date, "yyyy-MM-dd");
    }

    public static String datetime(Date date){
        return datetime(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String datetime(Date date, String pattern){
        return DateKit.toString(date, pattern);
    }

    public static String money(BigDecimal money){
        if(null == money){
            return "0";
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 输出格式化货币格式
        return format.format(money.divide(new BigDecimal("100")).doubleValue()).replaceFirst("￥", "");
    }

    public static String money(String money){
        return money(new BigDecimal(money));
    }

}
