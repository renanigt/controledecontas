<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Adicionar Conta</title>
</head>
<body>
	<div class="container container-page">
		<fieldset>
			<legend>Adicionar Conta</legend>
			<c:if test="${not empty errors}">
				<div class="alert alert-error">
					<c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach>
				</div>
			</c:if>
	
			<form class="form-horizontal" action="<c:url value="/conta/novo/salvar" />" name="form_conta" method="post">
				<div class="control-group">
					<label class="control-label">Descrição:</label>
					<div class="controls">
						<input class="input-xlarge" type="text" name="conta.descricao" id="descricao" 
							maxlength="100" value="${conta.descricao}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Tipo de Conta:</label>
					<div class="controls">
						<select name="conta.tipoConta" id="tipo" >
							<option>Selecione...</option>
							<c:forEach var="tipoConta" items="${tiposConta}">
								<option value="${tipoConta.name}">${tipoConta.descricao}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Valor:</label>
					<div class="controls">
						<input type="text" name="conta.valor" id="valor" size="15" maxlength="15"
							value="${conta.valor}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Data:</label>
					<div class="controls">
						<input type="text" name="conta.data" id="data" size="10" maxlength="10"
							value="${conta.data}" />
					</div>
				</div>
				<div class="controls">
					<input class="btn btn-primary" type="submit" value="Salvar" />
				</div>
			</form>
		</fieldset>
	</div>
	
	<script src="<c:url value="/js/conta/formulario.js"/>"></script>
</body>
</html>