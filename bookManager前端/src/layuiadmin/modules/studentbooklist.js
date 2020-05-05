/**

 @Name：layuiAdmin 教材管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form', 'laydate'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , laydate = layui.laydate
        , setter = layui.setter;

    //用户管理
    table.render({
        elem: '#book-manage'
        , url: setter.baseURL + 'getBookList'
        , headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
        }
        , where: {
            name: '',
            author: '',
            publisher: ''
        }
        , cols: [[
            { field: 'id', width: 100, title: 'ID', sort: true }
            , { field: 'name', title: '教材名', minWidth: 100 }
            , { field: 'author', title: '作者' }
            , { field: 'publisher', title: '出版社' }
            , { field: 'publishTime', title: '出版时间' }
            , { field: 'price', title: '定价（元）' }
            , {field: 'stockSum', title: '库存'}
        ]]
        , page: true
        , groups: 5
        , limit: 30
        , height: 'full-180'
        , text: {none: '无结果'}
    });
    exports('studentbooklist', {})
});