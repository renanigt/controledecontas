<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adicionar usuário !</title>
</head>
<body>

	<span>
		<c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach>
	</span>

	<form action="<c:url value="/usuario/novo/salvar" />" name="form_usuario" method="post">
		<div>
			<label>Nome:</label>
			<input type="text" name="usuario.nome" id="nome" size="60" maxlength="60" />
		</div>
		<div>
			<label>Username:</label>
			<input type="text" name="usuario.username" id="username" size="15" maxlength="15" />
		</div>
		<div>
			<label>Password:</label>
			<input type="password" name="usuario.password" id="password" size="10" maxlength="10" />
		</div>
		<input type="submit" value="Salvar" />
	</form>

</body>
</html>