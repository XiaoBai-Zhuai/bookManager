layui.define(['index', 'form', 'upload'], function () {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        form = layui.form,
        layer = layui.layer,
        setter = layui.setter;

    layer.ready(function () {
        $.ajax({
            url: setter.baseURL + 'getAllMajorList',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                var data = res.data;
                var majorName = $("#util").val();
                for (i = 0; i < data.length; i++) {
                    $("#majorName").append(new Option(data[i].name, data[i].name));
                    if(majorName != undefined && majorName == data[i].name) {
                        $("#majorName").get(0).selectedIndex = i + 1;
                    }
                }
                form.render();
            }
        });
    });

    exports('classform', {})
});