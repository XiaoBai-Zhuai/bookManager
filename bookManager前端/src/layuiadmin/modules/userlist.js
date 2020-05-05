layui.define(['index', 'useradmin', 'table'], function () {
    var $ = layui.$,
        form = layui.form,
        table = layui.table,
        layer = layui.layer,
        setter = layui.setter;

    layer.ready(function () {
        $.ajax({
            url: setter.baseURL + 'getAllUserList',
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
                    $("#username").append(new Option(data[i].username, data[i].username));
                    $("#email").append(new Option(data[i].email, data[i].email));
                }
                form.render();
            }
        });
        $.ajax({
            url: setter.baseURL + 'getDUserNickname',
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
                    $("#nickname").append(new Option(data[i].nickname, data[i].nickname));
                }
                form.render();
            }
        });
    });

    //监听搜索
    form.on('submit(LAY-user-front-search)', function (data) {
        var field = data.field;
        //执行重载
        table.reload('LAY-user-manage', {
            where: field
        });
    });

    //事件
    var active = {
        batchdel: function () {
            var checkStatus = table.checkStatus('LAY-user-manage'),
                checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }
            layer.confirm('确定删除', function (index) {

                $.ajax({
                    url: setter.baseURL + 'deleteUserRoles',
                    type: 'post',
                    headers: {
                        'Authorization': sessionStorage.getItem("token"),
                        'Access-Control-Allow-Origin': '*'
                    },
                    data: JSON.stringify(checkData),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (res) {
                        table.reload('LAY-user-manage');
                        layer.msg('已删除');
                    },
                    error: function (res) {
                        layer.msg('删除失败');
                    }
                })

            });
        },
        add: function () {
            layer.open({
                type: 2,
                title: '添加用户',
                content: 'userform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-user-front-submit',
                        submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
                        var field = data.field; //获取提交的字段
                        console.log(field);
                        $.ajax({
                            url: setter.baseURL + 'addUserRole',
                            type: 'post',
                            headers: {
                                'Authorization': sessionStorage.getItem("token"),
                                'Access-Control-Allow-Origin': '*'
                            },
                            data: JSON.stringify(field),
                            dataType: 'json',
                            contentType: 'application/json;charset:utf-8',
                            success: function (res) {
                                if (res.msg != '添加成功') {
                                    layer.msg(res.msg);
                                } else {
                                    layer.msg('添加成功');
                                    table.reload('LAY-user-manage'); //数据刷新
                                    layer.close(index); //关闭弹层
                                }
                            },
                            error: function (res) {
                                layer.msg("添加失败");
                            }
                        });
                    });

                    submit.trigger('click');
                }
            });
        }
    };

    $('.layui-btn.layuiadmin-btn-useradmin').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    exports('userlist', {})
});