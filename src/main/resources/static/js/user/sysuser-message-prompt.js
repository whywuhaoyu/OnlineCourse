$(function (){


    //判断账号是否合法
    function isPhoneNumber(phoneNumber) {
        const regExp = /^1\d{10}$/;
        return regExp.test(phoneNumber);
    }


    //判断密码是否合法
    function isValidPassword(password) {
        const regex = /^[a-zA-Z0-9_]\w{5,14}$/; // 以字母数字下划线开头，总长度6-15位
        return regex.test(password);
    }


    //判断专业名是否只包含中文
    function isChinese(str) {
        const reg = /^[\u4e00-\u9fa5]+$/;
        return reg.test(str);
    }



    /*用户登录步骤*/
    $("#login-btn").click(function () {
        const account=$("#account-login").val()
        const password=$("#password-login").val()
        if($.isEmptyObject(account)||$.isEmptyObject(password)){
            layer.msg("用户账号或密码不能为空！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['260px','66px'],
            });
        }else if(!isPhoneNumber(account)){
            layer.msg("用户账号不合法", {
                icon: 2,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else if(!isValidPassword(password)){
            layer.msg("密码输入不合法！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else{
            $.ajax({
                url: "login",
                type: "POST",
                data: $("#login-form").serialize(),
                dataType: "JSON",
                success: function (json) {
                    if (json.state === 200) {
                        layer.msg('登录成功！', {
                            icon: 6,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['80px','66px'],
                            end: function (){
                                location.href = json.data.view;
                                location.replace(json.data.view)
                            }
                        });
                    }else if(json.state === 4001){
                        layer.msg("用户账号不存在！", {
                            icon: 5,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['200px','66px'],
                        });
                    } else if(json.state === 4002){
                        layer.msg("用户密码错误！", {
                            icon: 2,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['100px','66px'],
                        });
                    }else if(json.state === 4003){
                        layer.msg("用户账号不可用！", {
                            icon: 7,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['200px','66px'],
                        });
                    }
                }
            });
        }

    });


    /*修改密码步骤*/
    $("#updatePwd-btn").click(function (){
        const account=$("#account-update").val()
        const password=$("#password-update").val()
        const confirm=$("#confirm").val()
        if($.isEmptyObject(account)||$.isEmptyObject(password)){
            layer.msg("用户账号或密码不能为空！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['260px','66px'],
            });
        }else if(!isValidPassword(password)){
            layer.msg("密码输入不合法！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }
        else if($.isEmptyObject(confirm)){
            layer.msg("请再次输入密码！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['200px','66px'],
            });
        }else if(password!==confirm){
            layer.msg("两次密码输入不一致！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else{
            $.ajax({
                url: "updatePassword",
                type: "POST",
                data: {
                    account:account,
                    password:password
                },
                dataType: "JSON",
                success: function (json) {
                    if (json.state === 200) {
                        layer.msg('修改成功！', {
                            icon: 6,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['80px','66px'],
                            end: function (){
                                location.href = "toLogin";
                            }
                        });
                    }else if(json.state === 4001){
                        layer.msg("用户账号不存在！", {
                            icon: 5,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['200px','66px'],
                        });
                    }else if(json.state === 5001){
                        layer.msg("修改失败！", {
                            icon: 5,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['80px','66px'],
                        });
                    }
                }
            });
        }
    })


    /*用户注册步骤*/
    $("#register-btn").click(function (){
        const account=$("#account-register").val()
        const username=$("#username").val()
        const type=$("#type").val()
        const major=$("#major").val()
        const password=$("#password-register").val()
        const confirm=$("#confirm").val()
        if($.isEmptyObject(account)||$.isEmptyObject(username)||
            $.isEmptyObject(major)||$.isEmptyObject(password)||
            $.isEmptyObject(confirm)){
            layer.msg("输入不能为空！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['180px','66px'],
            });
        }else if(!isPhoneNumber(account)){
            layer.msg("请输入正确的手机号！", {
                icon: 2,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else if(username.length <= 2){
            layer.msg("用户名长度不能小于等于2！", {
                icon: 7,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['250px','66px'],
            });
        }else if(username.length > 10){
            layer.msg("用户名长度不能超过10！", {
                icon: 7,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['230px','66px'],
            });
        }else if(!isChinese(major)){
            layer.msg("专业名只能包含中文字符！", {
                icon: 7,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['260px','66px'],
            });
        }else if(!isValidPassword(password)){
            layer.msg("密码输入不合法！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else if(password!==confirm){
            layer.msg("两次密码输入不一致！", {
                icon: 5,
                time: 1000,
                shade: [0.4, '#393D49'],
                area:['220px','66px'],
            });
        }else{
            $.ajax({
                url: "register",
                type: "POST",
                data:{
                    account:account,
                    username:username,
                    type:type,
                    major:major,
                    password:password
                },
                dataType: "JSON",
                success: function (json) {
                    if (json.state === 200) {
                        layer.msg('注册成功！', {
                            icon: 6,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['80px','66px'],
                            end: function (){
                                location.href = "toLogin";
                            }
                        });
                    }else if(json.state === 3009){
                        layer.msg("用户名已被占用！", {
                            icon: 5,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['200px','66px'],
                        });
                    }else if(json.state === 4000){
                        layer.msg("用户账号已存在！", {
                            icon: 7,
                            time: 1000,
                            shade: [0.4, '#393D49'],
                            area:['200px','66px'],
                        });
                    }
                }
            });
        }
    })

})
