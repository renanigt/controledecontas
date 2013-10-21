<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Adicionar usuário !</title>
</head>
<body>
	<div class="container container-page">
	
		<fieldset>
			<legend>Adicionar Usuário</legend>
			
			<c:if test="${not empty errors}">
				<div id="erro" class="alert alert-error">
					<button type="button" class="close">&times;</button>
					<span id="mensagemErro"><c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach></span>
				</div>
			</c:if>

			<form class="form-horizontal" action="<c:url value="/usuario/novo/salvar" />" name="form_usuario" method="post">
				<div class="control-group">
					<label class="control-label">Nome:</label>
					<div class="controls">
						<input type="text" name="usuario.nome" id="nome" size="60" maxlength="60"
							value="${usuario.nome}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Login:</label>
					<div class="controls">
						<input type="text" name="usuario.login" id="login" size="15" maxlength="15"
							value="${usuario.login}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Senha:</label>
					<div class="controls">
						<input type="password" name="usuario.senha" id="senha" size="10" maxlength="10" />
					</div>
				</div>
				<div class="controls">
					<input class="btn btn-primary" type="submit" value="Salvar" />
				</div>
			</form>
		</fieldset>
	</div>
	
	<script src="<c:url value="/js/usuario/usuario.js"/>"></script>
</body>
</html>