layui.use(['form','jquery','jquery_cookie','layer'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

        form.on("submit(saveBtn)",function (data){

            //发送Ajax
            $.ajax({
                type:"post",
                url:ctx+"/user/setting",
                data:{
                    userName:data.field.userName,
                    phone:data.field.phone,
                    email:data.field.email,
                    trueName:data.field.trueName,
                    id:data.field.id
                },
                dataType:"json",
                success:function (msg){
                    if (msg.code == 200){
                        layer.msg("修改成功",function (){
                            //清空Cookie
                            $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                            $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                            $.removeCookie("trueName",{domain:"localhost",path:"/crm"});

                            window.parent.location.href=ctx+"/index";
                        });
                    }else {
                        layer.msg(msg.msg);
                    }
                }
            });

            return false;
        });
});