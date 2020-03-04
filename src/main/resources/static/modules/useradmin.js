/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form'], function(exports){
  var $ = layui.$
  ,table = layui.table
  ,form = layui.form;

  //用户管理
  table.render({
    elem: '#LAY-user-manage'
    ,url: '/user/getuserlist' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 100, title: 'ID', sort: true}
      ,{field: 'accountId', title: '用户账号', width: 120,templet: '<div><a href="http://101.200.47.40/user/home/{{d.accountId}}" target="_blank" class="layui-table-link">{{d.accountId}}</a></div>'}
      ,{field: 'avatarUrl', title: '头像', width: 90,align:'center', templet: '#imgTpl'}
      ,{field: 'name', title: '昵称', minWidth: 180}
      ,{field: 'sex', width: 60, title: '性别',templet: '#sex',align:'center'}
      ,{field: 'email', title: '邮箱',minWidth:180}
      ,{field: 'kissNum', title: '经验值',width:120}
      ,{field: 'userCreate', title: '加入时间', sort: true,minWidth:180,templet: '<div>{{layui.util.toDateString(d.userCreate)}}</div>'}
      ,{field: 'role', title: '用户角色',minWidth:180}
      ,{field: 'status', title: '用户状态',minWidth:100,templet: '#status',align:'center'}
      ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
    ]]
    ,page: true
    ,limit: 10
  });
  
  //监听工具条
  table.on('tool(LAY-user-manage)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.prompt({
        formType: 1
        ,title: '敏感操作，请验证口令'
      }, function(value, index){
        if(value == 'admin'){
          layer.close(index);
          layer.confirm('真的删除行么', function(index){
            $.ajax({
              url: '/user/deluser'
              ,data: {
                id:data.id
              }
              ,success: function(res){
                if(res.code == 0){
                  layer.msg(res.msg,{icon:1,time:1000},function () {
                    obj.del();
                    layer.close(index);
                  });
                }
              }
            });
          });
        }else{
          return layer.msg("密码错误！");
        }

        

      });
    } else if(obj.event === 'edit'){
      var tr = $(obj.tr);

      layer.open({
        type: 2
        ,title: '编辑用户'
        ,content: '/user/userform?id='+data.accountId
        ,maxmin: true
        ,area: ['500px', '450px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
          var iframeWindow = window['layui-layer-iframe'+ index]
          ,submitID = 'LAY-user-front-submit'
          ,submit = layero.find('iframe').contents().find('#'+ submitID);

          //监听提交
          iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            var field = data.field; //获取提交的字段
            
            //提交 Ajax 成功后，静态更新表格中的数据
            $.ajax({
              type:'post'
              ,url: '/user/updateuser'
              ,data:field
              ,success: function(res){
                if(res.code == 0){
                  layer.msg(res.msg,{icon:1,time:1000},function () {
                    obj.update({
                     name:field.name,
                      sex:field.sex,
                      email:field.email,
                      role:field.role,
                      kissNum:field.kissNum,
                      status:field.zhuangtai != null?1:0
                    }); //数据更新
                    table.reload('LAY-user-front-submit'); //数据刷新
                    layer.close(index); //关闭弹层
                  });
                }
              }
            });


          });  
          
          submit.trigger('click');
        }
        ,success: function(layero, index){
          
        }
      });
    }
  });

  exports('useradmin', {})
});