package io.github.biezhi.profit;

import com.blade.Blade;
import com.blade.security.web.csrf.CsrfMiddleware;
import com.blade.security.web.csrf.CsrfOption;
import io.github.biezhi.profit.verification.XssMiddleware;

/**
 * Profit Main
 *
 * @author biezhi
 * @date 2018/9/28
 */
public class Application {

    public static void main(String[] args) {
        CsrfOption csrfOption = CsrfOption.builder().build();
        csrfOption.getUrlExclusions().add("/callback/youzan");
        csrfOption.getUrlExclusions().add("/callback/payjs");

        Blade.of()
                .use(new CsrfMiddleware(csrfOption))
                .use(new XssMiddleware())
                .start(Application.class, args);
    }

}
