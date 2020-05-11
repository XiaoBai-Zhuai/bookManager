layui.define(['index', 'table'], function (exports) {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        form = layui.form,
        layer = layui.layer,
        setter = layui.setter;

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
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#schoolName").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
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
                for (i = 0; i < data.length; i++) {
                    $("#majorName").append(new Option(data[i].name, data[i].name));
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
            success: function (res) {
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#name").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    });

    form.on('select(schoolName)', function (data) {
        $.ajax({
            url: setter.baseURL + "getMajorListBySchoolName",
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
                $("#majorName").find("option").remove();
                $("#majorName").append(new Option("", ""));
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#majorName").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
        $.ajax({
            url: setter.baseURL + 'getAllClassListBySchoolName',
            type: 'post',
            data: JSON.stringify({
                'schoolName': data.value
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            success: function (res) {
                $("#name").find("option").remove();
                $("#name").append(new Option("", ""));
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#name").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    });

    form.on('select(majorName)', function (data) {
        $('#name').find("option").remove();
        $('#name').append(new Option("", ""));
        $.ajax({
            url: setter.baseURL + 'getAllClassListByMajorName',
            type: 'post',
            data: JSON.stringify({
                'majorName': data.value,
                'schoolName': $("#schoolName").val()
            }),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            success: function (res) {
                var data = res.data;
                for (i = 0; i < data.length; i++) {
                    $("#name").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
    })


    table.render({
        elem: '#test-table-toolbar',
        url: setter.baseURL + 'getClassList',
        headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
        },
        toolbar: '#test-table-toolbar-toolbarDemo',
        title: '班级信息列表',
        where: {
            name: '',
            majorName: '',
            schoolName: '',
            principalNumber: ''
        },
        cols: [
            [{
                type: 'checkbox',
                fixed: 'left'
            }, {
                field: 'id',
                title: 'ID',
                width: 80,
                sort: true
            }, {
                field: 'name',
                title: '班级名'
            }, {
                field: 'majorName',
                title: '所属专业'
            }, {
                field: 'schoolName',
                title: '所属学院'
            }, {
                field: 'principalName',
                title: '负责人姓名'
            }, {
                field: 'principalNumber',
                title: '负责人学号'
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
                    url: setter.baseURL + 'deleteOneClass',
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
                    },
                    error: function () {
                        layer.msg("删除失败");
                    }
                });
            });
        }
        if (obj.event === 'edit') {
            var tr = $(obj.tr);
            var id = obj.data.id;
            var data = obj.data;
            layer.open({
                type: 2,
                title: '编辑班级信息',
                content: 'classform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                success: function (layero, index) {
                    let body = layer.getChildFrame('body', index);
                    body.find("#name").val(data.name);
                    body.find("#util").val(data.majorName);
                    body.find("#principalName").val(data.principalName);
                    body.find("#principalNumber").val(data.principalNumber);
                },
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-class-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {

                            var field = data.field;
                            field['id'] = id;

                            //提交 Ajax 成功后，静态更新表格中的数据
                            $.ajax({
                                url: setter.baseURL + "updateClass",
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
    form.on('submit(LAY-class-front-search)', function (data) {
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
                    url: setter.baseURL + 'deleteClasses',
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
                title: '添加班级',
                content: 'classform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-class-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {
                            var field = data.field; //获取提交的字段

                            $.ajax({
                                url: setter.baseURL + "addClass",
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
    $('.layui-btn.layuiadmin-btn-classlist').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    exports('classlist', {})
});