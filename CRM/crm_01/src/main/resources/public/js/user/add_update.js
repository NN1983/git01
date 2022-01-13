layui.use(['form', 'layer', 'formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
        formSelects = layui.formSelects;

    form.on("submit(addOrUpdateUser)",function (data){
        //判断添加还是修改
        var url = ctx + "/user/save";

        if ($("input[name=id]").val()){
            url = ctx + "/user/update";
        }

        //发送ajax添加
        $.post(url,data.field,function (result){
            if (result.code == 200){
                parent.location.reload();
            }else {
                layer.msg(result.msg,{icon : 5});
            }
        },"json");

        return false;
    });

    //关闭弹出层
    $("#closeBtn").click(function (){
        //得到当前iframe层的索引
        var index = parent.layer.getFrameIndex(window.name);

        parent.layer.close(index);
    });

    formSelects.config('selectId',{
        type:'post',
        searchUrl:ctx + '/role/findRoles',
        keyName:'roleName',
        keyVal:'id',
    },true);
});