/**

 @Name：layuiAdmin 教材管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form', 'laydate'], function (exports) {
    var $ = layui.$,
        table = layui.table,
        form = layui.form,
        laydate = layui.laydate
    setter = layui.setter;

    clickImg = function (t) {
        var t = $(t).find("img");
        if (t == null || t == '') {
            return;
        }
        layer.open({
            type: 1,
            skin: 'layui-layer-rim',
            area: ['75%', '85'],
            closeBtn: 1,
            title: false,
            shadeClose: true,
            end: function (index, layero) {
                return false;
            },
            success: function (layero, index) {
                layer.iframeAuto(index);
            },
            content: '<div style="text-align:center"><img src="' + $(t).attr('src') + '"/></div>'
        })
    };

    //用户管理
    table.render({
        elem: '#book-manage',
        url: setter.baseURL + 'getBookList',
        headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
        },
        where: {
            name: '',
            author: '',
            publisher: ''
        },
        cols: [
            [{
                type: 'checkbox',
                fixed: 'left'
            }, {
                field: 'name',
                title: '教材名',
                minWidth: 100
            }, {
                field: 'image',
                title: '封面',
                width: 80,
                templet: function (d) {
                    if (d.image != undefined)
                        return '<div onclick="clickImg(this)"><img src=' + setter.baseURL + "images/" + d.image + ' style="width: 50px; height: 50px"/></div>'
                    else
                        return '';
                }
            }, {
                field: 'author',
                title: '作者'
            }, {
                field: 'publisher',
                title: '出版社'
            }, {
                field: 'publishTime',
                title: '出版时间'
            }, {
                field: 'price',
                title: '定价（元）'
            }, {
                field: 'stockSum',
                title: '库存'
            }, {
                title: '操作',
                width: 150,
                align: 'center',
                fixed: 'right',
                toolbar: '#table-book'
            }]
        ],
        page: true,
        groups: 5,
        limit: 30,
        height: 'full-180',
        text: {
            none: '无结果'
        },
    });

    //监听工具条
    table.on('tool(book-manage)', function (obj) {
        var data = obj.data;
        var id = data.id;
        if (obj.event === 'del') {
            layer.confirm('确认删除', function (index) {

                $.ajax({
                    url: setter.baseURL + 'deleteOneBookById',
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
        } else if (obj.event === 'edit') {
            var tr = $(obj.tr);
            var id = obj.data.id;
            layer.open({
                type: 2,
                title: '编辑教材信息',
                content: '../../views/template/bookform.html',
                maxmin: true,
                area: ['500px', '450px'],
                btn: ['确定', '取消'],
                success: function (layero, index) {
                    let body = layer.getChildFrame('body', index);
                    body.find("#publishTime").val(data.publishTime);
                    body.find("#publisher").val(data.publisher);
                    body.find("#name").val(data.name);
                    body.find("#author").val(data.author);
                    body.find("#price").val(data.price);
                    body.find("#imageUrl").val(data.imageUrl);
                },
                yes: function (index, layero) {
                    var iframeWindow = window['layui-layer-iframe' + index],
                        submitID = 'LAY-book-front-submit',
                        submit = layero.find('iframe').contents().find('#' + submitID);

                    //监听提交
                    iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {

                        var field = data.field;
                        field['id'] = id;

                        //提交 Ajax 成功后，静态更新表格中的数据
                        $.ajax({
                            url: setter.baseURL + "updateBookInfo",
                            type: 'post',
                            headers: {
                                'Authorization': sessionStorage.getItem("token"),
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
                                });
                                table.reload('book-manage'); //数据刷新
                                layer.close(index); //关闭弹层
                            }
                        });
                    });

                    submit.trigger('click');
                },
            });
        }
    });


    exports('booklist', {})
});