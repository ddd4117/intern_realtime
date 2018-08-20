var arrCheck = false;
var nowDriving = false;
var viewFixed = true;
var zoomSize = 12;

var cX, cY;
var currentP = 0;
var currMarker;
var curCircle;

var accidentMarkers = [];
var accidentMarkers2= [];
var gongsaMarkers = [];
var emergencyMarkers = [];

var checkClickEmerMarker = false;
var checkClickAccMarker = false;
var checkClickAccMarker2 = false;
var checkClickGongMarker = false;

var startX, startY;
var destination, start; // marker

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

function getClickAccMarker2(obj, i) {
    return function (e) {
        checkClickAccMarker2 = true;
        checkClickAccMarker=false;
        $("#text2").empty();
        $("#text2").append("<tr>");
        $("#text2").append("<th>사고 정보</th>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        if (obj.info == "car") {
            $("#text2").append("<td>" + "< 외부 > 차량간 추돌 사고" + "</td>");
        } else {
            $("#text2").append("<td>" + "< 외부 > " + obj.info + "</td>");
        }
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>" + "id: " + obj.id + "</td>");
        $("#text2").append("</tr>");
    }
}

function getClickAccMarker(obj, i) {
    return function (e) {
        checkClickAccMarker = true;
        checkClickAccMarker2 = false;

        $("#text2").empty();
        $("#text2").append("<tr>");
        $("#text2").append("<th>사고 정보</th>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>" + "< API >" + obj.incidenttitle + "</td>");
        $("#text2").append("</tr>");

        $("#text2").append("<tr>");
        $("#text2").append("<td>발생 위치: " + obj.location + "</td>");
        $("#text2").append("</tr>");
    }
}

function getClickEmerMarker(obj, i) {
    return function (e) {
        checkClickEmerMarker = true;
        $("#text1").empty();
        $("#text1").append("<tr>");
        $("#text1").append("<th>돌발 상황 정보</th>");
        $("#text1").append("<th></th>");
        $("#text1").append("</tr>");

        $("#text1").append("<tr>");
        $("#text1").append("<td>" + obj.info + "</td>");
        $("#text1").append("</tr>");
    }

}

function getClickGongsaMarker(obj, i) {
    return function (e) {
        checkClickGongMarker = true;

        console.log("get clicklistener gongsa marker>> (" + i + ")" + obj.incidenttitle);

        $("#text3").empty();
        $("#text3").append("<tr>");
        $("#text3").append("<th>공사 정보</th>");
        $("#text3").append("</tr>");

        $("#text3").append("<tr>");
        $("#text3").append("<td colspan='2'>" + obj.incidenttitle + "</td>");
        $("#text3").append("</tr>");

        $("#text3").append("<tr>");
        $("#text3").append("<td colspan='2'>발생 위치: " + obj.location + "</td>");
        $("#text3").append("</tr>");
    }
}

function setAccMarkers(info) {
    if (info == null || info[0] == null || info.length == 0) {
        checkClickAccMarker=false;
        clearAccMarker();
        return;
    }
    for (var j = 0; j < accidentMarkers.length; j++) {
        var ch = true;
        for (var i = 0; i < info.length; i++) {
            var xValue = Math.abs(info[i].x - accidentMarkers[j].position.lng());
            var yValue = Math.abs(info[i].y - accidentMarkers[j].position.lat());
            if (xValue <= 0.0005 && yValue <= 0.0005) {
                info[i].x = -1;
                info[i].y = -1;
                ch = false;
                break;
            }
        }
        if (ch) {
            accidentMarkers[j].setMap(null);
        }

    }

    for (var i = 0; i < info.length; i++) {
        if (info[i].x != -1) {
            var newMarker = new naver.maps.Marker({
                position: new naver.maps.LatLng(info[i].y, info[i].x),
                map: map,
                icon: {
                    url: './img/accident.png',
                }
            });
            naver.maps.Event.addListener(newMarker, 'click', getClickAccMarker(info[i], i));

            accidentMarkers.push(newMarker);
        }
    }

}
function setAccMarkers2(info) {
    if (info == null || info[0] == null || info.length == 0) {
        checkClickAccMarker2=false;
        clearAccMarker2();
        if(!checkAcc) initAccText();
        return;
    }
    for (var j = 0; j < accidentMarkers2.length; j++) {
        var ch = true;
        for (var i = 0; i < info.length; i++) {
            var xValue = Math.abs(info[i].x - accidentMarkers2[j].position.lng());
            var yValue = Math.abs(info[i].y - accidentMarkers2[j].position.lat());
            if (xValue <= 0.0005 && yValue <= 0.0005) {
                info[i].x = -1;
                info[i].y = -1;
                ch = false;
                break;
            }
        }
        if (ch) {
            accidentMarkers2[j].setMap(null);
        }

    }

    for (var i = 0; i < info.length; i++) {
        if (info[i].x != -1) {
            var newMarker = new naver.maps.Marker({
                position: new naver.maps.LatLng(info[i].y, info[i].x),
                map: map,
                icon: {
                    url: './img/accident.png',
                }
            });
            naver.maps.Event.addListener(newMarker, 'click', getClickAccMarker2(info[i], i));
            accidentMarkers2.push(newMarker);
        }
    }

}

