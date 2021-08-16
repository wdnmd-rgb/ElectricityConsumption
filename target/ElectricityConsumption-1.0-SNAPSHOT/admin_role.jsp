<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/2 0002
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<script src="layui/layui.js"></script>
<script src="js/jquery-3.4.1.js"></script>
<table class="layui-hide" id="test" lay-filter="test"></table>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">新增权限角色</button>
    </div>
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="perms">管理角色权限</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>
    layui.use('table', function(){
        var table = layui.table;
        var form = layui.form;

        table.render({
            id:"provinceReload"
            ,elem: '#test'
            ,url:'role/selectpage'
            ,cellMinWidth:100
            ,toolbar: '#toolbarDemo'
            ,cols: [[
                {type:'checkbox'}
                ,{field:'id', title: '角色ID'}
                ,{field:'name', title: '角色名称'}
                ,{field:'remark', title: '角色信息'}
                ,{fixed: 'right', align:'center', toolbar: '#barDemo'}
            ]]
            ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['prev', 'page', 'next','skip', 'count'] //自定义分页布局
                ,limit:3
                ,groups: 2 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页
            }
        });
        table.on('tool(test)', function(obj){
            var data = obj.data;
            var id = data.id;
            if(obj.event === 'del'){
                if(confirm("确认删除吗？")){
                    $.post("role/delete",{"id":id},function(result){
                        if(result == 0){
                            alert("删除失败！")
                        }else if(result == 1){
                            alert("删除成功！")
                            location.reload();
                        }else{
                            alert("权限不足");
                        }
                    });
                }
            } else if(obj.event === 'edit'){
                layer.open({
                    type: 2,
                    content: 'department/selectOne?id='+id
                    ,area: ['800px', '600px']
                    ,title: ['编辑部门', 'font-size:18px;']
                });
            }else{
                location.href = 'admin_role_perms.jsp?id='+id;
            }
        });

        table.on('toolbar(test)', function(){
            layer.open({
                type: 2,
                content: 'admin_role_add.jsp'
                ,area: ['800px', '600px']
                ,title: ['新增权限管理', 'font-size:18px;']
            });
        });
    });

</script>
</body>
</html>
