<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <title>登录</title>
    <link rel="shortcut icon" href="../frontResources/img/favicon.ico">
    <!-- global stylesheets -->
    <link href="https://fonts.googleapis.com/css?family=Roboto+Condensed" rel="stylesheet">
    <link rel="stylesheet" href="../frontResources/css/bootstrap.min.css">
    <link rel="stylesheet" href="../frontResources/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../frontResources/css/font-icon-style.css">
    <link rel="stylesheet" href="../frontResources/css/style.default.css" id="theme-stylesheet">

    <!-- Core stylesheets -->
    <link rel="stylesheet" href="../frontResources/css/pages/login.css">
</head>

<body>

<!--====================================================
                        PAGE CONTENT
======================================================-->
<section class="hero-area">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-12 ">
                <div class="contact-h-cont">
                    <h3 class="text-center"><img src="../frontResources/img/logo.png" class="img-fluid" alt=""></h3><br>
                    <form>
                        <div class="form-group">
                            <label for="username">账号</label>
                            <input type="text" class="form-control" id="username" placeholder="输入手机号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">密码</label>
                            <input class="form-control" type="password" value="" id="password" placeholder="输入密码">
                        </div>
                        <button id="login" class="btn btn-general btn-blue" role="button"><i fa fa-right-arrow></i>登录
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<!--Global Javascript -->
<script src="../frontResources/js/jquery.min.js"></script>
<script src="../frontResources/js/tether.min.js"></script>
<script src="../frontResources/js/bootstrap.min.js"></script>
<script>
    $("#login").click(function () {
        var phone = $("#username").val().trim();
        var password = $("#password").val().trim();
        if (phone == null || phone == "") {
            alert("请输入账号");
            return;
        }
        if (password == null || password == "") {
            alert("请输入密码");
            return;
        }
        $.ajax({
            url: "http://localhost:8080/login",
            type: "POST",
            data: {
                "phone": phone,
                "password": password
            },
            success: function (rsp) {
                if (rsp.code == 0) {
                    window.location.href = "http://localhost:8080/index";
                } else {
                    alert(rsp.msg);
                }
            }
        });
    });
</script>
</body>

</html>