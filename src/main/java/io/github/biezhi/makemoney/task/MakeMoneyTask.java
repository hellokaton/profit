package io.github.biezhi.makemoney.task;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.task.annotation.Schedule;
import io.github.biezhi.makemoney.bootstrap.Constant;
import io.github.biezhi.makemoney.service.MakeMoneyService;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 *
 * @author biezhi
 * @date 2018/9/29
 */
@Bean
@Slf4j
public class MakeMoneyTask {

    @Inject
    private MakeMoneyService makeMoneyService;

    /**
     * 凌晨清空
     */
    @Schedule(cron = "0 0 0 * * ?")
    public void cleanCountAmount() {
        log.info("清空今日统计");
        makeMoneyService.updateOption(Constant.TODAY_COUNT, "0");
        makeMoneyService.updateOption(Constant.TODAY_AMOUNT, "0");

        Constant.TRADE_NOS.clear();
    }

}
