function closeScheduleModal() {
    var modal = document.getElementById("clubScheModal");
    modal.style.display = "none";
}

function loadEvents(date) {
  // 선택된 날짜에서 년, 월, 일을 추출
  var year = date.getFullYear();
  var month = ("0" + (date.getMonth() + 1)).slice(-2);
  var day = ("0" + date.getDate()).slice(-2);

  // 날짜 문자열 생성 (YYYY-MM-DD)
  var dateString = year + "-" + month + "-" + day;

  // 서버에 일정 조회 요청
  $.ajax({
    url: "/club/getClubSchedule/" + dateString,
    type: "GET",
    dataType: "json",
    success: function(data) {
        // 응답 데이터를 템플릿에 적용하여 리스트 만들기
        var listItems = "";
        data.forEach(function(item) {
            listItems += "<li>" + item.scheduleNm + "</li>";
        });
        $("#list").html(listItems); // 리스트를 모달창에 뿌리기
        $("#my-modal").modal("show"); // 모달창 열기
    },
  error: function(xhr, status, error) {
    alert("데이터를 가져오는데 실패했습니다.");
  }
});
}