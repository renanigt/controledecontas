$(document).ready(function() {
	
	$("[id=acoes]").each(function(index) {
		$("#delete" + index).click(function() {
			$.ajax({
				type: "GET",
				url: $("#urlDelete" + index).val(),
				success: function() {
					$("#urlDelete" + index).parent().parent().remove();
				}
			});
		});
	});
	
});