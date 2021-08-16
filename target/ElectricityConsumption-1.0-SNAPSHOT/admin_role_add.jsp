<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/2 0002
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<div class="layui-row layui-col-space10">
    <div class="layui-col-md3">
    </div>
    <div class="layui-col-md6">
        <form class="layui-form layui-form-pane" action="">
            <div class="layui-form-item">
                <label class="layui-form-label">角色名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="name" id="name" required  lay-verify="required" placeholder="请输入角色名称" autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">角色信息</label>
                <div class="layui-input-inline">
                    <input type="text" name="remark" id="remark" required lay-verify="required" placeholder="请输入角色信息" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div id="test3" class="demo-transfer"></div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
    <div class="layui-col-md3">
    </div>
</div>
<script src="js/jquery-3.4.1.js"></script>
<script src="layui/layui.js"></script>
<script>
    var data = "";
    $.post("role/selectPerms",function (result) {
        data = result;
    });
    //Demo
    layui.use(['form','transfer', 'layer', 'util'], function(){
        var $ = layui.$
            , transfer = layui.transfer
            , form = layui.form
            , util = layui.util;
        transfer.render({
            elem: '#test3'
            ,data:data
            ,parseData: function(res) {
                return {
                    "value": res.id //数据值
                    , "title": res.remark //数据标题
                }
            }
            ,title: ['未获得的权限', '以获得的权限']
            ,showSearch: true
            ,id:'key123'
        })
        //监听提交
        form.on('submit(formDemo)', function(data){
            var getData = transfer.getData('key123');
            var post = data.field;
            var ids = "";
            for(var i =0 ;i<getData.length;i++){
                ids += getData[i].value
                if(i != (getData.length-1)){
                    ids+=",";
                }
            }
            $.post("role/add",{"name":post.name,"remark":post.remark,"ids":ids},function (result) {
                if(result == 0){
                    alert("添加失败！")
                }else{
                    alert("添加成功！");
                }
            })
            return false;
        });
    });

</script>
</body>
</html>
