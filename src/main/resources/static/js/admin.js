new Vue({
    el: '#app',
    data: {
        error: '',
        config: {
            theme: '',
            platform: '',
            youzanClientId: '',
            youzanClientSecret: '',
            youzanShopId: '',
            payJSMchid: '',
            payJSSecret: ''
        }
    },
    mounted: function(){
        var vm = this;
        axios.get('/admin/config')
            .then(function (response) {
                var data = response.data;
                vm.config.theme = data.theme;
                vm.config.platform = data.safe_platform;
                vm.config.youzanClientId = data.safe_youzan_client_id;
                vm.config.youzanClientSecret = data.safe_youzan_client_secret;
                vm.config.youzanShopId = data.safe_youzan_shop_id;
                vm.config.payJSMchid = data.safe_payjs_mchid;
                vm.config.payJSSecret = data.safe_payjs_secret;
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
            .then(function () {
                // always executed
            });
    },
    methods: {
        doinstall: function () {
            var vm = this;
            if (vm.config.platform === 'youzan' && vm.config.youzanClientId === '') {
                vm.error = '请输入 ClientID';
                vm.$refs.youzanClientId.focus();
                return;
            }
            if (vm.config.platform === 'youzan' && vm.config.youzanClientSecret === '') {
                vm.error = '请输入 ClientSecret';
                vm.$refs.youzanClientSecret.focus();
                return;
            }
            if (vm.config.platform === 'youzan' && vm.config.youzanShopId === '') {
                vm.error = '请输入授权店铺 ID';
                vm.$refs.youzanShopId.focus();
                return;
            }

            if (vm.config.platform === 'payjs' && vm.config.payJSMchid === '') {
                vm.error = '请输入商户号';
                vm.$refs.payJSMchid.focus();
                return;
            }

            if (vm.config.platform === 'payjs' && vm.config.payJSSecret === '') {
                vm.error = '请输入API密钥';
                vm.$refs.payJSSecret.focus();
                return;
            }

            axios.post('/admin/config', vm.config).then(function (response) {
                var data = response.data;
                if (data.success) {
                    alert('配置保存成功！');
                    window.location.reload();
                } else {
                    console.log(data);
                    vm.error = data.msg || '保存失败!';
                }
            }).catch(function (error) {
                console.log(error);
            });
        },
        enableTheme: function (theme) {
            if(window.confirm('确定使用该主题吗？')){
                axios.post('/admin/theme/'+theme).then(function (response) {
                    var data = response.data;
                    if (data.success) {
                        alert('主题启用成功，请点击【查看网站】预览！');
                        window.location.reload();
                    } else {
                        vm.error = data.msg || '主题启用失败!';
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        }
    }
});

function showUpdatePwd() {
    document.querySelector('.modal').style = 'display: block;';
    // var mods = document.querySelectorAll('.modal > [type=checkbox]');
    // [].forEach.call(mods, function(mod){ mod.checked = false; });
}