package io.github.biezhi.profit.bootstrap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 *
 * @author biezhi
 * @date 2018/9/28
 */
public interface Constant {

    /**
     * HTTP 接口超时时间，单位：毫秒
     */
    int HTTP_TIME_OUT = 10_000;

    /**
     * make money 数据库名
     */
    String DB_NAME = "profit.db";

    /**
     * 登录用户 session key
     */
    String LOGIN_SESSION_KEY = "login_user";

    /**
     * 当前使用的主题模板
     */
    String CURRENT_THEME = "theme";

    /**
     * 当前使用的支付渠道
     */
    String SAFE_PLATFORM = "safe_platform";

    /**
     * 管理员用户名
     */
    String SAFE_USERNAME = "safe_username";

    /**
     * 管理员密码
     */
    String SAFE_PASSWORD = "safe_password";

    /**
     * 有赞 clientId
     */
    String SAFE_YOUZAN_CLIENT_ID = "safe_youzan_client_id";

    /**
     * 有赞 clientSecret
     */
    String SAFE_YOUZAN_CLIENT_SECRET = "safe_youzan_client_secret";

    /**
     * 有赞店铺 ID
     */
    String SAFE_YOUZAN_SHOP_ID = "safe_youzan_shop_id";

    /**
     * payjs 商户号
     */
    String SAFE_PAYJS_MCHID = "safe_payjs_mchid";

    /**
     * payjs API 密钥
     */
    String SAFE_PAYJS_SECRET = "safe_payjs_secret";

    /**
     * 今天收到的金额
     */
    String TODAY_AMOUNT = "today_amount";

    /**
     * 今天收到的订单数
     */
    String TODAY_COUNT = "today_count";

    /**
     * 总共收到的金额
     */
    String TOTAL_AMOUNT = "total_amount";

    /**
     * 总共收到的订单数
     */
    String TOTAL_COUNT = "total_count";

    /**
     * 每页条数
     */
    String PAGE_SIZE = "page_size";

    /**
     * 留言最小字数
     */
    String COMMENT_MIN_SIZE = "comment_min_size";

    /**
     * 留言最大字数
     */
    String COMMENT_MAX_SIZE = "comment_max_size";

    /**
     * 最小打赏金额
     */
    String AMOUNT_MIN = "amount_min";

    /**
     * 最大打赏金额
     */
    String AMOUNT_MAX = "amount_max";

    /**
     * 查询等待查询次数，20次每次 3 秒，共 1 分钟
     */
    int MAX_WAIT_COUNT = 20;

    /**
     * 缓存已提交的订单号，每日凌晨清空避免堆积
     */
    Set<String> TRADE_NOS = new HashSet<>();

    /**
     * 记录每个订单等待次数
     */
    Map<String, Integer> TRADE_CHECK_COUNT = new ConcurrentHashMap<>();

}