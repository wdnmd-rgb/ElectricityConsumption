
    layui.use('element', function(){
        var element = layui.element;
        var active={
            tabAdd:function(url,id,name){
                element.tabAdd('contentnav',{
                    title:name,
                    content:'<iframe data-frameid="'+id+'" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;"></iframe>',
                    id:id
                });
                rightMenu();
                iframeWH();
            },
            tabChange:function(id){
                element.tabChange('contentnav',id);
            },
            tabDelete:function(id){
                element.tabDelete('contentnav',id);
            },
            tabDeleteAll:function(ids){
                $.each(ids,function(index,item){
                    element.tabDelete('contentnav',item);
                });
            }
        };
        $(".site-url").on('click',function(){
            var nav=$(this);
            var length = $("ul.layui-tab-title li").length;
            if(length<=0){
                active.tabAdd(nav.attr("data-url"),nav.attr("data-id"),nav.attr("data-title"));
            }else{
                var isData=false;
                $.each($("ul.layui-tab-title li"),function(){
                    if($(this).attr("lay-id")==nav.attr("data-id")){
                        isData=true;
                    }
                });
                if(isData==false){
                    active.tabAdd(nav.attr("data-url"),nav.attr("data-id"),nav.attr("data-title"));
                }
                active.tabChange(nav.attr("data-id"));
            }
        });
        function rightMenu(){
            //右击弹出
            $(".layui-tab-title li").on('contextmenu',function(e){
                var menu=$(".rightmenu");
                menu.find("li").attr('data-id',$(this).attr("lay-id"));
                l = e.clientX;
                t = e.clientY;
                menu.css({ left:l, top:t}).show();
                return false;
            });
            //左键点击隐藏
            $("body,.layui-tab-title li").click(function(){
                $(".rightmenu").hide();
            });
        }
        $(".rightmenu li").click(function(){
            if($(this).attr("data-type")=="closethis"){
                active.tabDelete($(this).attr("data-id"));
            }else if($(this).attr("data-type")=="closeall"){
                var tabtitle = $(".layui-tab-title li");
                var ids = new Array();
                tabtitle.each(function(i){
                    ids.push($(this).attr("lay-id"));
                });
                //如果关闭所有 ，即将所有的lay-id放进数组，执行tabDeleteAll
                active.tabDeleteAll(ids);
            }
            $('.rightmenu').hide(); //最后再隐藏右键菜单
        });
        function iframeWH(){
            var H = $(window).height()-80;
            $("iframe").css("height",H+"px");
        }
        $(window).resize(function(){
            iframeWH();
        });

         // 左侧栏向左折叠
         $("#menuBar").on('click', function () {
                if ($("body").hasClass("mini-sidebar")) {
                    $("body").removeClass("mini-sidebar");
                    $('.layui-nav-tree').animate({width:'100px'},300);
                    $('.tabs').animate({'margin-left':'100px'},300)
                    $(this).addClass("layui-icon-shrink-right");
                    $(this).removeClass("layui-icon-spread-left");
                } else {
                    $("body").addClass("mini-sidebar");
                    $('.layui-nav-tree').animate({width:'200px'},300);
                    $(this).removeClass("layui-icon-shrink-right");
                    $('.tabs').animate({'margin-left':'200px'},300)
                    $(this).addClass("layui-icon-spread-left");
                }
            });
    }); 

    //修改密码
    $('.modify').click(function(){
        layer.open({
       title: '修改密码'
      ,content: `<form class="layui-form" action="">
                   <div class="layui-form-item">
                      <label class="layui-form-label">账号:</label>
                         <div class="layui-input-block">
                             <input type="text" name="title" required  lay-verify="required" placeholder="请输入账号" autocomplete="off" class="layui-input inputs">
                     </div>
                     <div class="layui-form-item">
                     <label class="layui-form-label">密码:</label>
                     <div class="layui-input-inline">
                       <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                     </div>
                  </div>`
});
    })

    // 首页内容
    layui.use(['laydate', 'form', 'table'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        var table = layui.table;

        //执行一个laydate实例
        laydate.render({
            elem: '#date',//指定元素
            value: new Date()
            , min: -10
            , max: 0
        });
        table.render({
            id: "testReload"
            , elem: '#test'
            , url: 'tgLine/queryTgLineLoss'
            , cols: [[
                  {field: 'orgName', title: '供电所名称',width:180}
                , {field: 'tgName', title: '台区名称',width:220}
                , {field: 'tgNo', title: '台区编号',width:100}
                , {field: 'eventTime', title: '时间',width:100}
                , {field: 'count', title: '台区用户数',width:100}
                , {field: 'realCount', title: '实际用户数',width:100}
                , {field: 'remark0', title: '丢失点数量',width:100}
                , {field: 'remark1', title: '跳变点数量',width:100}
                , {field: 'remark2', title: '负值点数量',width:100}
                , {field: 'remark3', title: '缺失点数量',width:100}
                , {field: 'ppq', title: '考核电量',width:100}
                , {field: 'upq', title: '用户电量',width:100}
                , {field: 'lossPq', title: '损失电量',width:100}
                , {field: 'rate', title: '线损率(%)',width:100}
            ]]
        });

        form.render();
        form.on('submit(provinceSearch)', function (data) {
            $("#testDiv2").css("display", "none");
            $("#testDiv1").css("display", "none");
            $("#testDiv4").css("display", "none");
            $("#testDiv3").css("display", "none");
            $("#testDiv5").css("display", "none");
            $("#testDiv").css("display", "none");
            var loading = layer.load(0, {
                shade: false,
                time: 100*1000
            });
            var formData = data.field;
            var no = formData.no;
            var date = formData.date;
            var index = $("#index").val();
            table.reload('testReload', {
                where: {
                    tgNo: no,
                    date: date,
                    index:index
                },
                url: 'tgLine/queryTgLineLoss',
                method: 'post',
                done: function (res) {
                    console.log(res.msg);
                    $("#update").attr("href", res.msg[4]);
                    show("main",res.msg[0],res.msg[1],res.msg[2],res.msg[3],res.msg[5]);
                    show("main1",res.msg[0],res.msg[6],res.msg[7],res.msg[8],res.msg[5]);
                    layer.close(loading);
                    $("#testDiv").css("display", "");
                    $("#testDiv2").css("display", "");
                    $("#testDiv4").css("display", "");
                    $("#testDiv3").css("display", "");
                }
            });
            return false;
        });


    });

    function show(id,time,array1,array2,array3,array4) {
        var chartDom = document.getElementById(id);
        var myChart = echarts.init(chartDom);
        var option;
        option = {
            title: {
                text: name,
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['考核表电量', '用户表电量','线损率','缺点数']
            },
            toolbox: {
                show: true,
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    dataView: {readOnly: false},
                    magicType: {type: ['line', 'bar']},
                    restore: {},
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: time
            },
            yAxis: [
                {
                    name:'电量',
                    type: 'value',
                    offset: 30,
                    axisLabel: {
                        formatter: '{value}'
                    },
                    position: 'right'
                },
                {
                    name:'线损率',
                    type: 'value',
                    offset: 30,
                    axisLabel: {
                        formatter: '{value}%'
                    },
                    position: 'left'
                },
                {
                    name:'缺点数',
                    type: 'value',
                    offset: 80,
                    axisLabel: {
                        formatter: '{value}'
                    },
                    position: 'left'
                }
            ],
            series: [
                {
                    name: '考核表电量',
                    type: 'bar',
                    yAxisIndex:0,
                    barWidth : 20,
                    data: array1
                },
                {
                    name: '用户表电量',
                    type: 'bar',
                    yAxisIndex:0,
                    barWidth : 20,
                    data: array2
                },{
                    name: '线损率',
                    type: 'line',
                    yAxisIndex:1,
                    data: array3
                },{
                    name: '缺点数',
                    type: 'line',
                    yAxisIndex:2,
                    data: array4
                }
            ]
        };
        myChart.setOption(option);
    }
    function change() {
        $("#testDiv3").css("display", "none");
        $("#testDiv4").css("display", "none");
        $("#testDiv1").css("display", "");
        $("#testDiv5").css("display", "");
    }
    function change2() {
        $("#testDiv5").css("display", "none");
        $("#testDiv1").css("display", "none");
        $("#testDiv4").css("display", "");
        $("#testDiv3").css("display", "");
    }