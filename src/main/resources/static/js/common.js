// 提交支付订单
function payOrder(options) {
    $.ajax({
        type: "POST",
        url: "/pay_order",
        data: options.data,
        headers: {
            'X-CSRF-TOKEN': document.head.querySelector("[name=csrf_token]").content
        },
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                return options.success && options.success(data.payload);
            } else {
                return options.failure && options.failure(data.msg);
            }
        },
        error: function (data) {
            alert('服务器错误');
            return false;
        }
    })
}

// 查询订单支付状态
var ck_timer;

function checkOrder(options) {
    ck_timer = setInterval(function () {
        _check(options)
    }, options.interval);
}

function _check(options) {
    $.getJSON('/check_order?tradeNo=' + options.tradeNo, function (data) {
        console.log(data);
        // 支付成功
        if (data.code === 200) {
            options.success && options.success();
        } else if (data.code === 300) {
            // 等待超时
            options.timeout && options.timeout(data.msg);
        } else if (data.code === -1000) {
            clearInterval(ck_timer);
        } else {
            options.error && options.error(data.msg);
        }
    });
}