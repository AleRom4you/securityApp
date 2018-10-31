$('#submit').click(function () {
    post();
});

function post() {
    // get the form values
    var login = $('#login').val();
    var password = $('#password').val();
    var repassword = $('#re-password').val();
    var name = $('#name').val();

    $.ajax({
        type: "POST",
        url: "/signup",
        data: {
            login       :login,
            password    :password,
            repassword  :repassword,
            name        :name
        },
        success: function(response){
            // we have the response
            if(response.status == "SUCCESS"){
                alert(response.result);
                // userInfo = "<ol>";
                // for(i =0 ; i < response.result.length ; i++){
                //     userInfo += "<br><li><b>Name</b> : " + response.result[i].name +
                //         ";<b> Education</b> : " + response.result[i].education;
                // }
                // userInfo += "</ol>";
                // $('#info').html("User has been added to the list successfully. " + userInfo);
                // $('#name').val('');
                // $('#education').val('');
                // $('#error').hide('slow');
                // $('#info').show('slow');
            }else{
                errorInfo = "";
                for(i =0 ; i < response.result.length ; i++){
                    errorInfo += "<br>" + (i + 1) +". " + response.result[i].code;
                }
                $('#error').html("Please correct following errors: " + errorInfo);
                $('#info').hide('slow');
                $('#error').show('slow');
            }
        },
        error: function(e){
            alert('Error: ' + e);
        }
    });
}
