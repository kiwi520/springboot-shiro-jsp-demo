<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
        <h1>用户登录</h1>
        <form action="${pageContext.request.contextPath}/user/login" method="post">
           用户名： <input type="text" name="username" id="username"><br>
            密码：<input type="password" name="password" id="password"><br>
            请输入验证码：<input type="text" name="code"> <img id = "imageMask" src="${pageContext.request.contextPath}/user/getImage" alt=""><br>
            <a href = "#" style = "font-size: 13px;margin-left: 5px;" onclick = "myReload()">看不清换一张</a>
            <input type="submit" value="登陆">
        </form>
        <script type="text/javascript">
            //用于刷新验证码
            function myReload(){
                document.getElementById("imageMask").src=document.getElementById("imageMask").src+"?"+Math.random();
            }
        </script>
</body>
</html>