function setEmerMarkers(info) {
    if (info == null || info[0] == null || info.length == 0) {
        checkClickEmerMarker=false;
        clearEmerMarker();
        return;
    }
    for (var j = 0; j < emergencyMarkers.length; j++) {
        var ch = true;
        for (var i = 0; i < info.length; i++) {
            var xValue = Math.abs(info[i].x - emergencyMarkers[j].position.lng());
            var yValue = Math.abs(info[i].y - emergencyMarkers[j].position.lat());
            if (xValue <= 0.0005 && yValue <= 0.0005) {
                info[i].x = -1;
                info[i].y = -1;
                ch = false;
                break;
            }
        }
        if (ch) {
            emergencyMarkers[j].setMap(null);
        }
    }

    console.log("emer Marker length: " + emergencyMarkers.length);
    console.log("emer info length: " + info.length);
    for (var i = 0; i < info.length; i++) {
        if (info[i].x != -1) {
            var newMarker = new naver.maps.Marker({
                position: new naver.maps.LatLng(info[i].y, info[i].x),
                map: map,
                icon: {
                    url: './img/emergency.png',
                }
            });
            naver.maps.Event.addListener(newMarker, 'click', getClickEmerMarker(info[i], i));
            emergencyMarkers.push(newMarker);
        }
    }
}

function setGongMarkers(info) {
    if (info == null || info[0] == null || info.length == 0) {
        checkClickGongMarker=false;
        clearGongMarker();
        return;
    }
    console.log("set marker >> gongsaMarkers length:" + gongsaMarkers.length);
    console.log("set marker >> info length: " + info.length);

    for (var j = 0; j < gongsaMarkers.length; j++) {
        var ch = true;
        for (var i = 0; i < info.length; i++) {
            var xValue = Math.abs(info[i].x - gongsaMarkers[j].position.lng());
            var yValue = Math.abs(info[i].y - gongsaMarkers[j].position.lat());
            if (xValue <= 0.0005 && yValue <= 0.0005) {
                info[i].x = -1;
                info[i].y = -1;
                ch = false;
                break;
            }
        }
        if (ch) {
            emergencyMarkers[j].setMap(null);
        }
    }

    for (var i = 0; i < info.length; i++) {
        if (info[i].x != -1) {
            var newMarker = new naver.maps.Marker({
                position: new naver.maps.LatLng(info[i].y, info[i].x),
                map: map,
                icon: {
                    url: './img/gongsa.png',
                }
            });
            naver.maps.Event.addListener(newMarker, 'click', getClickGongsaMarker(info[i], i));
            gongsaMarkers.push(newMarker);
        }
    }
}

function clearGongMarker() {
    if (gongsaMarkers == null) return;
    initGongText();
    console.log("clear marker >> gongsaMarkers length:" + gongsaMarkers.length);
    for (var i = 0; i < gongsaMarkers.length; i++) {
        gongsaMarkers[i].setMap(null);
    }
    gongsaMarkers = [];
}

function clearEmerMarker() {
    if (emergencyMarkers == null) return;
    initEmerText();
    console.log("clear marker >> emergencyMarkers length:" + emergencyMarkers.length);
    for (var i = 0; i < emergencyMarkers.length; i++) {
        emergencyMarkers[i].setMap(null);
    }
    emergencyMarkers = [];
}

function clearAccMarker() {
    if (accidentMarkers == null) return;
    console.log("clear marker >> AccMarkers length:" + accidentMarkers.length);
    for (var i = 0; i < accidentMarkers.length; i++) {
        accidentMarkers[i].setMap(null);
    }
    accidentMarkers = [];
}
function clearAccMarker2() {
    if (accidentMarkers2 == null) return;
    console.log("clear marker >> AccMarkers2 length:" + accidentMarkers2.length);
    for (var i = 0; i < accidentMarkers2.length; i++) {
        accidentMarkers2[i].setMap(null);
    }
    accidentMarkers2 = [];
}

function fixDestination() {
    destination.setOptions({
        draggable: false
    });
    nowDriving = true;
}

function setStartMarker(x, y) {
    startX = x;
    startY = y;
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
        center: new naver.maps.LatLng(startX, startY),
        zoom: 8
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
        curCircle= new naver.maps.Circle({
            map: map,
            center: new naver.maps.LatLng(x, y),
            radius: 1000,
            fillColor: 'white',
            fillOpacity: 0.2,
            strokeColor: "blue",
            strokeStyle:"longdash"
        });
    } else {
        currMarker.setPosition(new naver.maps.LatLng(x, y));
        curCircle.setCenter(new naver.maps.LatLng(x, y));
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
    if (currentP == 0) {
        setStartMarker(cX, cY);
        viewFixed = true;
    }
    if (currentP == 1)
        viewFixed = true;

    setCarMarker(cX, cY);
    currentP++;
    changeStep(2);
}
