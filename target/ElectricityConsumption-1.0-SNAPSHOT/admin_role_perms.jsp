<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/2 0002
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css">
</head>
<body>
<div id="test3" class="demo-transfer"></div>
<div class="layui-btn-container">
    <button type="button" class="layui-btn" lay-demotransferactive="getData">提交</button>
</div>
<%
    String id = request.getParameter("id");
%>
<script src="layui/layui.js"></script>
<script src="js/jquery-3.4.1.js"></script>
<script>
    var data = "",data2 = ""
    $.post("role/selectPerms",function (result) {
        data = result;
    });
    $.post("role/selectRolePerms",{"id":"<%=id%>"},function (result) {
        data2 = result;
    });
    layui.use(['transfer', 'layer', 'util'], function() {
        var $ = layui.$
            , transfer = layui.transfer
            , layer = layui.layer
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
            ,value: data2
            ,showSearch: true
            ,id:'key123'
        })
        util.event('lay-demoTransferActive', {
            getData: function(othis){
                var getData = transfer.getData('key123'); //获取右侧数据
                var ids = "";
                for(var i =0 ;i<getData.length;i++){
                    ids += getData[i].value
                    if(i != (getData.length-1)){
                        ids+=",";
                    }
                }
                $.post("role/updateRolePerms",{"ids":ids,"id":"<%=id%>"},function (result) {
                    if(result == 0){
                        alert("修改失败！")
                    }else{
                        alert("修改成功！")
                        location.reload();
                    }
                });
            }
        });
    });
</script>
</body>
</html>
