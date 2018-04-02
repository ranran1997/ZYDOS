<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="Xenon Boostrap Admin Panel"/>
    <meta name="author" content=""/>

    <title>ZYDOS智能导航</title>

    <!-- <link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic"> -->
    <link rel="stylesheet" href="/assets/css/fonts/linecons/css/linecons.css">
    <link rel="stylesheet" href="/assets/css/fonts/fontawesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/bootstrap.css">
    <link rel="stylesheet" href="/assets/css/xenon-core.css">
    <link rel="stylesheet" href="/assets/css/xenon-forms.css">
    <link rel="stylesheet" href="/assets/css/xenon-components.css">
    <link rel="stylesheet" href="/assets/css/xenon-skins.css">
    <link rel="stylesheet" href="/assets/css/custom.css">

    <script src="/assets/js/jquery-1.11.1.min.js"></script>

    <%--引入高德地图JavaScript API文件:--%>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=${jsAPI}"></script>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <%--自适应显示--%>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/assets/js/html5shiv.min.js"></script>
    <script src="/assets/js/respond.min.js"></script>
    <![endif]-->
    <style>
        #container {
            width: 100%;
            height: 100%;
        }

        /* body {
             font-size: 12px;
         }
         .amap-info-close{
             top:10px;
         }

 */
        .info {
            border: solid 1px silver;
        }

        div.info-top {
            position: relative;
            background: none repeat scroll 0 0 #F9F9F9;
            border-bottom: 1px solid #CCC;
            border-radius: 5px 5px 0 0;
        }

        div.info-top div {
            display: inline-block;
            color: #333333;
            font-size: 14px;
            font-weight: bold;
            line-height: 31px;
            padding: 0 10px;
        }

        div.info-top img {
            position: absolute;
            top: 10px;
            right: 10px;
            transition-duration: 0.25s;
        }

        div.info-top img:hover {
            box-shadow: 0px 0px 5px #000;
        }

        div.info-middle {
            font-size: 12px;
            padding: 6px;
            line-height: 20px;
        }

        div.info-bottom {
            height: 0px;
            width: 100%;
            clear: both;
            text-align: center;
        }

        div.info-bottom img {
            position: relative;
            z-index: 104;
        }

        span {
            margin-left: 5px;
            font-size: 11px;
        }

        .info-middle img {
            float: left;
            margin-right: 6px;
        }

        .amap-marker .marker-route {
            position: absolute;
            width: 40px;
            height: 44px;
            color: #e90000;
            background: url(http://webapi.amap.com/theme/v1.3/images/newpc/poi-1.png) no-repeat;
            cursor: pointer;
        }
        .amap-marker .marker-marker-bus-from {
            background-position: -334px -180px;
        }
    </style>
</head>
<body class="page-body">
<div id="container"></div>
<script type="text/javascript">
    var map,marker,infoWindow,polyline;
    var path = ${path2};
    function init_Param(){
        map = new AMap.Map('container', {
            resizeEnable: true,
            zoom: 16  //地图显示缩放级别
        });

        //点标注
        var lnglats = [    //初始化坐标点
                [${self}],
            <c:forEach var="store" items="${path}">  //换成json数据如何循环
            [${store.lng}, ${store.lat}],
            </c:forEach>
        ];


        marker = new AMap.Marker({  //当前用户位置点标记
            map: map,
            position: lnglats[0], //基点位置
            offset: new AMap.Pixel(-17, -42), //相对于基点的偏移位置
            draggable: false,  //是否可拖动
            content: '<div class="marker-route marker-marker-bus-from"></div>'   //自定义点标记覆盖物内容

        });
        for (var i = 1, marker; i < lnglats.length; i++) {
            var icon = '/assets/images/map/markers/mark_b'+i+'.png';
            marker = new AMap.Marker({
                map: map,
                position:lnglats[i],
                extData:i,
                icon:icon
            });
            AMap.event.addListener(marker, 'click', onclick);//注册监听，当点击marker后触发

            //marker.on('click', onclick);
           //marker.emit('click', {target: marker});
        }
        map.setFitView();//自适应显示
        /*依次连线所有标注点*/
         polyline = new AMap.Polyline({
            path: lnglats,          //设置线覆盖物路径
            strokeColor: "#3366FF", //线颜色
            strokeOpacity: 1,       //线透明度
            strokeWeight: 2,        //线宽
            strokeStyle: "solid",   //线样式
            strokeDasharray: [10, 5], //补充线样式
            geodesic: true
        });
        polyline.setMap(map);

        infoWindow = new AMap.InfoWindow({
            isCustom: true,  //使用自定义窗体
            //content: createInfoWindow(title[i], content[i].join("<br/>")),
            offset: new AMap.Pixel(16, -45)
        });

    }

    $(document).ready(function(){
        init_Param();
    });
    //点击marker后触发调用onclick函数
    var onclick = function(e){
        var i = e.target.H.extData;
        var urge = "";
        if(path[i-1].isUrgent == 1){
            urge = "紧急";
        }
        var position = [e.lnglat.lng,e.lnglat.lat];
        e.target.setTitle(path[i-1].storeName+"<span style='font-size:11px;color:#F00;'>"+urge+"</span>");
        infoWindow.setContent(createInfoWindow(e.target.Pe.title,createContent(i)));
        infoWindow.open(map, position);
    };
    //信息窗体内容
    function createContent(i){
        var content = [];
        content.push("<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134'>地址："+path[i-1].address);
        content.push("电话："+path[i-1].storePhone);
        content.push("附加："+ path[i-1].extraMsg);
        return content.join("<br>");
    }



    //构建自定义信息窗体
    function createInfoWindow(title, content) {
        var info = document.createElement("div");
        info.className = "info";

        //可以通过下面的方式修改自定义窗体的宽高
        //info.style.width = "400px";
        // 定义顶部标题
        var top = document.createElement("div");
        var titleD = document.createElement("div");
        var closeX = document.createElement("img");
        top.className = "info-top";
        titleD.innerHTML = title;
        closeX.src = "http://webapi.amap.com/images/close2.gif";
        closeX.onclick = closeInfoWindow;

        top.appendChild(titleD);
        top.appendChild(closeX);
        info.appendChild(top);

        // 定义中部内容
        var middle = document.createElement("div");
        middle.className = "info-middle";
        middle.style.backgroundColor = 'white';
        middle.innerHTML = content;
        info.appendChild(middle);

        // 定义底部内容
        var bottom = document.createElement("div");
        bottom.className = "info-bottom";
        bottom.style.position = 'relative';
        bottom.style.top = '0px';
        bottom.style.margin = '0 auto';
        var sharp = document.createElement("img");
        sharp.src = "http://webapi.amap.com/images/sharp.png";
        bottom.appendChild(sharp);
        info.appendChild(bottom);
        return info;
    }

    //关闭信息窗体
    function closeInfoWindow() {
        map.clearInfoWindow();
    }




    /*
     //Marker多级展示
     //1、marker构建
     var createMarker = function(data,hide) {
     var div = document.createElement('div');
     div.className = 'circle';
     var r = Math.floor(data.count / 1024);
     div.style.backgroundColor = 'rgb('+r*8+',100,255)';
     div.innerHTML = data.count || 0;
     var marker = new AMap.Marker({
     content: div,
     title:data.name,
     position: data.center.split(','),
     offset: new AMap.Pixel(-30, 5),
     zIndex: data.count
     });
     marker.subMarkers = [];
     if(!hide){
     marker.setMap(mapObj)
     }
     if(data.subDistricts&&data.subDistricts.length){
     for(var i = 0 ; i<data.subDistricts.length;i+=1){
     marker.subMarkers.push(createMarker(data.subDistricts[i],true));
     }
     }
     return marker;
     }

     /!* 2、这里，为每个Marker绑定一个click事件。当Marker点被点击的时候，我们将显示其下级的
     Marker标记，setFitView方法用来将地图调整到合适的范围来显示我们需要展示的markers。*!/
     var _onClick = function(e){
     if(e.target.subMarkers){
     mapObj.add(e.target.subMarkers);
     mapObj.setFitView(e.target.subMarkers);
     mapObj.remove(markers)
     }
     }
     AMap.event.addListener(marker, 'click', _onClick);

     /!* 3、同时为地图绑定一个zoomend事件，当地图缩放结束后停留的级别小于8的时候将溢出
     所有父一级的标记*!/
     var _onZoomEnd = function(e) {
     if (mapObj.getZoom() < 8) {
     for (var i = 0; i < markers.length; i += 1) {
     mapObj.remove(markers[i].subMarkers)
     }
     }
     }
     //示例
     var markers = []; //province见Demo引用的JS文件
     for (var i = 0; i < provinces.length; i += 1) {
     var marker = createMarker(provinces[i]);
     markers.push(marker);
     AMap.event.addListener(marker, 'click', _onClick);
     }
     AMap.event.addListener(mapObj, 'zoomend', _onZoomEnd);*/
</script>

</body>
</html>