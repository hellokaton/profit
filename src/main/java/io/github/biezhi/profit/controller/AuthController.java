package io.github.biezhi.profit.controller;

import com.blade.kit.PasswordKit;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Session;
import com.blade.mvc.ui.RestResponse;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Option;
import io.github.biezhi.profit.entities.param.LoginParam;
import io.github.biezhi.profit.verification.Validator;

import java.util.Base64;

import static io.github.biezhi.anima.Anima.select;

/**
 * 后台管理页面
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Path
public class AuthController {

    @GetRoute("login")
    public String login() {
        return "login.html";
    }

    @PostRoute("login")
    @JSON
    public RestResponse doLogin(@BodyParam LoginParam loginParam, Session session) {
        Validator.authParam(loginParam.getUsername(), loginParam.getPassword());

        long username = select().from(Option.class)
                .where(Option::getKey, Constant.SAFE_USERNAME)
                .and(Option::getValue, loginParam.getUsername())
                .count();

        if (username == 0) {
            return RestResponse.fail("要饭先把名字输对，O不OK？");
        }

        Option option = select().from(Option.class)
                .where(Option::getKey, Constant.SAFE_PASSWORD).one();

        String base64 = Base64.getEncoder().encodeToString(option.getValue().getBytes());

        boolean success = PasswordKit.checkPassword(loginParam.getPassword(),
                new String(Base64.getDecoder().decode(base64)));
        if (success) {
            session.attribute(Constant.LOGIN_SESSION_KEY, username);
            return RestResponse.ok();
        }
        return RestResponse.fail("密码都输不对的人没资格要饭 (σ｀д′)σ");
    }

}
