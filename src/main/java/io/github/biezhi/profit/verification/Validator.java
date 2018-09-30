package io.github.biezhi.profit.verification;

import com.blade.exception.ValidatorException;
import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
import com.blade.validator.Validators;
import io.github.biezhi.profit.bootstrap.Bootstrap;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Order;
import io.github.biezhi.profit.entities.param.InstallParam;
import io.github.biezhi.profit.enums.Platform;
import io.github.biezhi.profit.utils.Utils;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * 参数校验工具类
 *
 * @author biezhi
 * @date 2018/9/28
 */
@UtilityClass
public class Validator {

    private static final Pattern EMOJI_PATTERN = Pattern.compile("[^\\u0000-\\uFFFF]");

    public static void installParam(InstallParam installParam) {
        Validators.notNull().test(installParam).throwMessage("安装参数缺失");
        Validators.notEmpty().test(installParam.getPlatform()).throwMessage("安装参数缺失[platform]");

        authParam(installParam.getUsername(), installParam.getPassword());

        if (Platform.YOUZAN.toString().equalsIgnoreCase(installParam.getPlatform())) {
            Validators.notEmpty().test(installParam.getYouzanClientId()).throwMessage("ClientID不能为空");
            Validators.notEmpty().test(installParam.getYouzanClientSecret()).throwMessage("ClientSecret不能为空");
            Validators.notEmpty().test(installParam.getYouzanShopId()).throwMessage("ShopId不能为空");
        }

        if (Platform.PAYJS.toString().equalsIgnoreCase(installParam.getPlatform())) {
            Validators.notEmpty().test(installParam.getPayJSMchid()).throwMessage("商户号不能为空");
            Validators.notEmpty().test(installParam.getPayJSSecret()).throwMessage("API 密钥不能为空");
        }

    }

    public static void authParam(String username, String password) {
        Validators.notEmpty().test(username).throwMessage("用户名不能为空");
        Validators.notEmpty().test(password).throwMessage("密码不能为空");
        Validators.length(5, 20).test(username).throwMessage("用户名长度在 5-20 位");
        Validators.length(6, 20).test(password).throwMessage("密码长度在 6-20 位");
    }

    public static void orderParam(Order order) {
        Validators.notNull().test(order).throwMessage("订单参数缺失");
        Validators.notNull().test(order.getMoney()).throwMessage("订单参数缺失[money]");

        double minAmount = Utils.parseDouble(Bootstrap.getGlobalConfig().get(Constant.AMOUNT_MIN), 0.1D);
        double maxAmount = Utils.parseDouble(Bootstrap.getGlobalConfig().get(Constant.AMOUNT_MAX), 1000D);

        if (order.getMoney().doubleValue() < minAmount) {
            throw new ValidatorException(String.format("金额最小为 %d 元", minAmount));
        }
        if (order.getMoney().doubleValue() > maxAmount) {
            throw new ValidatorException("先生，请不要这样！");
        }

        if (StringKit.isNotEmpty(order.getPayEmail()) && !PatternKit.isEmail(order.getPayEmail())) {
            throw new ValidatorException("错误的邮箱格式");
        }
        Validators.notEmpty().test(order.getPayUser()).throwMessage("订单参数缺失[payUser]");
        Validators.notEmpty().test(order.getPayComment()).throwMessage("订单参数缺失[payComment]");
        Validators.length(1, 50).test(order.getPayUser()).throwMessage("昵称长度仅允许在 1-50 个字符");

        int min = Utils.parseInt(Bootstrap.getGlobalConfig().get(Constant.COMMENT_MIN_SIZE), 4);
        int max = Utils.parseInt(Bootstrap.getGlobalConfig().get(Constant.COMMENT_MAX_SIZE), 500);

        Validators.length(min, max).test(order.getPayComment()).throwMessage(String.format("留言长度仅允许在 %d-%d 个字符", min, max));

        if (EMOJI_PATTERN.matcher(order.getPayUser()).find()) {
            throw new ValidatorException("您的输入中包含了禁止处理的字符 :( 请重新输入");
        }

        if (EMOJI_PATTERN.matcher(order.getPayUser()).find()) {
            throw new ValidatorException("您的输入中包含了禁止处理的字符 :( 请重新输入");
        }

    }
}
