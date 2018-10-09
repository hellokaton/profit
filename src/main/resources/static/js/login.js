new Vue({
    el: "#app",
    data: {
        error: "",
        login: {
            username: "",
            password: ""
        }
    },
    methods: {
        dologin: function () {
            var vm = this;
            if (vm.login.username === "") {
                vm.error = "请输入管理员账号";
                vm.$refs.username.focus()
                return;
            }
            if (vm.login.password === "") {
                vm.error = "请输入密码";
                vm.$refs.password.focus()
                return;
            }

            axios.post("/login", vm.login).then(function (response) {
                var data = response.data;
                if (data.success) {
                    window.location.href = "/admin/index";
                } else {
                    console.log(data);
                    vm.error = data.msg || "登录失败!";
                }
            }).catch(function (error) {
                console.log(error);
            });
        }
    }
});