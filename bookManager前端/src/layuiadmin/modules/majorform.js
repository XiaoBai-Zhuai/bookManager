layui.define(['index', 'form', 'upload'], function (exports) {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        form = layui.form,
        setter = layui.setter,
        layer = layui.layer;

    layer.ready(function () {
        $.ajax({
            url: setter.baseURL + 'getAllSchoolList',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                var schoolName = $("#util").val();
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#schoolName").append(new Option(data[i].name), data[i].name);
                    if(schoolName != undefined && schoolName == data[i].name) {
                        $("#schoolName").get(0).selectedIndex = i + 1;
                    }
                }
                form.render();
            }
        });
    });

    exports('majorform', {})
});