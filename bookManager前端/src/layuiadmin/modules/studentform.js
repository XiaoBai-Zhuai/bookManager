layui.define(['index', 'form', 'upload'], function () {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        form = layui.form,
        layer = layui.layer,
        setter = layui.setter;
    
    layer.ready(function() {
        $.ajax({
            url: setter.baseURL + 'getAllClassList',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                var data = res.data;
                var className = $("#util").val();
                for (i = 0; i < data.length; i++) {
                    $("#className").append(new Option(data[i].name, data[i].name));
                    if(className != undefined && className == data[i].name) {
                        $("#className").get(0).selectedIndex = i + 1;
                    }
                }
                form.render();
            }
        });
    });

    exports('studentform', {})
});