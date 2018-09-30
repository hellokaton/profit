package io.github.biezhi.profit.bootstrap;

import com.blade.Blade;
import com.blade.ioc.annotation.Bean;
import com.blade.kit.JsonKit;
import com.blade.kit.json.GsonSupport;
import com.blade.loader.BladeLoader;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import io.github.biezhi.anima.Anima;
import io.github.biezhi.profit.controller.IndexController;
import io.github.biezhi.profit.entities.model.Option;
import io.github.biezhi.profit.thirdparty.PayApi;
import io.github.biezhi.profit.utils.DBUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.biezhi.anima.Anima.select;
import static io.github.biezhi.profit.bootstrap.Constant.CURRENT_THEME;
import static io.github.biezhi.profit.bootstrap.Constant.SAFE_PLATFORM;
import static java.util.stream.Collectors.toMap;

/**
 * 启动配置引导
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Bean
public class Bootstrap implements BladeLoader {

    /**
     * 缓存 options 配置，不对外使用
     */
    private static Map<String, String> GLOBAL_CONFIG = new HashMap<>();

    public static PayApi payApi;

    @Override
    public void load(Blade blade) {

        // 使用 gson 作为 JSON 支持
        JsonKit.jsonSupprt(new GsonSupport());

        DBUtils.importSql(blade.devMode());
        Anima.open(DBUtils.DB_SRC);

        JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
        templateEngine.getGlobalResolver().registerFunctions(TemplateExt.class);
        blade.templateEngine(templateEngine);

        // 初始化主题、全局变量信息
        Option theme = select().from(Option.class).where(Option::getKey, CURRENT_THEME).one();
        if (null != theme) {
            IndexController.THEME_NAME = theme.getValue();
        }
        List<Option> all = select().from(Option.class).all();
        if (null != all) {
            GLOBAL_CONFIG = all.stream().collect(toMap(Option::getKey, (Option::getValue)));
        }
        payApi = PayApi.getPayApi(GLOBAL_CONFIG.get(SAFE_PLATFORM));
    }

    public static Map<String, String> getGlobalConfig(){
        return GLOBAL_CONFIG;
    }

    public static void refreshConfig(){
        GLOBAL_CONFIG = select().from(Option.class).all().stream()
                .collect(toMap(Option::getKey, (Option::getValue)));
    }

}
