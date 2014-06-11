$(document).ready(function() {
	
	$(".close").click(function() {
		$(this).parent().fadeOut("slow");
	});
	
	$(".acoes .delete").click(function(event) {
		event.preventDefault();
		
		var exclusao = confirm("Tem certeza que deseja excluir essa Conta ?");
		var conta = $(this).parent().parent();
		
		if(exclusao) {
			$.ajax({
				type: "GET",
				url: $(this).attr("href"),
				success: function(json) {
					var saldo = json.saldo;
					var mensagem = json.mensagem;
					
					conta.fadeOut();
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