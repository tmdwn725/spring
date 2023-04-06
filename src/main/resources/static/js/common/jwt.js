// Add JWT token to local storage
$(document).ready(function(){
    var token = localStorage.getItem('token')
    if (token != ''){
        localStorage.setItem('jwt', 'your_jwt_token_here');
    }


    // Add JWT to AJAX request headers
    /**/

    $(document).ajaxStart(function() {
        // Show a loading spinner
        alert("ajax 시작");
    });

    $(document).ajaxSend(function(event, xhr, options) {
        alert("ajax 전송");
        var token = localStorage.getItem('token');
        if (token) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        }
    });
    $(document).ajaxStop(function(event, xhr, options) {
        // Hide the loading spinner
        alert("ajax 끝");
    });
});