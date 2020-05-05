layui.define(['index', 'form', 'upload', 'laydate'], function () {
    var $ = layui.$,
        form = layui.form,
        upload = layui.upload,
        laydate = layui.laydate,
        layer = layui.layer,
        setter = layui.setter;

    layer.ready(function () {
        $.ajax({
            url: setter.baseURL + 'getAllDBookName',
            type: 'post',
            headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
            },
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (res) {
                var data = res.data;
                var bookName = $("#butil").val();
                for (i = 0; i < data.length; i++) {
                    $("#bookName").append(new Option(data[i].name, data[i].name));
                    if (bookName != undefined && bookName == data[i].name) {
                        $("#bookName").get(0).selectedIndex = i + 1;
                    }
                }
                form.render();
            }
        });
        var author = $("#autil").val(),
            publishTime = $("#ptutil").val(),
            publisher = $("#putil").val(),
            bookPrice = $("#prutil").val();
        if (author != undefined) {
            $.ajax({
                url: setter.baseURL + 'getAllDBookAuthorByBookName',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#butil").val()
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#author").append(new Option(data[i].author, data[i]
                            .author));
                        if (author != undefined && author == data[i].author) {
                            $("#author").get(0).selectedIndex = i + 1;
                        }
                    }
                    form.render();
                }
            });
        }
        if (publishTime != undefined) {
            $.ajax({
                url: setter.baseURL + 'getAllDBookPublishTimeByBookAuthor',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#butil").val(),
                    'author': $("#autil").val()
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#publishTime").append(new Option(data[i].publishTime,
                            data[i].publishTime));
                        if (publishTime != undefined && publishTime == data[i]
                            .publishTime) {
                            $("#publishTime").get(0).selectedIndex = i + 1;
                        }
                    }
                    form.render();
                }
            });
        }
        if (publisher != undefined) {
            $.ajax({
                url: setter.baseURL + 'getAllDBookPublisherByBookPublishTime',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#butil").val(),
                    'author': $("#autil").val(),
                    'publishTime': $("#ptutil").val()
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#publisher").append(new Option(data[i].publisher, data[i]
                            .publisher));
                        if (publisher != undefined && publisher == data[i]
                            .publisher) {
                            $("#publisher").get(0).selectedIndex = i + 1;
                        }
                    }
                    form.render();
                }
            });
        }
        if (bookPrice != undefined) {
            $.ajax({
                url: setter.baseURL + 'getAllDBookPriceByBookPublisher',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#butil").val(),
                    'author': $("#autil").val(),
                    'publishTime': $("#ptutil").val(),
                    'publisher': $("#putil").val()
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#bookPrice").append(new Option(data[i].price, data[i]
                            .price));
                        if (bookPrice != undefined && bookPrice == data[i].price) {
                            $("#bookPrice").get(0).selectedIndex = i + 1;
                        }
                    }
                    form.render();
                }
            });
        }
    });

    form.on('select(bookName)', function (data) {
        var bookName = data.value;
        if ((bookName != '' && bookName != null && bookName != undefined)) {
            $("#author").find("option").remove();
            $("#author").append(new Option("", ""));
            $("#publishTime").find("option").remove();
            $("#publishTime").append(new Option("", ""));
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
            $.ajax({
                url: setter.baseURL + 'getAllDBookAuthorByBookName',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': data.value
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    $("#author").find("option").remove();
                    $("#author").append(new Option("", ""));
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#author").append(new Option(data[i].author, data[i]
                            .author));
                    }
                    form.render();
                }
            });
        }
        if (bookName == '') {
            $("#author").find("option").remove();
            $("#author").append(new Option("", ""));
            $("#publishTime").find("option").remove();
            $("#publishTime").append(new Option("", ""));
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
        }
    });

    form.on('select(author)', function (data) {
        var author = data.value;
        if (author == '') {
            $("#publishTime").find("option").remove();
            $("#publishTime").append(new Option("", ""));
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
        }
        if (author != '' && author != null && author != undefined) {
            $("#publishTime").find("option").remove();
            $("#publishTime").append(new Option("", ""));
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
            $.ajax({
                url: setter.baseURL + 'getAllDBookPublishTimeByBookAuthor',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#bookName").val(),
                    'author': data.value
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    $("#publishTime").find("option").remove();
                    $("#publishTime").append(new Option("", ""));
                    var data = res.data;

                    for (i = 0; i < data.length; i++) {
                        $("#publishTime").append(new Option(data[i].publishTime,
                            data[i].publishTime));
                    }
                    form.render();
                }
            });
        }
    });

    form.on('select(publishTime)', function (data) {
        var publishTime = data.value;
        if (publishTime != '' && publishTime != null && publishTime != undefined) {
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
            $.ajax({
                url: setter.baseURL + 'getAllDBookPublisherByBookPublishTime',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#bookName").val(),
                    'author': $("#author").val(),
                    'publishTime': data.value
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    $("#publisher").find("option").remove();
                    $("#publisher").append(new Option("", ""));
                    var data = res.data;

                    for (i = 0; i < data.length; i++) {
                        $("#publisher").append(new Option(data[i].publisher, data[i]
                            .publisher));
                    }
                    form.render();
                }
            });
        } else {
            $("#publisher").find("option").remove();
            $("#publisher").append(new Option("", ""));
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
        }
    });

    form.on('select(publisher)', function (data) {
        var publisher = data.value;
        if (publisher != '' && publisher != undefined && publisher != null) {
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
            $.ajax({
                url: setter.baseURL + 'getAllDBookPriceByBookPublisher',
                type: 'post',
                headers: {
                    'Authorization': sessionStorage.getItem("token"),
                    'Access-Control-Allow-Origin': '*'
                },
                data: JSON.stringify({
                    'bookName': $("#bookName").val(),
                    'author': $("#author").val(),
                    'publishTime': $("#publishTime").val(),
                    'publisher': data.value
                }),
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                success: function (res) {
                    $("#bookPrice").find("option").remove();
                    $("#bookPrice").append(new Option("", ""));
                    var data = res.data;
                    for (i = 0; i < data.length; i++) {
                        $("#bookPrice").append(new Option(data[i].price, data[i]
                            .price));
                    }
                    form.render();
                }
            });
        } else {
            $("#bookPrice").find("option").remove();
            $("#bookPrice").append(new Option("", ""));
            form.render('select');
        }
    });

    laydate.render({
        elem: '#stockOutDate' //指定元素
    });

    exports('stockOutform', {})
})