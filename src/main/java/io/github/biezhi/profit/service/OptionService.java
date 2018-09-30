package io.github.biezhi.profit.service;

import com.blade.ioc.annotation.Bean;
import com.blade.kit.PasswordKit;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Option;
import io.github.biezhi.profit.entities.param.InstallParam;
import io.github.biezhi.profit.enums.Platform;

import java.util.Map;
import java.util.Set;

/**
 * 配置项 Service
 *
 * @author biezhi
 * @date 2018/9/30
 */
@Bean
public class OptionService {

    public void updatePayConfig(InstallParam installParam) {
        // 保存到数据库
        if (installParam.isUpdate()) {
            this.update(Constant.SAFE_PLATFORM, installParam.getPlatform());

            if (Platform.YOUZAN.toString().equalsIgnoreCase(installParam.getPlatform())) {
                this.update(Constant.SAFE_YOUZAN_CLIENT_ID, installParam.getYouzanClientId());
                this.update(Constant.SAFE_YOUZAN_CLIENT_SECRET, installParam.getYouzanClientSecret());
                this.update(Constant.SAFE_YOUZAN_SHOP_ID, installParam.getYouzanShopId());
            }

            if (Platform.PAYJS.toString().equalsIgnoreCase(installParam.getPlatform())) {
                this.update(Constant.SAFE_PAYJS_MCHID, installParam.getPayJSMchid());
                this.update(Constant.SAFE_PAYJS_SECRET, installParam.getPayJSSecret());
            }
        } else {
            this.save(Constant.SAFE_USERNAME, installParam.getUsername());

            String pass = PasswordKit.hashPassword(installParam.getPassword());
            this.save(Constant.SAFE_PASSWORD, pass);

            this.save(Constant.SAFE_PLATFORM, installParam.getPlatform());
            this.save(Constant.CURRENT_THEME, installParam.getTheme());

            if (Platform.YOUZAN.toString().equalsIgnoreCase(installParam.getPlatform())) {
                this.save(Constant.SAFE_YOUZAN_CLIENT_ID, installParam.getYouzanClientId());
                this.save(Constant.SAFE_YOUZAN_CLIENT_SECRET, installParam.getYouzanClientSecret());
                this.save(Constant.SAFE_YOUZAN_SHOP_ID, installParam.getYouzanShopId());
                this.save(Constant.SAFE_PAYJS_MCHID, "");
                this.save(Constant.SAFE_PAYJS_SECRET, "");
            }

            if (Platform.PAYJS.toString().equalsIgnoreCase(installParam.getPlatform())) {
                this.save(Constant.SAFE_PAYJS_MCHID, installParam.getPayJSMchid());
                this.save(Constant.SAFE_PAYJS_SECRET, installParam.getPayJSSecret());
                this.save(Constant.SAFE_YOUZAN_CLIENT_ID, "");
                this.save(Constant.SAFE_YOUZAN_CLIENT_SECRET, "");
                this.save(Constant.SAFE_YOUZAN_SHOP_ID, "");
            }
        }
    }

    public void updateOptions(Map<String, String> options) {
        if (null == options || options.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = options.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            this.update(entry.getKey(), entry.getValue());
        }
    }

    public void save(String key, String value) {
        Option option = new Option();
        option.setKey(key);
        option.setValue(value);
        option.save();
    }

    public void update(String key, String value) {
        Option option = new Option();
        option.setKey(key);
        option.setValue(value);
        option.update();
    }

}
