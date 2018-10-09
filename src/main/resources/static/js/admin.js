new Vue({
    el: "#app",
    data: {
        error: "",
        theme: "",
        common: {
            page_size: "10",
            comment_min_size: "4",
            comment_max_size: "500",
            amount_min: "0.1",
            amount_max: "1000"
        },
        payConfig: {
            platform: "",
            youzanClientId: "",
            youzanClientSecret: "",
            youzanShopId: "",
            payJSMchid: "",
            payJSSecret: ""
        }
    },
    mounted: function () {
        var vm = this;
        axios.get("/admin/options")
            .then(function (response) {
                var data = response.data;
                vm.theme = data.theme;
                vm.payConfig.platform = data.safe_platform;
                vm.payConfig.youzanClientId = data.safe_youzan_client_id;
                vm.payConfig.youzanClientSecret = data.safe_youzan_client_secret;
                vm.payConfig.youzanShopId = data.safe_youzan_shop_id;
                vm.payConfig.payJSMchid = data.safe_payjs_mchid;
                vm.payConfig.payJSSecret = data.safe_payjs_secret;

                vm.common.page_size = data.page_size;
                vm.common.comment_min_size = data.comment_min_size;
                vm.common.comment_max_size = data.comment_max_size;
                vm.common.amount_min = data.amount_min;
                vm.common.amount_max = data.amount_max;
            })
            .catch(function (error) {
                console.log(error);
            });
    },
    methods: {
        savePayConfig: function () {
            var vm = this;
            if (vm.payConfig.platform === "youzan" && vm.payConfig.youzanClientId === "") {
                vm.error = "请输入 ClientID";
                vm.$refs.youzanClientId.focus();
                return;
            }
            if (vm.payConfig.platform === "youzan" && vm.payConfig.youzanClientSecret === "") {
                vm.error = "请输入 ClientSecret";
                vm.$refs.youzanClientSecret.focus();
                return;
            }
            if (vm.payConfig.platform === "youzan" && vm.payConfig.youzanShopId === "") {
                vm.error = "请输入授权店铺 ID";
                vm.$refs.youzanShopId.focus();
                return;
            }

            if (vm.payConfig.platform === "payjs" && vm.payConfig.payJSMchid === "") {
                vm.error = "请输入商户号";
                vm.$refs.payJSMchid.focus();
                return;
            }

            if (vm.payConfig.platform === "payjs" && vm.payConfig.payJSSecret === "") {
                vm.error = "请输入API密钥";
                vm.$refs.payJSSecret.focus();
                return;
            }

            axios.post("/admin/pay_config", vm.payConfig).then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert("配置保存成功！");
                    window.location.reload();
                } else {
                    console.log(data);
                    vm.error = data.msg || "保存失败!";
                }
            }).catch(function (error) {
                console.log(error);
            });
        },
        saveCommonConfig: function () {
            var vm = this;
            axios.post("/admin/common_config", vm.common).then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert("配置保存成功！");
                    window.location.reload();
                } else {
                    console.log(data);
                    vm.error = data.msg || "保存失败!";
                }
            }).catch(function (error) {
                console.log(error);
            });
        },
        enableTheme: function (theme) {
            if (window.confirm("确定使用该主题吗？")) {
                axios.post("/admin/theme/" + theme).then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert("主题启用成功，请点击【查看网站】预览！");
                        window.location.reload();
                    } else {
                        vm.error = data.msg || "主题启用失败!";
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        }
    }
});

function showUpdatePwd() {
    document.querySelector(".modal").style = "display: block;";
}