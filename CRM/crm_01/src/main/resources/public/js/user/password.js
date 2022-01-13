layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on("submit(saveBtn)",function (data){
        var fieldData = data.field;

        $.ajax({
            type:"post",
            url:ctx+"/user/updatePwd",
            data:{
                "oldPassword":fieldData.old_password,
                "newPassword":fieldData.new_password,
                "confirmPwd":fieldData.again_password
            },
            dataType:"json",
            success:function (data){
                if (data.code == 200){
                    layer.msg("密码修改成功，系统三秒钟后退出",function (){
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"});

                        window.parent.location.href=ctx+"/index";
                    })
                }else {
                    layer.msg(data.msg);
                }
            }
        });
        return false;
    });
});