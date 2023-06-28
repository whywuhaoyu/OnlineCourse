$(function() {
    //用户点击登录按钮时
    $("#login-btn").click(function() {
        const account = $('#account').val();
        const password = $('#password').val();
        //如果用户勾选自动登录
        if($("#rememberMe").prop("checked")){
            //存入cookie
            //expires: 7 表示存储一个带7天期限的cookie
            $.cookie("rememberMe", "true",{
                expires: 7,
                sameSite: none


            });
            $.cookie("account", account,{
                path: '/',
                expires: 7
            });
            $.cookie("password", password, {
                expires: 7
            });
        }else{
            $.cookie("rememberMe", "false", {
                expires: -1
            });
            $.cookie("account", account, {
                expires: -1
            });
            $.cookie("password", password, {
                expires: -1
            });
        }
    })


    //登录页面加载时运行的代码
    //判断是否有记住密码的内容
    if ($.cookie("rememberMe") === "true") {
        $("#rememberMe").prop("checked", true);
        $("#account").val($.cookie("account"));
        $("#password").val($.cookie("password"));
    }
});

