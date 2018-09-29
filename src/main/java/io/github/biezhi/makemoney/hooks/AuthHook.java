package io.github.biezhi.makemoney.hooks;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;
import io.github.biezhi.makemoney.utils.Utils;

/**
 * 认证授权钩子
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Bean
public class AuthHook implements WebHook {

    @Override
    public boolean before(RouteContext context) {
        if (Utils.isBlackIP(context.address())) {
            context.text("You are forbidden to access :(");
            return false;
        }

        if (!context.uri().startsWith("/install") && !Utils.isInstall()) {
            context.redirect("/install");
            return false;
        }

        if (context.uri().startsWith("/admin/") && !Utils.isLogin()) {
            context.redirect("/login");
            return false;
        }

        return true;
    }

}
