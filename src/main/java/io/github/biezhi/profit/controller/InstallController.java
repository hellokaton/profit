package io.github.biezhi.profit.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import io.github.biezhi.profit.bootstrap.Bootstrap;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Option;
import io.github.biezhi.profit.entities.param.InstallParam;
import io.github.biezhi.profit.service.MakeMoneyService;
import io.github.biezhi.profit.service.OptionService;
import io.github.biezhi.profit.thirdparty.PayApi;
import io.github.biezhi.profit.utils.Utils;
import io.github.biezhi.profit.verification.Validator;
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

    @Inject
    private OptionService optionService;

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
        optionService.updatePayConfig(installParam);

        optionService.save(Constant.TODAY_COUNT, "0");
        optionService.save(Constant.TOTAL_COUNT, "0");
        optionService.save(Constant.TODAY_AMOUNT, "0");
        optionService.save(Constant.TOTAL_AMOUNT, "0");
        optionService.save(Constant.PAGE_SIZE, "10");
        optionService.save(Constant.COMMENT_MIN_SIZE, "4");
        optionService.save(Constant.COMMENT_MAX_SIZE, "500");
        optionService.save(Constant.AMOUNT_MIN, "0.1");
        optionService.save(Constant.AMOUNT_MAX, "1000");

        Bootstrap.refreshConfig();
        Bootstrap.payApi = PayApi.getPayApi(installParam.getPlatform());

        File lock = new File(Utils.CLASSPATH + "install.lock");
        lock.createNewFile();

        return RestResponse.ok();
    }

}
