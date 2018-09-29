package io.github.biezhi.makemoney.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import io.github.biezhi.makemoney.bootstrap.Constant;
import io.github.biezhi.makemoney.entities.model.Option;
import io.github.biezhi.makemoney.entities.param.InstallParam;
import io.github.biezhi.makemoney.service.MakeMoneyService;
import io.github.biezhi.makemoney.utils.Utils;
import io.github.biezhi.makemoney.verification.Validator;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

import static io.github.biezhi.anima.Anima.delete;

/**
 * 系统安装
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Slf4j
@Path
public class InstallController {

    @Inject
    private MakeMoneyService makeMoneyService;

    @GetRoute("install")
    public String install(Response response) {
        if (Utils.isInstall()) {
            response.redirect("/");
            return null;
        }
        return "install.html";
    }

    @PostRoute("install")
    @JSON
    public RestResponse doInstall(@BodyParam InstallParam installParam) throws IOException {
        if (Utils.isInstall()) {
            return RestResponse.fail("请勿重复安装!");
        }

        log.info("安装参数: {}", installParam);
        Validator.installParam(installParam);

        delete().from(Option.class).execute();
        makeMoneyService.updateConfig(installParam);

        makeMoneyService.saveOption(Constant.TODAY_COUNT, "0");
        makeMoneyService.saveOption(Constant.TODAY_AMOUNT, "0");
        makeMoneyService.saveOption(Constant.TOTAL_COUNT, "0");
        makeMoneyService.saveOption(Constant.TOTAL_AMOUNT, "0");

        File lock = new File(Utils.CLASSPATH + "install.lock");
        lock.createNewFile();

        return RestResponse.ok();
    }

}
