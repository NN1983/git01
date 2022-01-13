layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    
    form.on('submit(login)',function (data){
        var fieldData = data.field;

        if(fieldData.username=='undefined' || fieldData.username==''){
            layer.msg("用户名不能为空");
            return;
        }

        if(fieldData.password=='undefined' || fieldData.password==''){
            layer.msg("密码不能为空");
            return;
        }

        //发送Ajax
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                "userName":fieldData.username,
                "userPwd":fieldData.password
            },
            dataType:"json",
            success:function (result){
                if(result.code==200){
                    layer.msg("登录成功！",function (){

                        //将用户信息存到cookie中
                        $.cookie("userIdStr",result.result.userIdStr);
                        $.cookie("userName",result.result.userName);
                        $.cookie("trueName",result.result.trueName);

                        //记住密码
                        if($("input[type='checkbox']").is(":checked")){
                            $.cookie("userIdStr",result.result.userIdStr,{expires:7});
                            $.cookie("userName",result.result.userName,{expires:7});
                            $.cookie("trueName",result.result.trueName,{expires:7});
                        }
                        window.location.href=ctx+"/main";
                    });
                }else {
                    layer.msg(result.msg);
                }
            }
        });
        return false;
    });
});