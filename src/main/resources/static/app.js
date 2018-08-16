var stompClient = null;
var checkAcc = false;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        sendinit();
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/greetings', function (greeting) {
            // showGreeting(JSON.parse(greeting.body).content);
            var jsondata = JSON.parse(greeting.body);
            if (jsondata.type == "init") {
                startX = jsondata.y;
                startY = jsondata.x;
                initMap();
                setCarMarker(startX, startY);
            }
            if (jsondata.type == "marker_info") {
                changeStep(1);
                parse_incident_Data(jsondata.incident);
                parse_ext_Data(jsondata.ext_information);
                parse_total_road(jsondata.close_data, 2);
                move_mainCursor(jsondata);
            }
            if (jsondata.type == "info") {
                parse_total_road(jsondata.total_data, 1);
                changeStep(1);
            }

        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendinit() {
    stompClient.send("/app/init", {}, "connected start");
}

function sendName() {
    var x1 = routeY[0];
    var y1 = routeX[0];
    var x2 = destination.position.lng();
    var y2 = destination.position.lat();
    var data = {
        "x1": x1,
        "y1": y1,
        "x2": x2,
        "y2": y2,
        'text': "song"
    }
    stompClient.send("/app/hello", {}, JSON.stringify(data));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        if (arrCheck) {
            sendName();
            changeStep(0);
            fixDestination();
            viewFixed = true;
        } else alert("check destination");
    });
});

function parse_total_road(data, color) {

    if (data == null || data[0] == null) {
        return;
    }

    $("#text4").empty();
    $("#text4").append("<tr>");
    if (color == 1) {
        $("#text4").append("<th> <FONT SIZE=\"1\">전체 도로 정보</FONT></th>");
    } else {
        $("#text4").append("<th> <FONT SIZE=\"1\">인접 도로 정보</FONT></th>");
    }
    $("#text4").append("<th> <FONT SIZE=\"1\">통행 속도</br>(소요시간)</FONT></th>");
    $("#text4").append("</tr>");

    for (var i = 0; i < data.length; i++) {
        var obj = data[i];
        if (obj.roadNm != null) {
            $("#text4").append("<tr>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + obj.roadNm + "</FONT></td>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + obj.avg_speed + "</FONT></td>");
            $("#text4").append("</tr>");

            $("#text4").append("<tr>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + "(" + obj.sectionNm + ")" + "</FONT></td>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + "(" + obj.travel_time + ")" + "</FONT></td>");
            $("#text4").append("</tr>");

            $("#text4").append("<tr>");
            $("#text4").append("</br>");
            $("#text4").append("</tr>");
        }
    }
}

function parse_ext_Data(data) {
    var emerX = [];
    var emerY = [];
    var emerInfo = [];

    var accX = [];
    var accY = [];
    var accInfo = [];

    var checkEmer = false;

    if (!checkClickEmerMarker) {
        $("#text1").empty();
        $("#text1").append("<tr>");
        $("#text1").append("<th>돌발 상황 정보</th>");
        $("#text1").append("<th></th>");
        $("#text1").append("</tr>");
    }

    if (data != null && data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            if (obj.type == "sudden case") {
                checkEmer = true;
                if (!checkClickEmerMarker) {
                    $("#text1").append("<tr>");
                    $("#text1").append("<td>" + obj.info + "</td>");
                    $("#text1").append("</tr>");

                    $("#text1").append("<tr>");
                    $("#text1").append("</br>");
                    $("#text1").append("</tr>");
                }
                emerX.push(obj.y);
                emerY.push(obj.x);
                emerInfo.push(obj);

            } else if (obj.type == "accident") {
                checkAcc = true;
                if (!checkClickAccMarker) {
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

                    $("#text2").append("<tr>");
                    $("#text2").append("</br>");
                    $("#text2").append("</tr>");
                }

                accX.push(obj.y);
                accY.push(obj.x);
                accInfo.push(obj);
            }
        }
        setEmerMarkers(emerX, emerY, emerInfo);
        setAccMarkers(accX, accY, accInfo, "ext");
        if (!checkEmer) {
            clearEmerMarker();
        }
    } else {
        clearEmerMarker();
    }
}


function parse_incident_Data(data) {
    var checkGong = true;

    var accX = [];
    var accY = [];
    var accInfo = [];

    var gongX = [];
    var gongY = [];
    var gongInfo = [];

    if (!checkClickAccMarker) {
        $("#text2").empty();
        $("#text2").append("<tr>");
        $("#text2").append("<th>사고 정보</th>");
        $("#text2").append("<th>사고 위치</th>");
        $("#text2").append("</tr>");
    }
    if (!checkClickGongMarker) {
        $("#text3").empty();
        $("#text3").append("<tr>");
        $("#text3").append("<th>공사 정보</th>");
        $("#text3").append("<th>공사 위치</th>");
        $("#text3").append("</tr>");
    }
    if (data != null && data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            if (obj.incidentcode == 1) {
                checkAcc = true;
                if (!checkClickAccMarker) {
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

                accX.push(obj.y);
                accY.push(obj.x);
                accInfo.push(obj);
                console.log("사고 정보 처리");


            } else if (obj.incidentcode == 2) {
                checkGong = false;
                if (!checkClickGongMarker) {

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

                gongX.push(obj.y);
                gongY.push(obj.x);
                gongInfo.push(obj);
                console.log("공사 정보 처리");

            }
        }
        if (checkAcc) {
            setAccMarkers(accX, accY, accInfo, "api");
        }
        if (checkGong) {
            clearGongMarker();
        } else {
            setGongMarkers(gongX, gongY, gongInfo);
        }
    } else {
        clearGongMarker();
        clearAccMarker();
    }

}