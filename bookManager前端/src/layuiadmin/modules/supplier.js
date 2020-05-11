layui.define(['index', 'table'], function (exportss) {
    var admin = layui.admin,
        table = layui.table,
        $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        setter = layui.setter;

    layer.ready(function() {
        $.ajax({
            url: setter.baseURL + 'getAllDSupplierName',
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
                    $("#name").append(new Option(data[i].name, data[i].name));
                }
                form.render();
            }
        });
        $.ajax({
            url: setter.baseURL + 'getAllSupplierList',
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
                    $("#tel").append(new Option(data[i].tel, data[i].tel));
                }
                form.render();
            }
        })
    });

    table.render({
        elem: '#test-table-toolbar',
        url: setter.baseURL + 'getSupplierList',
        headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
        },
        toolbar: '#test-table-toolbar-toolbarDemo',
        where: {
            name: '',
            tel: ''
        },
        title: '班级教材领取情况表',
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
                title: '供应商名'
            }, {
                field: 'tel',
                title: '供应商电话'
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
        if (obj.event === 'edit') {
            var tr = $(obj.tr);
            var id = obj.data.id;
            layer.open({
                type: 2,
                title: '编辑教材入库信息',
                content: 'supplierform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                success: function (layero, index) {
                    let body = layer.getChildFrame('body', index);
                    body.find("#name").val(data.name);
                    body.find("#tel").val(data.tel);
                },
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-supplier-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);
                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {

                            var field = data.field;
                            field['id'] = id;

                            //提交 Ajax 成功后，静态更新表格中的数据
                            $.ajax({
                                url: setter.baseURL + "updateSupplier",
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
    form.on('submit(LAY-supplier-front-search)', function (data) {
        var field = data.field;

        //执行重载
        table.reload('test-table-toolbar', {
            where: field
        });
    });

    var active = {
        add: function () {
            layer.open({
                type: 2,
                title: '添加入库订单',
                content: 'supplierform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-supplier-front-submit',
                        submit = layero.find('iframe').contents().find('#' +
                            submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')',
                        function (data) {
                            var field = data.field; //获取提交的字段

                            $.ajax({
                                url: setter.baseURL + "addSupplier",
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
    $('.layui-btn.layuiadmin-btn-supplierlist').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    exports('supplier', {})
});