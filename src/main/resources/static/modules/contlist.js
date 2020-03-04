/**

 @Name：layuiAdmin 内容系统
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form','admin'], function(exports){
  var $ = layui.$
  ,table = layui.table
      ,admin = layui.admin
  ,form = layui.form;

  //文章管理
  table.render({
    elem: '#LAY-app-content-list'
    ,url:'/topic/getlist' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 60, title: 'ID', sort: true}
      ,{field: 'title',title:'标题',minWidth: 200,templet: '<div><a href="http://101.200.47.40/jie/detail/{{d.id}}" target="_blank" class="layui-table-link">{{d.title}}</a></div>'}
      ,{field: 'userId', title: '作者',templet: '<div><a href="http://101.200.47.40/user/home/{{d.userId}}" target="_blank" class="layui-table-link">{{d.userId}}</a></div>',minWidth: 130}
      ,{field: 'catename', title: '板块', minWidth: 130}
      ,{field: 'kindname',title: '分类', minWidth: 60}
      ,{field: 'experience',title: '悬赏', minWidth: 50}
      ,{field: 'time', title: '发表时间',minWidth: 160, templet: '<div>{{layui.util.toDateString(d.topicCreate)}}</div>',sort: true}
      ,{field: 'isGood',width: 60, title: '精帖', templet: '#good'}
      ,{field: 'isTop',width: 60, title: '置顶', templet: '#top'}
      ,{field: 'isEnd', width: 60,title: '已结', templet: '#end'}
      ,{field: 'status', title: '状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
      ,{title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list'}
    ]]
    ,page: true
    ,limit: 10
    ,limits: [10, 15, 20, 25, 30]
  });
  
  //监听工具条
  table.on('tool(LAY-app-content-list)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.confirm('确定删除此文章？', function(index){
        admin.req({
          url: '/topic/deltopic'
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
    } else if(obj.event === 'edit'){
      layer.open({
        type: 2
        ,title: '编辑文章'
        ,content: '/topic/listform?id='+ data.id
        ,maxmin: true
        ,area: ['550px', '550px']
        ,btn: ['确定', '取消']
        ,yes: function(index,layero){
          var iframeWindow = window['layui-layer-iframe'+ index]
          ,submit = layero.find('iframe').contents().find("#layuiadmin-app-form-edit");

          //监听提交
          iframeWindow.layui.form.on('submit(layuiadmin-app-form-edit)', function(data){
            var field = data.field; //获取提交的字段
            
            //提交 Ajax 成功后，静态更新表格中的数据
            admin.req({
              type:'post'
              ,url: '/topic/updatetopic'
              ,data:field
              ,success: function(res){
                if(res.code == 0){
                  layer.msg(res.msg,{icon:1,time:1000},function () {
                    obj.update({
                      id: field.id
                      ,title: field.title
                      ,userId: field.userId
                      ,catename: field.catename
                      ,kindname: field.kind
                      ,time: field.time
                      ,isGood: field.isGood != null?1:0
                      ,isTop: field.isTop != null?1:0
                      ,isEnd: field.isEnd != null?1:0
                      ,status: field.status != null?1:0
                      ,experience: field.experience
                    }); //数据更新
                    form.render();
                    layer.close(index); //关闭弹层
                  });
                }
              }
            });
          });  
          
          submit.trigger('click');
          submit.addClass("layui-btn-disabled");
          submit.attr('disabled', 'disabled');
        }
      });
    }
  });

  //板块管理
  table.render({
    elem: '#LAY-app-content-category'
    ,url: '/topic/getcategory' //模拟接口
    ,cols: [[
      {field: 'id', width: 100, title: 'ID' }
      ,{field: 'catename', title: '板块', minWidth: 100}
      ,{field: 'categoryCreate', title: '添加时间', templet: '<div>{{layui.util.toDateString(d.categoryCreate)}}</div>',sort: true}
      ,{field: 'categoryModified', title: '修改时间', templet: '<div>{{layui.util.toDateString(d.categoryModified)}}</div>',sort: true}
      ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#layuiadmin-app-cont-categorybar'}
    ]]
  });
  
  //监听工具条
  table.on('tool(LAY-app-content-category)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.confirm('确定删除此板块？', function(index){
        admin.req({
          url: '/topic/delcategory'
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
    } else if(obj.event === 'edit') {
      var tr = $(obj.tr);
      layer.open({
        type: 2
        , title: '编辑板块'
        , content: '/topic/categoryform?id=' + data.id
        , area: ['450px', '200px']
        , btn: ['确定', '取消']
        , yes: function (index, layero) {
          //获取iframe元素的值
          var iframeWindow = window['layui-layer-iframe' + index]
              , submit = layero.find('iframe').contents().find("#layuiadmin-app-form-edit");

          iframeWindow.layui.form.on('submit(layuiadmin-app-form-edit)', function (data) {
            var field = data.field;
            admin.req({
              type: 'post'
              , url: '/topic/updatecategory'
              , data: field
              , success: function (res) {
                if (res.code == 0) {
                  layer.msg(res.msg, {icon: 1, time: 1000}, function () {
                    obj.update({
                      id: field.id
                      , catename: field.catename
                      ,categoryCreate:field.categoryCreate
                      ,categoryModified:field.categoryModified
                    });
                    form.render();
                    layer.close(index);
                  });
                }
              }
            })

          });
          submit.trigger('click');
          submit.addClass("layui-btn-disabled");
          submit.attr('disabled', 'disabled');
        }
      });
    }
  });
  //分类管理
  table.render({
    elem: '#LAY-app-content-kind'
    ,url:'/topic/getkind' //模拟接口
    ,cols: [[
      {field: 'id', width: 100, title: 'ID'}
      ,{field: 'kindname', title: '分类名', minWidth: 100}
      ,{field: 'kindCreate', title: '添加时间', templet: '<div>{{layui.util.toDateString(d.kindCreate)}}</div>',sort: true}
      ,{field: 'kindModified', title: '修改时间', templet: '<div>{{layui.util.toDateString(d.kindModified)}}</div>',sort: true}
      ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#layuiadmin-app-cont-kindbar'}
    ]]
    ,text: '对不起，加载出现异常！'
  });

  //监听工具条
  table.on('tool(LAY-app-content-kind)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.confirm('确定删除此分类？', function(index){
        admin.req({
          url: '/topic/delkind'
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
    } else if(obj.event === 'edit'){
      var tr = $(obj.tr);
      layer.open({
        type: 2
        ,title: '编辑分类'
        ,content: '/topic/kindform?id='+ data.id
        ,area: ['450px', '200px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
          //获取iframe元素的值
          var iframeWindow = window['layui-layer-iframe'+ index]
              ,submit = layero.find('iframe').contents().find("#layuiadmin-app-form-edit");

          iframeWindow.layui.form.on('submit(layuiadmin-app-form-edit)', function(data){
            var field = data.field;
            admin.req({
              type:'post'
              ,url: '/topic/updatekind'
              ,data:field
              ,success: function(res){
                if(res.code == 0){
                  layer.msg(res.msg,{icon:1,time:1000},function () {
                    obj.update({
                      id:field.id
                      ,kindname:field.kindname
                      ,kindCreate:field.kindCreate
                      ,kindModified:field.kindModified
                    });
                    form.render();
                    layer.close(index);
                  });
                }
              }
            })

          });
          submit.trigger('click');
          submit.addClass("layui-btn-disabled");
          submit.attr('disabled', 'disabled');
        }
      });
    }
  });
  //评论管理
  table.render({
    elem: '#LAY-app-content-comm'
    ,url: '/topic/getComment' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 60, title: 'ID', sort: true}
      ,{field: 'userId', title: '评论者', width: 150,templet: '<div><a href="http://101.200.47.40/user/home/{{d.userId}}" target="_blank" class="layui-table-link">{{d.userId}}</a></div>'}
      ,{field: 'content', title: '评论内容', minWidth: 100,templet: '<div><a href="http://101.200.47.40/jie/detail/{{d.topicId}}?size=1000#{{d.id}}" target="_blank" class="layui-table-link">{{d.content}}</a></div>'}
      ,{field: 'commentCreate', title: '评论时间', width: 180,templet: '<div>{{layui.util.toDateString(d.commentCreate)}}</div>', sort: true}
      ,{field: 'agreeNum',align: 'center', title: '点赞数', width: 80}
      ,{field: 'isAccept', title: '采纳', width: 60,templet:'#accept'}
      ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-content-com'}
    ]]
    ,page: true
    ,limit: 10
    ,limits: [10, 15, 20, 25, 30]
    ,text: '对不起，加载出现异常！'
  });
  
  //监听工具条
  table.on('tool(LAY-app-content-comm)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.confirm('确定删除此条评论？', function(index){
        admin.req({
          url: '/topic/delcomment'
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
    } else if(obj.event === 'edit') {
      layer.open({
        type: 2
        ,title: '编辑评论'
        ,content: '/topic/contform?id='+data.id
        ,area: ['450px', '300px']
        ,btn: ['确定', '取消']
        ,yes: function(index, layero){
          var iframeWindow = window['layui-layer-iframe'+ index]
          ,submitID = 'layuiadmin-app-comm-submit'
          ,submit = layero.find('iframe').contents().find('#'+ submitID);

          //监听提交
          iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
            var field = data.field; //获取提交的字段
            //提交 Ajax 成功后，静态更新表格中的数据
            admin.req({
              type:'post'
              ,url: '/topic/updatecomment'
              ,data:field
              ,success: function(res){
                if(res.code == 0){
                  layer.msg(res.msg,{icon:1,time:1000},function () {
                    obj.update({
                      content: field.content
                    }); //数据更新
                    table.reload('LAY-app-content-comm'); //数据刷新
                    layer.close(index); //关闭弹层
                  });
                }
              }
            });
          });
          submit.trigger('click');
          submit.addClass("layui-btn-disabled");
          submit.attr('disabled', 'disabled');
        }
        ,success: function(layero, index){
          
        }
      });
    }
  });



  exports('contlist', {})
});