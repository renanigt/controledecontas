$(document).ready(function() {
	
	$(".acoes").each(function(index) {
		$("#delete" + index).click(function() {
			
			var exclusao = confirm("Tem certeza que deseja excluir essa Conta ?");
			
			if(exclusao) {
				$.ajax({
					type: "GET",
					url: $("#urlDelete" + index).val(),
					success: function(json) {
						var saldo = json.saldo;
						var mensagem = json.mensagem;
						
						$("#urlDelete" + index).parent().parent().remove();
						$("#saldo").html(saldo);

						$("#mensagemSucesso").html(mensagem);
						$("#sucesso").fadeIn("slow");

						if(saldo > 0) {
							$("#saldo").css("color", "blue");
						} else if(saldo < 0) {
							$("#saldo").css("color", "red");
						} else {
							$("#saldo").css("color", "black");
						}
					},
					error: function(json) {
						var erro = $.parseJSON(json.responseText);
						$("#mensagemErro").html(erro);
						$("#erro").fadeIn("slow");
					}
				});
			}
			
		});
	});
	
});