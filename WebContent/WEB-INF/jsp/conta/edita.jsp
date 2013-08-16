<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
	<title>Atualizar Conta</title>
</head>
<body>
	<div class="container container-page">
		<fieldset>
			<legend>Atualizar Conta</legend>
			<c:if test="${not empty errors}">
				<div class="alert alert-error">
					<c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach>
				</div>
			</c:if>
			<c:if test="${not empty erro}">
				<div class="alert alert-error">
					${erro}
				</div>
			
			</c:if>
	
			<form class="form-horizontal" action="<c:url value="/conta/atualiza/salvar" />" name="form_conta" method="post">
				<input type="hidden" name="conta.id" value="${conta.id}" />
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
								<c:choose>
									<c:when test="${conta.tipoConta == tipoConta}">
										<option value="${tipoConta.name}" selected="selected">${tipoConta.descricao}</option>
									</c:when>
									<c:otherwise>
										<option value="${tipoConta.name}">${tipoConta.descricao}</option>
									</c:otherwise>
								</c:choose>
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
							value="<fmt:formatDate value='${conta.data}' type='date' pattern='dd/MM/yyyy'/>" />
					</div>
				</div>
				<div class="controls">
					<input class="btn btn-primary" type="submit" value="Salvar" />
				</div>
			</form>
		</fieldset>
	</div>
	
</body>
</html>