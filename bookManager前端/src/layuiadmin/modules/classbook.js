layui.define(['index', 'table', 'laydate'], function (exports) {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        form = layui.form,
        laydate = layui.laydate,
        setter = layui.setter;

    layer.ready(function () {
        $.ajax({
            url: setter.baseURL + 'getAllBookList',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function(res) {
                var data = res.data;
                for(i = 0; i < data.length; i++) {
                    $("#bookName").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
        $.ajax({
            url: setter.baseURL + 'getAllClassList',
            type: 'post', 
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function(res) {
                var data = res.data;
                for(i = 0; i < data.length; i++) {
                    $("#className").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    });

    laydate.render({
        elem: '#receiveDate'
    });

    table.render({
        elem: '#test-table-toolbar',
        url: setter.baseURL + 'getClassBookList',
        headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
        },
        toolbar: '#test-table-toolbar-toolbarDemo',
        where: {
            receiveDate: '',
            bookName: '',
            className: '',
            bookSum: '',
            schoolName: ''
        },
        title: '班级教材领取情况表',
        cols: [
            [{
                type: 'checkbox',
                fixed: 'left'
            }, {
                field: 'receiveDate',
                title: '领取日期'
            }, {
                field: 'className',
                title: '班级名'
            }, {
                field: 'bookName',
                title: '教材名'
            }, {
                field: 'bookSum',
                title: '领取数量'
            }, {
                field: 'principalName',
                title: '班级负责人'
            }, {
                field: 'principalTel',
                title: '负责人电话'
            }, {
                title: '操作',
                fixed: 'right',
                align: 'center',
                toolbar: '#test-table-toolbar-barDemo',
                width: 150
            }]
        ],
        page: true,
        groups: 5,
        limit: 30,
        height: 'full-130',
        text: {
            none: '无结果'
        }
    });

    //监听行工具事件
    table.on('tool(test-table-toolbar)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确认删除', function (index) {
                $.ajax({
                    url: setter.baseURL + 'deleteOneClassBook',
                    type: 'post',
                    headers: {
                        'Authorization': sessionStorage.getItem("token"),
                        'Access-Control-Allow-Origin': '*'
                    },
                    data: JSON.stringify(data),
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (res) {
                        layer.msg(res.msg, {
                            offset: '15px',
                            icon: 1,
                            time: 1000
                        }, function () {
                            obj.del();
                            layer.close(index);
                        });
                    }
                });
            });
        }
        if (obj.event === 'edit') {
            var tr = $(obj.tr);
            var id = obj.data.id;
            layer.open({
                type: 2,
                title: '编辑教材领取信息',
                content: 'classbookform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                success: function (layero, index) {
                    let body = layer.getChildFrame('body', index);
                    body.find("#receiveDate").val(data.receiveDate);
                    body.find("#cutil").val(data.className);
                    body.find("#butil").val(data.bookName);
                    body.find("#autil").val(data.author);
                    body.find("#ptutil").val(data.publishTime);
                    body.find("#putil").val(data.publisher);
                    body.find("#prutil").val(data.bookPrice);
                    body.find("#bookSum").val(data.bookSum);
                },
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-classBook-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {

                            var field = data.field;
                            field['id'] = id;

                            //提交 Ajax 成功后，静态更新表格中的数据
                            $.ajax({
                                url: setter.baseURL + "updateClassBook",
                                type: 'post',
                                headers: {
                                    'Authorization': sessionStorage
                                        .getItem("token"),
                                    'Content-Type': 'application/json',
                                    'Access-Control-Allow-Origin': '*'
                                },
                                data: JSON.stringify(field),
                                contentType: 'application/json;charset=utf-8',
                                dataType: 'json',
                                success: function (res) {
                                    layer.msg("修改成功！", {
                                        offset: '15px',
                                        icon: 1,
                                        time: 1000
                                    })
                                }
                            });
                            table.reload('test-table-toolbar'); //数据刷新
                            layer.close(index); //关闭弹层
                        });

                    submit.trigger('click');
                }
            });
        }
    });

    //监听搜索
    form.on('submit(LAY-classBook-front-search)', function (data) {
        var field = data.field;

        //执行重载
        table.reload('test-table-toolbar', {
            where: field
        });
    });

    var active = {
        batchdel: function () {
            var checkStatus = table.checkStatus('test-table-toolbar'),
                checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定删除', function (index) {
                //执行 Ajax 后重载
                $.ajax({
                    url: setter.baseURL + 'deleteClassBooks',
                    type: 'post',
                    data: JSON.stringify(checkData),
                    headers: {
                        'Authorization': sessionStorage.getItem("token"),
                        'Access-Control-Allow-Origin': '*'
                    },
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (res) {
                        table.reload('test-table-toolbar');
                        layer.msg('已删除');
                    },
                    error: function (res) {
                        layer.msg("删除失败")
                        table.reload('test-table-toolbar');
                    }
                })
            });
        },
        add: function () {
            layer.open({
                type: 2,
                title: '添加教材领取情况',
                content: 'classbookform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-classBook-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {
                            var field = data.field; //获取提交的字段

                            $.ajax({
                                url: setter.baseURL + "addClassBook",
                                type: 'post',
                                headers: {
                                    'Authorization': sessionStorage
                                        .getItem("token"),
                                    'Access-Control-Allow-Origin': '*'
                                },
                                data: JSON.stringify(field),
                                contentType: 'application/json;charset=utf-8',
                                dataType: 'json',
                                success: function (res) {
                                    layer.msg("添加成功");
                                    table.reload(
                                        'test-table-toolbar'
                                    ); //数据刷新
                                    layer.close(index); //关闭弹层
                                },
                                error: function (res) {
                                    layer.msg("添加失败");
                                    // table.reload('book-manage'); //数据刷新
                                    // layer.close(index); //关闭弹层
                                }
                            })

                        });

                    submit.trigger('click');
                }
            });
        }
    };
    $('.layui-btn.layuiadmin-btn-classBooklist').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    exports('classbook', {})
});