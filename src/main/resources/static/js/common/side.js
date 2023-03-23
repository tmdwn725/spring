$(function(){
    var duration = 300;
    var $sidebar = $('.sidebar');

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
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}