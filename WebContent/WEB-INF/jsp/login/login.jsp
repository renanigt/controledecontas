<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<form action="<c:url value="/login/logar" />" name="form_usuario" method="post">
		<div>
			<label>Username:</label>
			<input type="text" name="username" id="username" size="15" maxlength="15" value="${username}" />
		</div>
		<div>
			<label>Password:</label>
			<input type="password" name="password" id="password" size="10" maxlength="10" />
		</div>
		<input type="submit" value="Logar" >
	</form>

</body>
</html>