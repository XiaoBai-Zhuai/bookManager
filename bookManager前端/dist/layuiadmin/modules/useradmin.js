/** layuiAdmin.std-v1.1.0 LPPL License By http://www.layui.com/admin/ */
 ;layui.define(["table","form"],function(e){var t=layui.$,i=layui.table;layui.form;i.render({elem:"#LAY-user-manage",url:"http://localhost:8080/getUserRoleList",headers:{Authorization:sessionStorage.getItem("token"),"Access-Control-Allow-Origin":"*"},where:{username:"",nickname:"",roleName:"",email:"",sex:""},cols:[[{type:"checkbox",fixed:"left"},{field:"id",width:100,title:"ID",sort:!0},{field:"username",title:"用户名",minWidth:100},{field:"nickname",title:"真实姓名"},{field:"roleName",title:"用户角色"},{field:"email",title:"邮箱"},{field:"sex",width:80,title:"性别"},{title:"操作",width:150,align:"center",fixed:"right",toolbar:"#table-useradmin-webuser"}]],page:!0,limit:30,height:"full-220",text:{error:"对不起，加载出现异常！",none:"无结果"}}),i.on("tool(LAY-user-manage)",function(e){var l=e.data;if("del"===e.event)layer.confirm("确定删除",function(i){t.ajax({url:"http://localhost:8080/deleteOneUserRole",type:"post",headers:{Authorization:sessionStorage.getItem("token"),"Access-Control-Allow-Origin":"*"},data:JSON.stringify(l),dataType:"json",contentType:"application/json;charset=utf-8",success:function(t){layer.msg(t.msg,function(){e.del(),layer.close(i)})},error:function(e){layer.msg("删除失败",{offset:"15px",icon:2,time:1e3})}})});else if("edit"===e.event){var n=(t(e.tr),e.data.id);layer.open({type:2,title:"编辑用户",content:"../../../views/user/user/updateUserform.html",maxmin:!0,area:["500px","450px"],btn:["确定","取消"],yes:function(e,l){var o=window["layui-layer-iframe"+e],r="LAY-user-front-submit",a=l.find("iframe").contents().find("#"+r);o.layui.form.on("submit("+r+")",function(l){var o=l.field;o.id=n,console.log(o),t.ajax({url:"http://localhost:8080/updateUserRole",type:"post",headers:{Authorization:sessionStorage.getItem("token"),"Access-Control-Allow-Origin":"*"},data:JSON.stringify(o),dataType:"json",contentType:"application/json;charset=utf-8",success:function(t){layer.msg(t.msg),i.reload("LAY-user-manage"),layer.close(e)},error:function(e){layer.msg("修改失败")}})}),a.trigger("click")},success:function(e,t){}})}}),i.render({elem:"#LAY-user-back-manage",url:layui.setter.base+"json/useradmin/mangadmin.js",headers:{Authorization:sessionStorage.getItem("token"),"Access-Control-Allow-Origin":"*"},cols:[[{type:"checkbox",fixed:"left"},{field:"id",width:80,title:"ID",sort:!0},{field:"loginname",title:"登录名"},{field:"telphone",title:"手机"},{field:"email",title:"邮箱"},{field:"role",title:"角色"},{field:"jointime",title:"加入时间",sort:!0},{field:"check",title:"审核状态",templet:"#buttonTpl",minWidth:80,align:"center"},{title:"操作",width:150,align:"center",fixed:"right",toolbar:"#table-useradmin-admin"}]],text:"对不起，加载出现异常！"}),i.on("tool(LAY-user-back-manage)",function(e){e.data;if("del"===e.event)layer.prompt({formType:1,title:"敏感操作，请验证口令"},function(t,i){layer.close(i),layer.confirm("确定删除此管理员？",function(t){e.del(),layer.close(t)})});else if("edit"===e.event){t(e.tr);layer.open({type:2,title:"编辑管理员",content:"../../../views/user/administrators/adminform.html",area:["420px","420px"],btn:["确定","取消"],yes:function(e,t){var l=window["layui-layer-iframe"+e],n="LAY-user-back-submit",o=t.find("iframe").contents().find("#"+n);l.layui.form.on("submit("+n+")",function(t){t.field;i.reload("LAY-user-front-submit"),layer.close(e)}),o.trigger("click")},success:function(e,t){}})}}),i.render({elem:"#LAY-user-back-role",url:"http://localhost:8080/getRoleList",headers:{Authorization:sessionStorage.getItem("token"),"Access-Control-Allow-Origin":"*"},cols:[[{type:"checkbox",fixed:"left"},{field:"id",width:80,title:"ID",sort:!0},{field:"name",title:"角色名"},{field:"description",title:"具体描述"},{title:"操作",width:150,align:"center",fixed:"right",toolbar:"#table-useradmin-admin"}]],text:{error:"对不起，加载出现异常！",none:"无结果"}}),i.on("tool(LAY-user-back-role)",function(e){e.data;if("del"===e.event)layer.alert("功能待开发");else if("edit"===e.event){t(e.tr);layer.alert("功能待开发")}}),e("useradmin",{})});