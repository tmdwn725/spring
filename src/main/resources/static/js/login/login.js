$(document).ready(function(){
    /*$("#login-form").submit(function(event) {
        // Prevent the form from submitting normally
        event.preventDefault();

        // Submit the form and handle the response
        $.post($(this).attr("action"), $(this).serialize(), function(data, textStatus, xhr) {
            // Save the JWT token in local storage
            const token = xhr.getResponseHeader("Authorization");
            localStorage.setItem("token", token);

            // Create a new XMLHttpRequest object
            var xhr = new XMLHttpRequest();

            // Set up the request
            xhr.open("GET", "/member/main");

            // Set the Authorization header with the token
            xhr.setRequestHeader("Authorization", "Bearer " + token);

            // Set up a handler to run when the response is received
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Handle the successful response
                        window.location.href = "/member/main"; // Move to the main page
                    } else {
                        // Handle the error
                        console.error("Failed to load main page");
                    }
                }
            };

            // Send the request
            xhr.send();

        }).fail(function(xhr, textStatus, errorThrown) {
            // Display an error message
            $("#error-message").text(xhr.responseJSON.message);
        });
    });*/
});

function login(){
    var memberId = $("#memberId").val();
    var password = $("#password").val();
    $.ajax({
        type: "POST",
        url: "/login",
        data: JSON.stringify({"memberId": memberId, "password": password}),
        contentType: "application/json",
        success: function(response) {
            // Save the JWT token in local storage
            localStorage.setItem("token", response.token);

            // Redirect to another page
            window.location.href = "/member/main";
        },
        error: function() {
            console.log("Error: authentication failed.");
        }
    });
}