<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
</head>
<body>
<div style="width: 400px; margin-left: auto; margin-right: auto;">
	<fieldset>
		<legend>Login</legend><br />
		<form class="form-horizontal" action="<c:url value="/login/logar" />" name="form_usuario" method="post">
			<div class="control-group">
				<label class="control-label">Username:</label>
				<div class="controls">
					<input type="text" name="username" id="username" size="15" maxlength="15" placeholder="Username" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Password:</label>
				<div class="controls">
					<input type="password" name="password" id="password" size="10" maxlength="10" placeholder="Password" />
				</div>
			</div>
			<div class="controls">
				<input class="btn btn-primary" type="submit" value="Logar" >
			</div>
		</form>
	</fieldset>
</div>
</body>
</html>