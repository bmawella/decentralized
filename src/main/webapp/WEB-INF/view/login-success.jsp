<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> Welcome </title>
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

        form {
            border: 3px solid #f1f1f1;
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


        .container {
            padding: 100px;
            background-color: lightblue;
            height: 40%;
            text-align: center;
        }
    </style>
</head>
<body>
<center><h1> Decentralized Demo</h1></center>
<div style="text-align: right;font-size: large"><a href="/logout?ledgerId=<%=session.getAttribute("ledgerId")%>">Logout</a></div>
<form>
    <div class="container">
        <div style="color: #1f2980; font-size: x-large">${success}</div>
    </div>
</form>
</body>
</html>