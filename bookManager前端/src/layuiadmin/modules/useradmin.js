/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form'], function (exports) {
  var $ = layui.$
    , table = layui.table
    , form = layui.form;

  //用户管理
  table.render({
    elem: '#LAY-user-manage'
    , url: 'http://localhost:8080/getUserRoleList' //模拟接口
    , headers: {
      'Authorization': sessionStorage.getItem("token"),
      'Access-Control-Allow-Origin': '*'
    }
    , where: {
      username: '',
      nickname: '',
      roleName: '',
      email: '',
      sex: ''
    }
    , cols: [[
      { type: 'checkbox', fixed: 'left' }
      , { field: 'id', width: 100, title: 'ID', sort: true }
      , { field: 'username', title: '用户名', minWidth: 100 }
      , { field: 'nickname', title: '真实姓名' }
      , { field: 'roleName', title: '用户角色' }
      , { field: 'email', title: '邮箱' }
      , { field: 'sex', width: 80, title: '性别' }
      , { title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-webuser' }
    ]]
    , page: true
    , limit: 30
    , height: 'full-220'
    , text: { error: '对不起，加载出现异常！', none: '无结果' }
  });

  //监听工具条
  table.on('tool(LAY-user-manage)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      layer.confirm('确定删除', function (index) {
        $.ajax({
          url: 'http://localhost:8080/deleteOneUserRole',
          type: 'post',
          headers: {
            'Authorization': sessionStorage.getItem("token"),
            'Access-Control-Allow-Origin': '*'
          },
          data: JSON.stringify(data),
          dataType: 'json',
          contentType: 'application/json;charset=utf-8',
          success: function (res) {
            layer.msg(res.msg, function () {
              obj.del();
              layer.close(index);
            });
          },
          error: function (res) {
            layer.msg('删除失败', {
              offset: '15px'
              , icon: 2
              , time: 1000
            });
          }
        })

      });

    } else if (obj.event === 'edit') {
      var tr = $(obj.tr);
      var id = obj.data.id;
      layer.open({
        type: 2
        , title: '编辑用户'
        , content: '../../../views/user/user/updateUserform.html'
        , maxmin: true
        , area: ['500px', '450px']
        , btn: ['确定', '取消']
        , yes: function (index, layero) {
          var iframeWindow = window['layui-layer-iframe' + index]
            , submitID = 'LAY-user-front-submit'
            , submit = layero.find('iframe').contents().find('#' + submitID);

          //监听提交
          iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
            var field = data.field; //获取提交的字段

            field['id'] = id;

            console.log(field);

            $.ajax({
              url: 'http://localhost:8080/updateUserRole',
              type: 'post',
              headers: {
                'Authorization': sessionStorage.getItem("token"),
                'Access-Control-Allow-Origin': '*'
              },
              data: JSON.stringify(field),
              dataType: 'json',
              contentType: 'application/json;charset=utf-8',
              success: function(res) {
                layer.msg(res.msg);
                table.reload('LAY-user-manage'); //数据刷新
                layer.close(index); //关闭弹层
              },
              error: function(res) {
                layer.msg('修改失败');
              }
            })
            
          });

          submit.trigger('click');
        }
        , success: function (layero, index) {

        }
      });
    }
  });

  //管理员管理
  table.render({
    elem: '#LAY-user-back-manage'
    , url: layui.setter.base + 'json/useradmin/mangadmin.js' //模拟接口
    , headers: {
      'Authorization': sessionStorage.getItem("token"),
      'Access-Control-Allow-Origin': '*'
    }
    , cols: [[
      { type: 'checkbox', fixed: 'left' }
      , { field: 'id', width: 80, title: 'ID', sort: true }
      , { field: 'loginname', title: '登录名' }
      , { field: 'telphone', title: '手机' }
      , { field: 'email', title: '邮箱' }
      , { field: 'role', title: '角色' }
      , { field: 'jointime', title: '加入时间', sort: true }
      , { field: 'check', title: '审核状态', templet: '#buttonTpl', minWidth: 80, align: 'center' }
      , { title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin' }
    ]]
    , text: '对不起，加载出现异常！'
  });

  //监听工具条
  table.on('tool(LAY-user-back-manage)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      layer.prompt({
        formType: 1
        , title: '敏感操作，请验证口令'
      }, function (value, index) {
        layer.close(index);
        layer.confirm('确定删除此管理员？', function (index) {
          obj.del();
          layer.close(index);
        });
      });
    } else if (obj.event === 'edit') {
      var tr = $(obj.tr);

      layer.open({
        type: 2
        , title: '编辑管理员'
        , content: '../../../views/user/administrators/adminform.html'
        , area: ['420px', '420px']
        , btn: ['确定', '取消']
        , yes: function (index, layero) {
          var iframeWindow = window['layui-layer-iframe' + index]
            , submitID = 'LAY-user-back-submit'
            , submit = layero.find('iframe').contents().find('#' + submitID);

          //监听提交
          iframeWindow.layui.form.on('submit(' + submitID + ')', function (data) {
            var field = data.field; //获取提交的字段

            //提交 Ajax 成功后，静态更新表格中的数据
            //$.ajax({});
            table.reload('LAY-user-front-submit'); //数据刷新
            layer.close(index); //关闭弹层
          });

          submit.trigger('click');
        }
        , success: function (layero, index) {

        }
      })
    }
  });

  //角色管理
  table.render({
    elem: '#LAY-user-back-role'
    , url: 'http://localhost:8080/getRoleList'
    , headers: {
      'Authorization': sessionStorage.getItem("token"),
      'Access-Control-Allow-Origin': '*'
    }
    , cols: [[
      { type: 'checkbox', fixed: 'left' }
      , { field: 'id', width: 80, title: 'ID', sort: true }
      , { field: 'name', title: '角色名' }
      , { field: 'description', title: '具体描述' }
      , { title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin' }
    ]]
    , text: { error: '对不起，加载出现异常！', none: '无结果' }
  });

  //监听工具条
  table.on('tool(LAY-user-back-role)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      /* layer.confirm('确定删除', function (index) {
        obj.del();
        layer.close(index);
      }); */
      layer.alert("功能待开发");
    } else if (obj.event === 'edit') {
      var tr = $(obj.tr);
      layer.alert("功能待开发");
      /* layer.open({
        type: 2
        , title: '编辑角色'
        , content: '../../../views/user/administrators/roleform.html'
        , area: ['500px', '480px']
        , btn: ['确定', '取消']
        , yes: function (index, layero) {
          var iframeWindow = window['layui-layer-iframe' + index]
            , submit = layero.find('iframe').contents().find("#LAY-user-role-submit");

          //监听提交
          iframeWindow.layui.form.on('submit(LAY-user-role-submit)', function (data) {
            var field = data.field; //获取提交的字段

            //提交 Ajax 成功后，静态更新表格中的数据
            //$.ajax({});
            table.reload('LAY-user-back-role'); //数据刷新
            layer.close(index); //关闭弹层
          });

          submit.trigger('click');
        }
        , success: function (layero, index) {

        }
      }) */
    }
  });

  exports('useradmin', {})
});