<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=request.getContextPath() + "/"%>">
<title>首页</title>
</head>
<body>
	<form action="house/add">
		<table>
			<tr>
				<td>name</td>
				<td><input name="name"></td>
			</tr>
			<tr>
				<td>age</td>
				<td><input name="age"></td>
			</tr>
			<tr>
				<td>sname</td>
				<td><input name="sname"></td>
			</tr>
			<tr>
				<td>sscore</td>
				<td><input name="sscore"></td>
			</tr>
			<tr>
				<td>money</td>
				<td><input name="money"></td>
			</tr>
			<tr>
				<td>birthday</td>
				<td><input name="birthday"></td>
			</tr>
		</table>
		<button type="submit">sure</button>
	</form>
</body>
</html>