addStudent.jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>

<head>
    <title>注册学生</title>
    <base href="<%=basePath%>">
</head>
<body>
<form action="order/insertOrder.do" method="post">
    <table>
        <tr>
            <td>skuID:</td>
            <td><input type="text" name="sku_id"></td>
        </tr>
        <tr>
            <td>仓库ID：</td>
            <td><input type="text" name="warehouse_id"></td>
        </tr>
        <tr>
            <td>数量：</td>
            <td><input type="text" name="order_amount"></td>
        </tr>
        <tr>
            <td>创建时间：</td>
            <td><input type="text" name="order_create_time"></td>
        </tr>
        <tr>
            <td>订单状态：</td>
            <td><input type="text" name="order_state"></td>
        </tr>
        <tr>
            <td>更新时间：</td>
            <td><input type="text" name="order_update_time"></td>
        </tr>
        <tr>
            <td><input type="submit" value="创建"></td>
        </tr>
    </table>
</form>
</body>
</html>