package io.github.biezhi.makemoney;

import com.blade.Blade;
import com.blade.security.web.csrf.CsrfMiddleware;
import com.blade.security.web.csrf.CsrfOption;

/**
 * 要饭启动
 *
 * @author biezhi
 * @date 2018/9/28
 */
public class Application {

    public static void main(String[] args) {
        CsrfOption csrfOption = CsrfOption.builder().build();
        csrfOption.getUrlExclusions().add("/callback/youzan");
        csrfOption.getUrlExclusions().add("/callback/payjs");

        Blade.of().use(new CsrfMiddleware(csrfOption)).start(Application.class, args);
    }

}
