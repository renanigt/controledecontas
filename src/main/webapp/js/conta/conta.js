$(document).ready(function() {
	
	$(".acoes").each(function(index) {
		$("#delete" + index).click(function() {
			
			var exclusao = confirm("Tem certeza que deseja excluir essa Conta ?");
			
			if(exclusao) {
				$.ajax({
					type: "GET",
					url: $("#urlDelete" + index).val(),
					success: function(json) {
						var saldo = json.usuario.saldo;
						
						$("#urlDelete" + index).parent().parent().remove();
						$("#saldo").html(json.usuario.saldo);
						
						if(saldo > 0) {
							$("#saldo").css("color", "blue");
						} else if(saldo < 0) {
							$("#saldo").css("color", "red");
						} else {
							$("#saldo").css("color", "black");
						}
					}
				});
			}
			
		});
	});
	
});