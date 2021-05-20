<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/19 0019
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>实时线损管理系统</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <link rel="icon" href="img/icon.jpg" >
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">实时线损管理系统</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <shiro:principal/>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="user/out">注销退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree " lay-shrink="all" lay-filter="test">

                <li class="layui-nav-item  layui-nav-itemed">
                    <a href="javascript:;">实时线损查询</a>
                    <dl class="layui-nav-child">
                    <dd><a href="show.html">单用户查询</a></dd>
                </dl>
                    <dl class="layui-nav-child">
                        <dd><a href="realEle.html">台区供电所实时电流电压查询</a></dd>
                    </dl>
                    <dl class="layui-nav-child">
                        <dd><a href="relation.html">查询台区详细信息</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">下载中心</a>
                    <dl class="layui-nav-child">
                        <dd><a href="selectTgOrg.html">查询台区/供电所</a></dd>
                        <dd><a href="update.html">上传文档</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a class="" href="javascript:;">用户管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="user.html">用户管理</a></dd>
                        <dd><a href="javascript:;">添加用户</a></dd>
                        <dd><a href="javascript:;">权限角色管理</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <iframe id="iframeMain" src="" style="width: 100%;height: 100%;"></iframe>
    </div>
</div>
<script src="layui/layui.js"></script>
<script src="js/jquery-3.4.1.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });
    $(document).ready(function(){
        $("dd>a").click(function (e) {
            e.preventDefault();
            $("#iframeMain").attr("src",$(this).attr("href"));
        });
    });
</script>
</body>
</html>
