<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adicionar conta !</title>
</head>
<body>

	<span>
		<c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach>
	</span>

	<form action="<c:url value="/conta/novo/salvar" />" name="form_conta" method="post">
		<div>
			<label>Descrição:</label>
			<input type="text" name="conta.descricao" id="descricao" size="60" maxlength="60" />
		</div>
		<div>
			<label>Tipo de Conta:</label>
			<select name="conta.tipoConta" id="tipo" >
				<option>Selecione...</option>
				<c:forEach var="tipoConta" items="${tiposConta}">
					<option value="${tipoConta.name}">${tipoConta.descricao}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			<label>Valor:</label>
			<input type="text" name="conta.valor" id="valor" size="15" maxlength="15" />
		</div>
		<div>
			<label>Data:</label>
			<input type="text" name="conta.data" id="data" size="10" maxlength="10" />
		</div>
		<input type="submit" value="Salvar" />
	</form>

</body>
</html>