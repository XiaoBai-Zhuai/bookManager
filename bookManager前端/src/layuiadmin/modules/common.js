/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */

layui.define(function (exports) {
  var $ = layui.$
    , layer = layui.layer
    , laytpl = layui.laytpl
    , setter = layui.setter
    , view = layui.view
    , admin = layui.admin
    , setter = layui.setter
    , form = layui.form;

  //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
  //……

  //退出
  admin.events.logout = function () {
    /* sessionStorage.clear();
    location.href = 'login.html'; */
    //执行退出接口
    $.ajax({
      url: setter.baseURL + 'logout'
      , type: 'post'
      , headers: {
        'Authorization': sessionStorage.getItem("token"),
        'Access-Control-Allow-Origin': '*'
      }
      , success: function (res) {
        //清空本地记录的 token，并跳转到登入页
        layer.msg("退出成功");
        sessionStorage.clear();
        location.href = 'login.html';
      }
    });
  };


  //对外暴露的接口
  exports('common', {});
});