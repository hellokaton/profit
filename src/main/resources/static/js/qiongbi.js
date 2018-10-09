$(function () {
    $("#customAmount").keyup(function () {
        var amount = $(this).val()
        amount = amount.replace(/\D|^0/g, "")
        $(this).val(amount)

        $("#amount").val(amount)
    }).bind("paste", function () {
        $(this).val($(this).val().replace(/\D|^0/g, ""))
    })

    $(".amount").click(function () {
        $(this).addClass("checked").siblings().removeClass("checked")
        $("#amount").val($(this).data("amount"))
    });

    // submit
    $(".alms-form").submit(function (e) {
        e.preventDefault();
        if ($("#username").val() === "") {
            showmsg("做好事请留名", "error")
            $("#username").focus();
            return false
        }
        if ($("#email").val() === "") {
            showmsg("请输入Email地址", "error")
            $("#email").focus();
            return false
        }
        var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,20}$/;
        if (!reg.test($("#email").val())) {
            alert("请输入有效的Email地址")
            $(this).val("").focus();
            return false;
        }
        if ($("#payComment").val() === "") {
            showmsg("请输入留言", "error")
            $("#payComment").focus();
            return false;
        }

        var params = $(this).serialize();
        payOrder({
            data: params,
            success: function (data) {
                $(".payment").show().find("img").attr("src", data.qrImg);
                $(".price .value").text($('#amount').val());

                checkOrder({
                    interval: 3000,
                    tradeNo: data.tradeNo,
                    success: function () {
                        showmsg("支付成功，感谢你的打赏 (づ￣ 3￣)づ", "info")
                        setTimeout(function () {
                            location.href = "/"
                        }, 3000)
                    },
                    timeout: function (msg) {
                        // 等待超时
                        showmsg(msg, "error");
                        setTimeout(function () {
                            window.location.href = "/"
                        }, 3000)
                    }
                });
            },
            failure: function (msg) {
                showmsg(msg, "error")
            }
        });

        return false;
    });
});

function showmsg(msg, type) {
    msgstr = '<div class="msg-wrap"><div class="msg"><span class="ico">';
    if (type === "error") {
        msgstr = msgstr + "!"
    } else {
        msgstr = msgstr + "√"
    }
    msgstr = msgstr + '</span><span class="txt">' + msg + '</span></div></div>';
    $(".alms-form").append(msgstr)
    setTimeout(function () {
        $(".msg-wrap").remove()
    }, 1500)
}