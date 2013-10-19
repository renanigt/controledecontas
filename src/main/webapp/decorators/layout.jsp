<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="Login - Controle de Contas" /></title>

<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/bootstrap-responsive.min.css"/>" rel="stylesheet" media="screen">

<script src="<c:url value="/js/jquery-1.8.1.min.js"/>"></script>

<style type="text/css">
	.container-page {
		margin-top: 60px;
	}
</style>

<decorator:head />
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
			
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
		        	<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		      	</a>
		      	
				<a class="brand" href="#">Controle de Contas</a>
				<c:if test="${usuarioSession.usuario != null}">				
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li><a href="<c:url value="/conta" />">Home</a></li>
							<li class="dropdown">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Contas<b class="caret"></b></a>
				                <ul class="dropdown-menu">
					                <li><a href="<c:url value="/conta/novo" />">Adicionar</a></li>
					                <li><a href="#myModal" data-toggle="modal">Listar</a></li>
				                </ul>
			                </li>
						</ul>
						<form class="navbar-search pull-left" action="<c:url value="/conta/pesquisaPorDescricao" />">
						  <input type="text" name="descricao" class="search-query" placeholder="Pesquisar">
						</form>
						<ul class="nav pull-right">
							<li class="dropdown">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${usuarioSession.usuario.username}<b class="caret"></b></a>
				                <ul class="dropdown-menu">
					                <li><a href="<c:url value="/usuario/atualiza/${usuarioSession.usuario.id}" />">Perfil</a></li>
					                <li><a href="#">Alterar Senha</a></li>
					                <li class="divider"></li>
					                <li><a href="<c:url value="/login/logout" />">Logout</a></li>
			                	</ul>
			                </li>
						</ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
	    	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    	<h3 id="myModalLabel">Pesquisar por Período</h3>
  		</div>
  		<div class="modal-body">
			<form class="form-horizontal" action="<c:url value="/conta/pesquisaPorPeriodo" />">
				<div class="control-group">
					<label class="control-label">Data Inicial:</label>
					<div class="controls">
						<input type="text" name="dataInicio" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Data Final:</label>
					<div class="controls">
						<input type="text" name="dataFim" />
					</div>
				</div>
				<div class="controls">
					<button class="btn" data-dismiss="modal" aria-hidden="true">Cancelar</button>
					<input class="btn btn-primary" type="submit" value="Listar" />
				</div>
			</form>
		</div>
	</div>	
	<decorator:body />
	
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</body>
</html>