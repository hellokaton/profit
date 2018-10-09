package io.github.biezhi.profit.service;

import com.blade.exception.ValidatorException;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Inject;
import com.blade.kit.JsonKit;
import com.blade.kit.StringKit;
import io.github.biezhi.anima.core.AnimaQuery;
import io.github.biezhi.anima.enums.OrderBy;
import io.github.biezhi.anima.page.Page;
import io.github.biezhi.profit.bootstrap.Bootstrap;
import io.github.biezhi.profit.bootstrap.Constant;
import io.github.biezhi.profit.entities.model.Option;
import io.github.biezhi.profit.entities.model.Order;
import io.github.biezhi.profit.entities.param.OrderParam;
import io.github.biezhi.profit.entities.request.CallbackTrade;
import io.github.biezhi.profit.entities.request.CreateOrderRequest;
import io.github.biezhi.profit.entities.response.PayOrderResponse;
import io.github.biezhi.profit.enums.OrderStatus;
import io.github.biezhi.profit.enums.Platform;
import io.github.biezhi.profit.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.github.biezhi.anima.Anima.select;
import static io.github.biezhi.anima.Anima.update;
import static java.util.stream.Collectors.toMap;

/**
 * Make Money Service
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Bean
@Slf4j
public class MakeMoneyService {

    @Inject
    private OptionService optionService;

    public Page<Order> findOrders(OrderParam orderParam) {
        AnimaQuery<Order> query = select().from(Order.class);

        int page  = Utils.parseInt(orderParam.getP(), 1);
        int limit = Utils.parseInt(Bootstrap.getGlobalConfig().get(Constant.PAGE_SIZE), 10);

        if (page < 1) {
            page = 1;
        }

        if (null != orderParam.getStatus()) {
            query.where(Order::getOrderStatus, orderParam.getStatus());
        }
        if (StringKit.isNotEmpty(orderParam.getPlatform())) {
            query.where(Order::getPlatform, orderParam.getPlatform());
        }

        return query.order(Order::getCreated, OrderBy.DESC).page(page, limit);
    }

    public String findOption(String key) {
        Option option = select().from(Option.class).where(Option::getKey, key).one();
        return null != option ? option.getValue() : null;
    }

    public Map<String, String> findOptions() {
        List<Option> options = select().from(Option.class).all();
        if (null != options) {
            return options.stream()
                    .filter(option -> !option.getKey().startsWith("safe"))
                    .collect(toMap(Option::getKey, (Option::getValue)));
        }
        return Collections.emptyMap();
    }

    public PayOrderResponse createOrder(Order order) {
        order.setMoney(order.getMoney().multiply(new BigDecimal("100")));

        String orderNo = Utils.genOrderID();
        order.setOrderStatus(0);
        order.setPlatform(Platform.YOUZAN.toString().toLowerCase());
        order.setOrderNo(orderNo);
        order.setCreated(new Date());

        try {
            // 调用支付接口
            PayOrderResponse payOrderResponse = getPayOrderResponse(order);
            order.setTradeNo(payOrderResponse.getTradeNo());
            order.save();

            this.addCount();

            Constant.TRADE_NOS.add(payOrderResponse.getTradeNo());
            return payOrderResponse;
        } catch (Exception e) {
            log.error("保存订单异常", e);
            throw new ValidatorException("创建订单失败");
        }
    }

    private PayOrderResponse getPayOrderResponse(Order order) {
        if (Platform.YOUZAN.toString().equalsIgnoreCase(order.getPlatform())) {
            CreateOrderRequest createOrderRequest = new CreateOrderRequest();
            createOrderRequest.setPrice(order.getMoney().intValue());
            createOrderRequest.setQrName(order.getPayComment());

            return Bootstrap.payApi.createQrCode(createOrderRequest);
        }
        if (Platform.PAYJS.toString().equalsIgnoreCase(order.getPlatform())) {
            // TODO
        }
        throw new ValidatorException("暂不支持交易");
    }

    /**
     * 支付渠道支付回调
     *
     * @param platform 支付渠道
     * @param body     返回消息体
     */
    public void execCallback(Platform platform, String body) {
        if (Platform.YOUZAN.equals(platform)) {
            CallbackTrade callbackTrade = JsonKit.formJson(body, CallbackTrade.class);

            if (!callbackTrade.getTest() && "trade_TradePaid".equals(callbackTrade.getType())
                    && "PAID".equals(callbackTrade.getStatus())) {

                // 支付成功
                String msg = Utils.decode(callbackTrade.getMsg());
                log.info("有赞支付回调msg: {}", msg);

                int start = msg.indexOf("qr_id\"");
                int end   = msg.indexOf(",", start);

                String qrId = msg.substring((start + 7), end);
                updatePaySuccess(qrId, OrderStatus.PAY_SUCCESS.getStatus());
            }
        }
    }

    public void updatePaySuccess(String tradeNo, Integer orderStatus) {
        Order order = select().from(Order.class).where(Order::getTradeNo, tradeNo).one();
        if (null == order || OrderStatus.PAY_SUCCESS.getStatus() == order.getOrderStatus()) {
            return;
        }

        update().from(Order.class).set(Order::getOrderStatus, orderStatus).where(Order::getTradeNo, tradeNo).execute();

        // 总金额+money
        String totalAmount = findOption(Constant.TOTAL_AMOUNT);
        optionService.update(Constant.TOTAL_AMOUNT, new BigDecimal(totalAmount).add(order.getMoney()).toString());

        // 今天金额+money
        String todayAmount = findOption(Constant.TODAY_AMOUNT);
        optionService.update(Constant.TODAY_AMOUNT, new BigDecimal(todayAmount).add(order.getMoney()).toString());

    }

    private void addCount() {
        // 总笔数+1
        String totalCount = findOption(Constant.TOTAL_COUNT);
        optionService.update(Constant.TOTAL_COUNT, String.valueOf(new BigDecimal(totalCount).intValue() + 1));

        // 今天笔数+1
        String todayCount = findOption(Constant.TODAY_COUNT);
        optionService.update(Constant.TODAY_COUNT, String.valueOf(Integer.valueOf(todayCount) + 1));
    }

    public boolean checkOrderPaySuccess(String tradeNo) {
        if (Platform.YOUZAN.toString().equalsIgnoreCase(Bootstrap.getGlobalConfig().get(Constant.SAFE_PLATFORM))) {
            return Bootstrap.payApi.orderPaySuccess(tradeNo);
        }
        return false;
    }

}
