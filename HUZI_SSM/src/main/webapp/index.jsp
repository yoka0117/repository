<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>功能入口</title>
    <base href="<%=basePath%>">
</head>
<body>
<p>ssm整合例子</p>
<table>
    <tr>
        <td><a href="addStudent.jsp"/> 注册</td>
    </tr>


</table>
</body>
</html>