<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta name="description" content="Xenon Boostrap Admin Panel"/>
  <meta name="author" content=""/>

  <title>Xenon - Login</title>

  <%--<link rel="stylesheet" href="http://fonts.useso.com/css?family=Arimo:400,700,400italic">--%>
  <link rel="stylesheet" href="/assets/css/fonts/linecons/css/linecons.css">
  <link rel="stylesheet" href="/assets/css/fonts/fontawesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="/assets/css/bootstrap.css">
  <link rel="stylesheet" href="/assets/css/xenon-core.css">
  <link rel="stylesheet" href="/assets/css/xenon-forms.css">
  <link rel="stylesheet" href="/assets/css/xenon-components.css">
  <link rel="stylesheet" href="/assets/css/xenon-skins.css">
  <link rel="stylesheet" href="/assets/css/custom.css">

  <script src="/assets/js/jquery-1.11.1.min.js"></script>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="/assets/js/html5shiv.min.js"></script>
  <script src="/assets/js/respond.min.js"></script>
  <![endif]-->
</head>
<body class="page-body login-page">
<div class="login-container">
  <div class="row">
    <div class="col-sm-6">
      <script type="text/javascript">
        jQuery(document).ready(function ($) {
          // Reveal Login form
          setTimeout(function () {
            $(".fade-in-effect").addClass('in');
          }, 3000);

          // Validation and Ajax action
          $("form#login").validate({
            rules: {
              code: {
                required: true
              },

              password: {
                required: true
              }
            },
            messages: {
              code: {
                required: 'Please enter your username.'
              },
              password: {
                required: 'Please enter your password.'
              }
            },
            // Form Processing via AJAX
            submitHandler: function (form) {
              show_loading_bar(70); // Fill progress bar to 70% (just a given value)
              var opts = {
                "closeButton": true,
                "debug": false,
                "positionClass": "toast-top-full-width",
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
              };

              $.ajax({
                url: "/login/login.jss",
                method: 'POST',
                /*dataType: 'json',*/
                data: {
                  do_login: true,
                  code: $(form).find('#code').val(),
                  password: $(form).find('#password').val(),
                },
                success: function (result) {
                  show_loading_bar({
                    delay: .5,
                    pct: 100,
                    finish: function () {
                      // Redirect after successful login page (when progress bar reaches 100%)
                      if (result.result) {
                        window.location.href = "/layout/main.cc?first=" + result.data;
                      } else {
                        toastr.error(result.message);
                        $password.select();
                      }
                    }
                  });

                },
                error: function () {
                  toastr.error("出错啦！");
                  $password.select();
                }
              });
            }
          });
          // Set Form focus
          $("form#login .form-group:has(.form-control):first .form-control").focus();
        });
      </script>
      <!-- Errors container -->
      <div class="errors-container">
      </div>
      <!-- Add class "fade-in-effect" for login form effect -->
      <form method="post" role="form" id="login" class="login-form fade-in-effect">
        <div class="login-header">
          <a href="dashboard-1.html" class="logo">
            <img src="/assets/images/logo-white.png" alt="" width="80"/>
            <span>log in</span>
          </a>

          <p>Dear user, log in to access the admin area!</p>
        </div>


        <div class="form-group">
          <label class="control-label" for="code"></label>
          <input type="text" class="form-control input-dark" name="code" id="code"
                 autocomplete="on" placeholder="账号"/>
        </div>

        <div class="form-group">
          <label class="control-label" for="password"></label>
          <input type="password" class="form-control input-dark" name="password" id="password"
                 autocomplete="off" placeholder="密码"/>
        </div>

        <div class="form-group">
          <button type="submit" class="btn btn-dark  btn-block text-left">
            <i class="fa-lock"></i>
            登录
          </button>
        </div>

        <div class="login-footer">
          <a href="#">Forgot your password?</a>

          <div class="info-links">
            <a href="#">ToS</a> -
            <a href="#">Privacy Policy</a>
          </div>

        </div>

      </form>

      <!-- External login -->
      <div class="external-login">
        <a href="#" class="facebook">
          <i class="fa-facebook"></i>
          Facebook Login
        </a>

        <!--
        <a href="#" class="twitter">
            <i class="fa-twitter"></i>
            Login with Twitter
        </a>

        <a href="#" class="gplus">
            <i class="fa-google-plus"></i>
            Login with Google Plus
        </a>
         -->
      </div>

    </div>

  </div>

</div>


<!-- Bottom Scripts -->
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/TweenMax.min.js"></script>
<script src="/assets/js/resizeable.js"></script>
<script src="/assets/js/joinable.js"></script>
<script src="/assets/js/xenon-api.js"></script>
<script src="/assets/js/xenon-toggles.js"></script>
<script src="/assets/js/jquery-validate/jquery.validate.min.js"></script>
<script src="/assets/js/toastr/toastr.min.js"></script>


<!-- JavaScripts initializations and stuff -->
<script src="/assets/js/xenon-custom.js"></script>

</body>
</html>

