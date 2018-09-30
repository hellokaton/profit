package io.github.biezhi.profit.entities.model;

import io.github.biezhi.anima.Model;
import io.github.biezhi.anima.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author biezhi
 * @date 2018/9/28
 */
@Data
@Table(pk = "order_no")
public class Order extends Model {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 付款用户名
     */
    private String payUser;

    /**
     * 付款用户邮箱
     */
    private String payEmail;

    /**
     * 付款留言
     */
    private String payComment;

    /**
     * 支付渠道订单唯一标识
     */
    private String tradeNo;

    /**
     * 支付渠道
     * youzan/payjs
     */
    private String platform;

    /**
     * 支付金额，单位：分
     */
    private BigDecimal money;

    /**
     * 订单状态
     * <p>
     * 0：待支付  1：支付成功  2：支付失败
     */
    private Integer orderStatus;

    /**
     * 订单创建时间
     */
    private Date created;

}