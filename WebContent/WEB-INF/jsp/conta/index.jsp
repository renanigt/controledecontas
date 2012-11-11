<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Listagem de Contas</title>
</head>
<body>
	<div class="container container-page">
		<span>
			<c:forEach var="error" items="${errors}"><li>${error.category} - ${error.message}</li></c:forEach>
		</span>
	
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Descrição</th>
					<th>Tipo de Conta</th>
					<th>Data</th>
					<th>Valor</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contas}" var="conta">
					<c:if test="${conta.tipoConta == 'DEBITO'}">
						<tr style="color: red;">
					</c:if>
					<c:if test="${conta.tipoConta == 'CREDITO'}">
						<tr style="color: blue;">
					</c:if>
						<td>${conta.descricao}</td>
						<td>${conta.tipoConta.descricao}</td>
						<td>${conta.data}</td>
						<td>${conta.valor}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>