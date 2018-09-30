package io.github.biezhi.profit.task;

import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.task.annotation.Schedule;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.service.OptionService;
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
    private OptionService optionService;

    /**
     * 凌晨清空
     */
    @Schedule(name = "cleanTask", cron = "0 0 0 * * ?")
    public void cleanCountAmount() {
        log.info("清空今日统计");
        optionService.update(Constant.TODAY_COUNT, "0");
        optionService.update(Constant.TODAY_AMOUNT, "0");

        Constant.TRADE_NOS.clear();
    }

}
