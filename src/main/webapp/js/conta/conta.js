$(document).ready(function() {
	
	$(".acoes").each(function(index) {
		$("#delete" + index).click(function() {
			
			var exclusao = confirm("Tem certeza que deseja excluir essa Conta ?");
			
			if(exclusao) {
				$.ajax({
					type: "GET",
					url: $("#urlDelete" + index).val(),
					success: function() {
						$("#urlDelete" + index).parent().parent().remove();
					}
				});
			}
			
		});
	});
	
});