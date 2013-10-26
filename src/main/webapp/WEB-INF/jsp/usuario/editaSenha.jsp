<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>Alterar senha</title>
</head>
<body>
	<div class="container container-page">
	
		<fieldset>
			<legend>Alterar Senha</legend>
			
			<c:if test="${not empty errors}">
				<div id="erro" class="alert alert-error">
					<button type="button" class="close">&times;</button>
					<span id="mensagemErro"><c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach></span>
				</div>
			</c:if>

			<form class="form-horizontal" action="<c:url value="/usuario/atualizaSenha/salvar" />" name="form_usuario" method="post">
				<input type="hidden" name="usuario.id" value="${usuario.id}">
				<input type="hidden" name="usuario.nome" value="${usuario.nome}">
				<input type="hidden" name="usuario.login" value="${usuario.login}">
				<div class="control-group">
					<label class="control-label">Senha Atual:</label>
					<div class="controls">
						<input type="password" name="senhaAtual" id="senhaAtual" size="15" maxlength="15" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Nova Senha:</label>
					<div class="controls">
						<input type="password" name="usuario.senha" id="senha" size="60" maxlength="60" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Confirme a senha:</label>
					<div class="controls">
						<input type="password" name="senhaConfirm" id="senhaConfirm" size="15" maxlength="15" />
					</div>
				</div>
				<div class="controls">
					<input class="btn btn-primary" type="submit" value="Atualizar" />
				</div>
			</form>
		</fieldset>
	</div>
	
	<script src="<c:url value="/js/usuario/usuario.js"/>"></script>
</body>
</html>