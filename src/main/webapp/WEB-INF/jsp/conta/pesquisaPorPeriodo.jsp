<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Listagem de Contas</title>
</head>
<body>
	<div class="container container-page">
		<div id="errors" class="alert alert-error">
		</div>
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Descrição</th>
					<th>Tipo de Conta</th>
					<th>Data</th>
					<th>Valor</th>
					<th>Ações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${contas}" var="conta">
					<c:choose>
						<c:when test="${conta.tipoConta == 'CREDITO'}">
							<tr style="color: blue;">
						</c:when>
						<c:otherwise>
							<tr style="color: red;">
						</c:otherwise>
					</c:choose>
						<td>${conta.descricao}</td>
						<td>${conta.tipoConta.descricao}</td>
						<td>${conta.data}</td>
						<td>R$ ${conta.valor}</td>
						<td>
							<a href="<c:url value='/conta/atualiza/${conta.id}' />"><img title="Editar" src="<c:url value='/images/editar.png' />" /></a>
							<a href="<c:url value='/conta/deleta/${conta.id}' />"><img title="Deletar" src="<c:url value='/images/delete.png' />" /></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>