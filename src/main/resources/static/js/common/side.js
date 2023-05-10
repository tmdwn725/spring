$(function(){
    var duration = 300;
    var $sidebar = $('.sidebar');
    // Authorization 헤더에서 JWT 토큰 추출
    const authHeader = response.headers.get('Authorization');
    const token = authHeader.split(' ')[1];

    // JWT 토큰을 로컬 스토리지에 저장
    localStorage.setItem('jwtToken', token);
    var $sidebarButton = $sidebar.find('.sidebar-btn').on('click', function(){
        $sidebar.toggleClass('close');
        if($sidebar.hasClass('close')){
            $sidebar.stop(true).animate({left: '-270px'}, duration, 'easeInBack');
            $sidebar.fadeIn();
            $sidebarButton.find('span').text('>>');
        }else{
            $sidebar.stop(true).animate({left: '0px'}, duration, 'easeOutBack');
            $sidebarButton.find('span').text('<<');
        };
    });
});

function openModal() {
    var modal = document.getElementById("applyClubModal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("applyClubModal");
    modal.style.display = "none";
}

function applicaClub(){
    var modal = document.getElementById("applyClubModal");
    var clubName = $("#club-name").val();
    var clubIntro = $("#club-intro").val();
    var clubCategory = $("#club-category").val();
    var clubRoom = $("#club-room-nm").val();

    // Ajax를 사용하여 컨트롤러로 데이터 전달
    $.ajax({
        type: "POST",
        url: "/club/applyClub",
        data: {
            clubNm: clubName,
            introduce: clubIntro,
            clubClsCd: clubCategory,
            roomNm: clubRoom
        },
        timeout: 5000, // 타임아웃 시간 설정 (5초)
        success: function (result) {
            // 성공적으로 데이터를 전달한 경우에 대한 처리
           modal.style.display = "none";
           window.location.replace(window.location.href); // 현재 페이지를 리다이렉트합니다.
        },
        error: function (xhr, status, error) {
            // 데이터 전달 실패에 대한 처리
            console.error(error);
        }
    });
}