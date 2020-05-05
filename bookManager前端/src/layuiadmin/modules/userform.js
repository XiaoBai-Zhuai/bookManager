layui.define(['index', 'form', 'upload', 'tree'], function () {
    var $ = layui.$,
        form = layui.form,
        upload = layui.upload,
        setter = layui.setter,
        layer = layui.layer;

    form.on('select(LAY-user-adminrole-type)', function (data) {
        var value = data.value;
        if (value == '管理员') {
            document.getElementById("workPlace_t").style.display = "none";
            document.getElementById("workPlace_s").style.display = "none";
            document.getElementById("workPlace_m").style.display = "none";
        }
        if (value == '普通学生' || value == '班级负责人') {
            document.getElementById("workPlace_t").style.display = "block";
            document.getElementById("workPlace_m").style.display = "block";
            document.getElementById("workPlace_s").style.display = "block";
        }
        if (value == '学院负责人') {
            document.getElementById("workPlace_t").style.display = "block";
            document.getElementById("workPlace_s").style.display = "none";
            document.getElementById("workPlace_m").style.display = "none";
        }
    });

    layer.ready(function () {

        document.getElementById("workPlace_t").style.display = "block";
        document.getElementById("workPlace_s").style.display = "none";
        document.getElementById("workPlace_m").style.display = "none";

        $("#schoolName").find("option").remove();
        $("#schoolName").append(new Option("", ""));
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
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    document.getElementById("schoolName").append(new Option(data[i]
                        .name, data[i].name));
                }
                form.render();
            }
        });
    });


    form.on('select(schoolName)', function (data) {
        $.ajax({
            url: setter.baseURL + 'getMajorListBySchoolName',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            data: JSON.stringify({
                'schoolName': data.value
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                $("#majorName").find('option').remove();
                $("#majorName").append(new Option("", ""));
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#majorName").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    });

    form.on('select(majorName)', function (data) {
        $.ajax({
            url: setter.baseURL + 'getAllClassListByMajorName',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            data: JSON.stringify({
                'majorName': data.value,
                'schoolName': $("#schoolName").val()
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                $("#className").find('option').remove();
                $("#className").append(new Option("", ""));
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#className").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    });

    exports('userform', {});
});