var arrCheck = false;
var nowDriving = false;
var viewFixed = true;
var zoomSize = 14;

var cX, cY;
var startX, startY;
var currentP = 0;
var currMarker;
var accidentMarkers = [];
var gongsaMarkers = [];
var emergencyMarkers = [];

var checkClickEmerMarker=false;
var checkClickAccMarker=false;
var checkClickGongMarker=false;

var destination, start; // marker
var routeX = [35.858018, 35.862313, 35.862313, 35.858108, 35.861992, 35.860165, 35.860165, 35.863499, 35.863499, 35.863926, 35.864235, 35.864423, 35.865858, 35.866048, 35.866048, 35.869558, 35.869558, 35.869417, 35.873957, 35.873956, 35.874093, 35.876422, 35.876667, 35.876666, 35.877355, 35.877553, 35.877552, 35.879392, 35.879391, 35.878616, 35.881913, 35.881912, 35.882943, 35.882813, 35.882813, 35.892907, 35.892906, 35.893510, 35.890567, 35.890402, 35.890401, 35.894782, 35.894781, 35.895017, 35.889382, 35.889042, 35.889041, 35.881643, 35.902328, 35.901585, 35.901585, 35.881607, 35.881607, 35.897000, 35.898348, 35.898348, 35.899493, 35.884985, 35.884985, 35.884030, 35.882593, 35.882593, 35.905005,
];
var routeY = [128.648288, 128.648248, 128.648248, 128.647540, 128.647472, 128.647197, 128.647197, 128.647005, 128.647005, 128.645816, 128.643975, 128.643860, 128.640520, 128.639740, 128.639740, 128.637435, 128.637435, 128.637268, 128.634607, 128.634606, 128.634475, 128.632878, 128.632833, 128.632833, 128.630074, 128.630006, 128.630005, 128.626994, 128.626994, 128.626875, 128.625688, 128.625688, 128.622597, 128.621450, 128.621450, 128.621338, 128.621338, 128.620015, 128.619380, 128.619075, 128.619075, 128.618743, 128.618743, 128.618108, 128.617488, 128.617205, 128.617205, 128.617127, 128.616098, 128.616073, 128.616073, 128.615972, 128.615971, 128.615617, 128.615235, 128.615235, 128.615133, 128.614497, 128.614496, 128.613352, 128.612402, 128.612401, 128.602910,
];
var map;


function goCurr() {
    console.log("viewFixed = TRUE \n");
    viewFixed = true;
    if (cX != null && cY != null) {
        map.setCenter(new naver.maps.LatLng(cX, cY));
        map.setZoom(zoomSize);
    }
}

function changeStep(stage) {
    var img = document.getElementById("step");
    console.log("step " + stage + "\n");
    switch (stage) {
        case 0:
            img.src = "./img/step1.png"
            break;
        case 1:
            img.src = "./img/step2.png"
            break;
        case 2:
            img.src = "./img/step3.png"
            break;
    }
}


