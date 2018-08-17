var stompClient = null;
var checkAcc=false;

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
                map.setZoom(8);
            }
            if (jsondata.type == "marker_info") {
                changeStep(1);
                checkAcc=false;
                parse_incident_Data(jsondata.incident);
                parse_ext_Data(jsondata.ext_information);
                parse_close_road(jsondata.close_data);
                move_mainCursor(jsondata);
            }
            if (jsondata.type == "info") {
                parse_total_road(jsondata.total_data);
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
    var x1 = startX;
    var y1 = startY;
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

function parse_close_road(data) {

    if (data == null || data[0] == null) {
        return;
    }

    $("#text5").empty();
    $("#text5").append("<tr>");
    $("#text5").append("<th> <FONT SIZE=\"1\">인접 도로 정보 (" + data.length + ")</FONT></th>");
    $("#text5").append("<th> <FONT SIZE=\"1\">통행 속도</br>(소요시간)</FONT></th>");
    $("#text5").append("</tr>");

    console.log("인접 도로 갯수 : " + data.length);
    for (var i = 0; i < data.length; i++) {
        var obj = data[i];
        if (obj.roadNm != null) {
            $("#text5").append("<tr>");
            $("#text5").append("<td> <FONT SIZE=\"1\">" + obj.roadNm + "</FONT>");
            switch (obj.sectionInfoCd) {
                case "01":
                    $("#text5").append("<FONT COLOR='green' SIZE=\"1\">- 원활</FONT>");
                    break;
                case "02":
                    $("#text5").append("<FONT COLOR='yellow' SIZE=\"1\">- 서행</FONT>");
                    break;
                case "03":
                    $("#text5").append("<FONT COLOR='red' SIZE=\"1\">- 혼잡</FONT>");
                    break;
            }
            $("#text5").append("</td>");
            $("#text5").append("</tr>");

            $("#text5").append("<tr>");
            $("#text5").append("<td> <FONT SIZE=\"1\">" + "(" + obj.sectionNm + ")" + "</FONT></td>");
            $("#text5").append("<td> <FONT SIZE=\"1\"> " + obj.avg_speed + " (" + obj.travel_time + ")" + "</FONT></td>");
            $("#text5").append("</tr>");

            /*      $("#text5").append("<tr>");
                  $("#text5").append("<td> <FONT SIZE=\"1\">" + obj.roadNm + "</FONT>");
                  switch (obj.sectionInfoCd) {
                      case "01":
                          $("#text5").append("<FONT COLOR='green' SIZE=\"1\">- 원활</FONT>");
                          break;
                      case "02":
                          $("#text5").append("<FONT COLOR='yellow' SIZE=\"1\">- 서행</FONT>");
                          break;
                      case "03":
                          $("#text5").append("<FONT COLOR='red' SIZE=\"1\">- 혼잡</FONT>");
                          break;
                  }
                  $("#text5").append("</td>");
                  $("#text5").append("</tr>");

                  $("#text5").append("<tr>");
                  $("#text5").append("<td> <FONT SIZE=\"1\">" + "(" + obj.sectionNm + ")" + "</FONT></td>");
                  $("#text5").append("<td> <FONT SIZE=\"1\"> " + obj.linkSpeed + " (" + obj.linkTime + ")" + "</FONT></td>");
                  $("#text5").append("</tr>");
      */
            $("#text5").append("<tr>");
            $("#text5").append("</br>");
            $("#text5").append("</tr>");
        }
    }
}

function parse_total_road(data) {

    if (data == null || data[0] == null) {
        return;
    }

    $("#text4").empty();
    $("#text4").append("<tr>");
    $("#text4").append("<th> <FONT SIZE=\"1\">전체 도로 정보 (" + data.length + ")</FONT></th>");

    $("#text4").append("<th> <FONT SIZE=\"1\">통행 속도</br>(소요시간)</FONT></th>");
    $("#text4").append("</tr>");

    for (var i = 0; i < data.length; i++) {
        var obj = data[i];
        if (obj.roadNm != null) {
            $("#text4").append("<tr>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + obj.roadNm + "</FONT>");
            switch (obj.sectionInfoCd) {
                case "01":
                    $("#text4").append("<FONT COLOR='green' SIZE=\"1\">- 원활</FONT>");
                    break;
                case "02":
                    $("#text4").append("<FONT COLOR='yellow' SIZE=\"1\">- 서행</FONT>");
                    break;
                case "03":
                    $("#text4").append("<FONT COLOR='red' SIZE=\"1\">- 혼잡</FONT>");
                    break;
            }
            $("#text4").append("</td>");
            $("#text4").append("</tr>");

            $("#text4").append("<tr>");
            $("#text4").append("<td> <FONT SIZE=\"1\">" + "(" + obj.sectionNm + ")" + "</FONT></td>");
            $("#text4").append("<td> <FONT SIZE=\"1\"> " + obj.avg_speed + " (" + obj.travel_time + ")" + "</FONT></td>");
            $("#text4").append("</tr>");

            /*
                        $("#text4").append("<tr>");
                        $("#text4").append("<td> <FONT SIZE=\"1\">" + obj.roadNm + "</FONT>");
                        switch (obj.sectionInfoCd) {
                            case "01":
                                $("#text4").append("<FONT COLOR='green' SIZE=\"1\">- 원활</FONT>");
                                break;
                            case "02":
                                $("#text4").append("<FONT COLOR='yellow' SIZE=\"1\">- 서행</FONT>");
                                break;
                            case "03":
                                $("#text4").append("<FONT COLOR='red' SIZE=\"1\">- 혼잡</FONT>");
                                break;
                        }
                        $("#text4").append("</td>");
                        $("#text4").append("</tr>");

                        $("#text4").append("<tr>");
                        $("#text4").append("<td> <FONT SIZE=\"1\">" + "(" + obj.sectionNm + ")" + "</FONT></td>");
                        $("#text4").append("<td> <FONT SIZE=\"1\"> " + obj.linkSpeed + " (" + obj.linkTime + ")" + "</FONT></td>");
                        $("#text4").append("</tr>");
            */
            $("#text4").append("<tr>");
            $("#text4").append("</br>");
            $("#text4").append("</tr>");
        }
    }
}

function parse_ext_Data(data) {
    var emerInfo = [];
    var accInfo = [];

    var cA = false;
    var cE = false;

    if (!checkClickEmerMarker) {
        initEmerText();
    }

    if (data != null && data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            if (obj.type == "sudden case") {
                if (!checkClickEmerMarker) {
                    if (cE) {
                        $("#text1").append("<tr>");
                        $("#text1").append("</br>");
                        $("#text1").append("</tr>");
                    }
                    cE = true;
                    $("#text1").append("<tr>");
                    $("#text1").append("<td>" + obj.info + "</td>");
                    $("#text1").append("</tr>");
                }
                emerInfo.push(obj);

            } else if (obj.type == "accident") {
                checkAcc=true;
                if (!checkClickAccMarker && !checkClickAccMarker2) {
                    if (cA) {
                        $("#text2").append("<tr>");
                        $("#text2").append("</br>");
                        $("#text2").append("</tr>");
                    }
                    cA = true;

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
                accInfo.push(obj);
            }
        }
    }
    setAccMarkers2(accInfo);
    setEmerMarkers(emerInfo);

}

function parse_incident_Data(data) {
    var accInfo = [];
    var gongInfo = [];

    var cA = false;
    var cG = false;

    if (!checkClickAccMarker && !checkClickAccMarker2) {
        initAccText();
    }
    if (!checkClickGongMarker) {
        initGongText();
    }
    if (data != null && data[0] != null) {
        for (var i = 0; i < data.length; i++) {
            var obj = data[i];
            if (obj.incidentcode == 1) {
                checkAcc=true;
                if (!checkClickAccMarker && !checkClickAccMarker2) {
                    if (cA) {
                        $("#text2").append("<tr>");
                        $("#text2").append("</br>");
                        $("#text2").append("</tr>");
                    }
                    cA = true;

                    $("#text2").append("<tr>");
                    $("#text2").append("<td>" + "< API >" + obj.incidenttitle + "</td>");
                    $("#text2").append("</tr>");

                    $("#text2").append("<tr>");
                    $("#text2").append("<td>발생 위치: " + obj.location + "</td>");
                    $("#text2").append("</tr>");

                    $("#text2").append("<tr>");
                    $("#text2").append("<td>인근 지역 교통: " + obj.trafficgrade + "</td>");
                    $("#text2").append("</tr>");

                    $("#text2").append("<tr>");
                    $("#text2").append("<td>추가 사고 위험: " + obj.troublegrade + "</td>");
                    $("#text2").append("</tr>");
                }
                accInfo.push(obj);
            } else if (obj.incidentcode == 2) {
                if (!checkClickGongMarker) {
                    if (cG) {
                        $("#text3").append("<tr>");
                        $("#text3").append("</br>");
                        $("#text3").append("</tr>");
                    }
                    cG = true;

                    $("#text3").append("<tr>");
                    $("#text3").append("<td colspan='2'>" + obj.incidenttitle + "</td>");
                    $("#text3").append("</tr>");

                    $("#text3").append("<tr>");
                    $("#text3").append("<td colspan='2'>발생 위치: " + obj.location + "</td>");
                    $("#text3").append("</tr>");

                    $("#text3").append("<tr>");
                    $("#text3").append("<td>인근 지역 교통: " + obj.trafficgrade + "</td>");
                    $("#text3").append("</tr>");

                    $("#text3").append("<tr>");
                    $("#text3").append("<td>사고 위험: " + obj.troublegrade + "</td>");
                    $("#text3").append("</tr>");
                }
                gongInfo.push(obj);
            }
        }
    }
    setAccMarkers(accInfo);
    setGongMarkers(gongInfo);

}

function initEmerText() {
    $("#text1").empty();
    $("#text1").append("<tr>");
    $("#text1").append("<th>돌발 상황 정보</th>");
    $("#text1").append("</tr>");
}

function initAccText() {
    $("#text2").empty();
    $("#text2").append("<tr>");
    $("#text2").append("<th>사고 정보</th>");
    $("#text2").append("</tr>");

}

function initGongText() {
    $("#text3").empty();
    $("#text3").append("<tr>");
    $("#text3").append("<th>공사 정보</th>");
    $("#text3").append("</tr>");
}
