$(document).ready(function() {
  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listYear'
    },
    events: function(info, successCallback, failureCallback) {
      // 디비 조회를 위한 데이터 설정
      var stDt = moment(info.start).format('YYYY-MM-DD');
      var edDt = moment(info.end).format('YYYY-MM-DD');
      var clubSeq = $("#clubSeq").val();
      // Ajax를 사용하여 디비 조회
      $.ajax({
        url: '/club/getClubSchedule', // 적절한 서버 경로 설정
        data: {
          clubSeq : clubSeq,
          stDt: stDt,
          edDt: edDt
        },
        success: function(response) {
          // 받아온 데이터를 캘린더 이벤트로 변환하여 캘린더에 추가
          var events = [];
          response.scheduleList.forEach(function(eventData) {
            var event = {
              title: eventData.title,
              start: eventData.scheduleDate,
              end: eventData.scheduleDate,
              description: eventData.content
            };
            events.push(event);
          });
          successCallback(events);
        },
        error: function(xhr, status, error) {
          console.error(error);
          failureCallback(error);
        }
      });
    },
    selectable: true,
    select: function(info) {
      loadEvents(info.start);
    },
    loading: function(bool) {
      $('#loading').toggle(bool);
    }
  });

  calendar.render();
});


function closeScheduleModal() {
    var modal = document.getElementById("clubScheModal");
    modal.style.display = "none";
}

function loadEvents(date) {
  // 선택된 날짜에서 년, 월, 일을 추출
  var year = date.getFullYear();
  var month = ("0" + (date.getMonth() + 1)).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);
  var clubSeq = $("#clubSeq").val();

  // 날짜 문자열 생성 (YYYY-MM-DD)
  var dateString = year + "-" + month + "-" + day;

  // 서버에 일정 조회 요청
  $.ajax({
    type: "GET",
    url: "/club/getClubSchedule",
    data: {
        clubSeq : clubSeq,
        stDt: dateString,
        edDt: dateString
    },
    dataType: "json",
    success: function(data) {
        $("#schedule-date").html(data.date);
        $("#date").val(data.date);
        str = ""
        data.scheduleList.forEach(function(list) {
            str += "<div class='activity-item d-flex'>"
            str += "    <div class='activite-label'><input type='checkbox' name='scheduleSeq' style='width:15px; margin:2px;' value='" + list.scheduleSeq + "' onclick='scheduleCheck(this)'></div>"
            str += "    <div class='activite-label'>" + list.startTime.substring(0,5) + " ~ " + list.endTime.substring(0,5) +  "</div>"
            str += "    <i class='bi bi-circle-fill activity-badge text-success align-self-start'/>"
            str += "    <div class='activity-content' style='width:120px;'>" + list.title + "</div>"
            str += "    <i class='bi bi-circle-fill activity-badge text-primary align-self-start'/>"
            str += "    <div class='activity-content' style='width:120px;'> 장소 : " + list.place + "</div>"
            str += "</div>"
        });
        $("#title").val("");
        $("#place").val("");
        $("#content").val("");
        $("#start-time").val("00:00");
        $("#end-time").val("23:59");
        $("#event-details").html(str); // 리스트를 모달창에 뿌리기*/
        $("#schedule-modal").modal("show"); // 모달창 열기
    },
  error: function(xhr, status, error) {
    alert("데이터를 가져오는데 실패했습니다.");
  }
});
}

// 일정 추가
function addSchedule(){
    var title = $("#title").val();
    var place = $("#place").val();
    var stTime = $("#start-time").val();
    var edTime = $("#end-time").val();
    var content = $("#content").val();
    var scheduleDate = $("#date").val();
    var clubSeq = $("#clubSeq").val();

    // Ajax를 사용하여 컨트롤러로 데이터 전달
    $.ajax({
        type: "POST",
        url: "/club/addSchedule",
        data: {
            title : title,
            place : place,
            stTime: stTime,
            edTime: edTime,
            content: content,
            scheduleDate : scheduleDate,
            clubSeq : clubSeq
        },
        timeout: 5000, // 타임아웃 시간 설정 (5초)
        success: function (result) {
            $("#title").val("");
            $("#place").val("");
            $("#content").val("");
            $("#start-time").val("00:00");
            $("#end-time").val("23:59");
            // 성공적으로 데이터를 전달한 경우에 대한 처리
           window.location.replace(window.location.href); // 현재 페이지를 리다이렉트합니다.
        },
        error: function (xhr, status, error) {
            // 데이터 전달 실패에 대한 처리
            console.error(error);
        }
    });
}

function closeScheduleModal(){
    $("#schedule-modal").modal("hide");
}

// 체크박스 체크
function scheduleCheck(obj){
    $("input:checked[name=scheduleSeq]").each(function() {
        if (obj.value != $(this).val()) {
            $(this).attr("checked", false); // uncheck all checkboxes
        }
   });

   $.ajax({
           type: "GET",
           url: "/club/setSchedule",
           data: {
               scheduleSeq : obj.value
           },
           dataType: "json",
           success: function (result) {
               $("#title").val(result.schedule.title);
               $("#place").val(result.schedule.place);
               $("#content").val(result.schedule.content);
               $("#start-time").val(result.schedule.startTime.substring(0,5));
               $("#end-Time").val(result.schedule.endTime.substring(0,5));
           },
           error: function (xhr, status, error) {
               // 데이터 전달 실패에 대한 처리
               console.error(error);
           }
       });

}