function getClickAccMarker2(obj,i) {
    return function (e) {
        checkClickAccMarker = true;
        $("#text2").empty();
        $("#text2").append("<tr>");
        $("#text2").append("<th>사고 정보</th>");
        $("#text2").append("</tr>");

        if(obj.info=="car") {
            $("#text2").append("<td>" + "< 외부 > 차량간 추돌 사고" + "</td>");
        }else {
            $("#text2").append("<td>" + "< 외부 > " + obj.info + "</td>");
        }
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>" + "id: " +obj.id + "</td>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("</br>");
        $("#text2").append("</tr>");
    }
}
function getClickAccMarker(obj,i){
    return function (e) {
        checkClickAccMarker=true;

        $("#text2").empty();
        $("#text2").append("<tr>");
        $("#text2").append("<th>사고 정보</th>");
        $("#text2").append("<th>사고 위치</th>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>" + "< API >" + obj.incidenttitle + "</td>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>" + obj.location + "</td>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("</br>");
        $("#text2").append("</tr>");

    }
}
function setAccMarkers(x, y,info,type) {

    for(var j=0;j<accidentMarkers.length;j++){
        var ch=true;
        for(var i=0;i<x.length;i++) {
            if(x[i]==accidentMarkers[j].position.lng() && y[i]==accidentMarkers[j].position.lat()){
                x.slice(i,i); y.slice(i,i); info.slice(i,i);
                ch=false;
                break;
            }
        }
        accidentMarkers[j].setOptions({map:null});
        accidentMarkers.slice(j,j);
    }

    for(var i=0;i<x.length;i++){
        var newMarker = new naver.maps.Marker({
            position: new naver.maps.LatLng(x[i], y[i]),
            map: map,
            icon: {
                url: './img/accident.png',
            }
        });
        if(type=="ext") {
            naver.maps.Event.addListener(newMarker, 'click', getClickAccMarker2(info[i], i));
        }else if(type=="api") {
            naver.maps.Event.addListener(newMarker, 'click', getClickAccMarker(info[i], i));
        }
        accidentMarkers.push(newMarker);
    }

}
function setEmerMarkers(x,y,info) {

    for(var j=0;j<emergencyMarkers.length;j++){
        var ch=true;
        for(var i=0;i<x.length;i++) {
            if(x[i]==emergencyMarkers[j].position.lng() && y[i]==emergencyMarkers[j].position.lat()) {
                x.slice(i,i); y.slice(i,i); info.slice(i,i);
                ch=false;
                break;
            }
        }
        emergencyMarkers[j].setOptions({map:null});
        emergencyMarkers.slice(j,j);
    }

    for(var i=0;i<x.length;i++){
        var newMarker = new naver.maps.Marker({
            position: new naver.maps.LatLng(x[i], y[i]),
            map: map,
            icon: {
                url: './img/emergency.png',
            }
        });
        naver.maps.Event.addListener(newMarker, 'click', getClickEmerMarker(info[i], i));
        emergencyMarkers.push(newMarker);
    }

}
function getClickEmerMarker(obj,i) {
    return function (e) {
        checkClickEmerMarker=true;
        $("#text1").empty();
        $("#text1").append("<tr>");
        $("#text1").append("<th>돌발 상황 정보</th>");
        $("#text1").append("<th></th>");
        $("#text1").append("</tr>");

        $("#text1").append("<tr>");
        $("#text1").append("<td>" + obj.info + "</td>");
        $("#text1").append("</tr>");

        $("#text1").append("<tr>");
        $("#text1").append("</br>");
        $("#text1").append("</tr>");
    }

}
function getClickGongsaMarker(obj,i) {
    return function(e) {
        checkClickGongMarker = true;

        console.log("get clicklistener gongsa marker>> (" + i + ")" + obj.incidenttitle);

        $("#text3").empty();
        $("#text3").append("<tr>");
        $("#text3").append("<th>공사 정보</th>");
        $("#text3").append("<th>공사 위치</th>");
        $("#text3").append("</tr>");

        $("#text3").append("<tr>");
        $("#text3").append("<td colspan='2'>" + obj.incidenttitle + "</td>");
        $("#text3").append("</tr>");

        $("#text3").append("<tr>");
        $("#text3").append("<td colspan='2'>" + obj.location + "</td>");
        $("#text3").append("</tr>");

        $("#text3").append("<tr>");
        $("#text3").append("</br>");
        $("#text3").append("</tr>");
    }
}

function setGongMarkers(x,y,info) {
    console.log("set marker >> gongsaMarkers length:"+gongsaMarkers.length);
    console.log("set marker >> x length: "+ x.length);

    for(var j=0;j<gongsaMarkers.length;j++){
        var ch=true;
        for(var i=0;i<x.length;i++) {
            if(x[i]==gongsaMarkers[j].position.lng() && y[i]==gongsaMarkers[j].position.lat()){
                x.slice(i,i); y.slice(i,i); info.slice(i,i);
                ch=false;
                break;
            }
        }
        gongsaMarkers[j].setOptions({map:null});
        gongsaMarkers.slice(j,j);
    }

    for(var i=0;i<x.length;i++){
        var newMarker = new naver.maps.Marker({
            position: new naver.maps.LatLng(x[i], y[i]),
            map: map,
            icon: {
                url: './img/gongsa.png',
            }
        });
        console.log("add clicklistener gongsa marker>> " + info[i].incidenttitle);
        naver.maps.Event.addListener(newMarker, 'click', getClickGongsaMarker(info[i], i));
        gongsaMarkers.push(newMarker);
    }
}

function clearGongMarker() {
    if(gongsaMarkers==null) return;
    console.log("clear marker >> gongsaMarkers length:"+gongsaMarkers.length);
    for(var i=0;i<gongsaMarkers.length;i++){
        gongsaMarkers[i].setOptions({map:null});
    }
    gongsaMarkers=[];
}

function clearEmerMarker() {
    if(emergencyMarkers==null) return;
    console.log("clear marker >> emergencyMarkers length:"+emergencyMarkers.length);
    for(var i=0;i<emergencyMarkers.length;i++){
        emergencyMarkers[i].setOptions({map:null});
    }
    emergencyMarkers=[];
}

function clearAccMarker() {
    if(accidentMarkers==null) return;
    console.log("clear marker >> AccMarkers length:"+accidentMarkers.length);
    for(var i=0;i<accidentMarkers.length;i++){
        accidentMarkers[i].setOptions({map:null});
    }
    accidentMarkers=[];
}

function fixDestination() {
    destination.setOptions({
        draggable: false
    });
    nowDriving = true;
}

function setStartMarker(x,y) {
    startX=x;
    startY=y;
    start = new naver.maps.Marker({
        position: new naver.maps.LatLng(startX, startY),
        map: map,
        icon: './img/start2.png'
    });
}

function initMap() {

    map = new naver.maps.Map('map', {
        mapTypeId: naver.maps.MapTypeId.HYBRID,
        mapDataControl: false,
        zoomControl: true,
        zoomControlOptions: {
            style: naver.maps.ZoomControlStyle.SMALL,
            position: naver.maps.Position.BOTTOM_LEFT
        },
        center: new naver.maps.LatLng(startX,startY),
        zoom: zoomSize
    });


    naver.maps.Event.addListener(map, 'click', function (e) {
        arrCheck = true;
        if (!nowDriving) {
            if (destination == null) {
                destination = new naver.maps.Marker({
                    position: e.coord,
                    map: map,
                    draggable: true,
                    icon: '..\\img\\destination2.png'
                });

            } else destination.setPosition(e.coord);
        }
    });

    naver.maps.Event.addListener(map, 'zoom_changed', function (zoom) {
        viewFixed = false;
    });


}


function setCarMarker(x, y) {
    if (currMarker == null) {
        currMarker = new naver.maps.Marker({
            position: new naver.maps.LatLng(x, y),
            map: map,
            icon: './img/current.png'
        });
    } else {
        currMarker.setPosition(new naver.maps.LatLng(x, y));
    }
    if (viewFixed) {
        map.setZoom(zoomSize);
        map.setOptions({
            center: new naver.maps.LatLng(x, y)
        });
    }
}


function move_mainCursor(data) {
    // alert(data.x + " " + data.y);
    cX = data.y;
    cY = data.x;
    if(currentP==0) {
        setStartMarker(cX,cY);
        viewFixed=true;
    }
    if(currentP==1)
        viewFixed=true;

    changeStep(2);
    setCarMarker(cX, cY);
    currentP++;
}
