$(function () {

    $(".form-horizontal #loginButten").on("click", function () {
        login();

    });

    //reload imagecode
    $("#reload").on("click", function () {
        var _this = $(this);
        var img = _this.find("img");
        img.attr("src", "service/sys/getImg?" + new Date().getTime());
    });

    //".form-horizontal input[type=text]"
    $(document).on("keydown", function (e) {
        if (e.keyCode == 13) {
            login();
        }
    });

    //login
    /*var login = function () {
        var userName = $(".form-horizontal #userName").val();
        var password = $(".form-horizontal #password").val();
        var code = $(".form-horizontal #code").val();
        $.post("sys/login", {userName: userName, password: password, code: code},
            function (data) {
                if (data) {
                    if (data.success) {
                        window.location.href = "index.html";
                    }
                    else {
                        $(".heading").text(data.msg);
                    }
                }
            });
    }*/
});