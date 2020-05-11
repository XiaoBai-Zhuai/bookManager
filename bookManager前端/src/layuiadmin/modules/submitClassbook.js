layui.define(['index', 'form', 'laydate'], function (exports) {
    var $ = layui.$,
        admin = layui.admin,
        element = layui.element,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form,
        setter = layui.setter;


    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#component-form-demo1").trigger('click');
            return false;
        }
    });

    laydate.render({
        elem: '#receiveDate' //指定元素
    });

    // form.render(null, 'component-form-group');

    document.getElementById("school").setAttribute("value", sessionStorage.getItem("schoolName"));
    document.getElementById("major").setAttribute("value", sessionStorage.getItem("majorName"));
    document.getElementById("class").setAttribute("value", sessionStorage.getItem("className"));

    layer.ready(function () {
        var data = {
            "majorName": sessionStorage.getItem("majorName")
        };
        $.ajax({
            url: setter.baseURL + 'getBookListByMajor',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    document.getElementById("bookName").append(new Option(data[i]
                        .name, data[i].name));
                }
                form.render();
            }
        })
    });

    /* 监听提交 */
    form.on('submit(component-form-demo1)', function (data) {

        var field = data.field;

        $.ajax({
            url: setter.baseURL + 'submitClassBook',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            data: JSON.stringify(field),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                layer.msg("提交成功！", {
                    offset: '15px',
                    icon: 1,
                    time: 1000
                }, function () {
                    location.reload();
                });
            }
        });
        return false;
    });

    exports('submitClassbook', {})
});