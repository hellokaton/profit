new Vue({
    el: "#app",
    data: {
        error: "",
        install: {
            theme: 'default',
            platform: 'youzan',
            username: "",
            password: "",
            youzanClientId: "",
            youzanClientSecret: "",
            youzanShopId: "",
            payJSMchid: "",
            payJSSecret: ""
        }
    },
    methods: {
        doinstall: function () {
            var vm = this;
            if (vm.install.username === "") {
                vm.error = "请输入管理员账号";
                vm.$refs.username.focus()
                return;
            }
            if (vm.install.password === "") {
                vm.error = "请输入密码";
                vm.$refs.password.focus()
                return;
            }
            if (vm.install.platform === "youzan" && vm.install.youzanClientId === "") {
                vm.error = "请输入 ClientID";
                vm.$refs.youzanClientId.focus()
                return;
            }
            if (vm.install.platform === "youzan" && vm.install.youzanClientSecret === "") {
                vm.error = "请输入 ClientSecret";
                vm.$refs.youzanClientSecret.focus()
                return;
            }
            if (vm.install.platform === "youzan" && vm.install.youzanShopId === "") {
                vm.error = "请输入授权店铺 ID";
                vm.$refs.youzanShopId.focus()
                return;
            }

            if (vm.install.platform === "payjs" && vm.install.payJSMchid === "") {
                vm.error = "请输入商户号";
                vm.$refs.payJSMchid.focus()
                return;
            }

            if (vm.install.platform === "payjs" && vm.install.payJSSecret === "") {
                vm.error = "请输入API密钥";
                vm.$refs.payJSSecret.focus()
                return;
            }

            axios.post("/install", vm.install).then(function (response) {
                var data = response.data;
                if (data.success) {
                    window.location.href = "/login";
                } else {
                    console.log(data);
                    vm.error = data.msg || "安装失败!";
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    }
});