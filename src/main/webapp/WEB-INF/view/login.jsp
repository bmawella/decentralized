<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> Decentralized login </title>
    <style>
        Body {
            font-family: Calibri, Helvetica, sans-serif;
            background-color: FFFFFFFF;
        }

        button {
            background-color: #4c74af;
            width: 25%;
            color: #ffffff;
            padding: 15px;
            margin: 10px 0px;
            border: none;
            cursor: pointer;
            horiz-align: center;
        }

        input[type=text], input[type=password] {
            width: 100%;
            margin: 8px 0;
            padding: 12px 20px;
            display: inline-block;
            border: 2px solid #1f2980;
            box-sizing: border-box;
        }

        button:hover {
            opacity: 0.7;
        }

        .button-container {
            text-align: center;
        }

        .container {
            padding: 25px;
            width: 40%;
            background-color: lightblue;
        }

        .form-container {
            width: 100%;
            display: flex;
        }

        .empty-container {
            width: 30%;
        }
    </style>
</head>
<body>
<center><h1> Decentralized Demo</h1></center>
<form action="/login-action" method="post" class="form-container">
    <div class="empty-container"></div>
    <div class="container">
        <label>Username : </label>
        <input type="text" placeholder="Enter Username" name="username" required>
        <label>Password : </label>
        <input type="password" placeholder="Enter Password" name="password" required>
        <div style="text-align: center;color: red">${message}</div>
        <div class="button-container">
            <button type="submit">Login</button>
        </div>

    </div>
    <div class="empty-container"></div>
</form>
</body>
</html>