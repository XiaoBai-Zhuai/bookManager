layui.define(['index', 'set'], function (exports) {
    var $ = layui.$,
        form = layui.form,
        layer = layui.layer,
        setter = layui.setter;

    layer.ready(function () {
        document.getElementById('username').setAttribute('value', sessionStorage.getItem('username'));
        document.getElementById('nickname').setAttribute('value', sessionStorage.getItem('nickname'));
        document.getElementById('email').setAttribute('value', sessionStorage.getItem('email') == null ? '' :
            sessionStorage.getItem('email'));
        document.getElementById('tel').setAttribute('value', sessionStorage.getItem('tel') == null ?
            '' : sessionStorage.getItem('tel'));

        var sex = sessionStorage.getItem('sex') == '男' ? 'man' : 'woman';
        document.getElementById(sex).setAttribute('checked', "true");

        var roleName = sessionStorage.getItem("roleName");
        switch (roleName) {
            case '管理员':
                document.getElementById("option1").setAttribute("selected", "selected");
                document.getElementById("option2").setAttribute("disabled", "disabled");
                document.getElementById("option3").setAttribute("disabled", "disabled");
                document.getElementById("option4").setAttribute("disabled", "disabled");
                break;
            case '学院负责人':
                document.getElementById("option2").setAttribute("selected", "selected");
                document.getElementById("option1").setAttribute("disabled", "disabled");
                document.getElementById("option3").setAttribute("disabled", "disabled");
                document.getElementById("option4").setAttribute("disabled", "disabled");
                break;
            case '班级负责人':
                document.getElementById("option3").setAttribute("selected", "selected");
                document.getElementById("option2").setAttribute("disabled", "disabled");
                document.getElementById("option1").setAttribute("disabled", "disabled");
                document.getElementById("option4").setAttribute("disabled", "disabled");
                break;
            case '普通学生':
                document.getElementById("option4").setAttribute("selected", "selected");
                document.getElementById("option2").setAttribute("disabled", "disabled");
                document.getElementById("option3").setAttribute("disabled", "disabled");
                document.getElementById("option1").setAttribute("disabled", "disabled");
                break;
        }
        form.render();
    })

    form.on('submit(setmyinfo)', function (obj) {
        // layer.msg(JSON.stringify(obj.field));

        $.ajax({
            url: setter.baseURL + 'updateUserInfo',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            data: JSON.stringify(obj.field),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function (res) {
                sessionStorage.setItem("nickname", res.user.nickname);
                sessionStorage.setItem("sex", res.user.sex);
                sessionStorage.setItem("email", res.user.email);
                sessionStorage.removeItem("tel");
                sessionStorage.setItem("tel", res.user.tel);
                form.render();
                layer.msg("修改成功！", {
                    offset: '15px',
                    icon: 1,
                    time: 1000
                }, function () {
                    parent.location.reload();
                });
            }
        })
    });

    exports('userinfo', {})
});