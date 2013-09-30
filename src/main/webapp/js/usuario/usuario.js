$(document).ready(function() {
	
	$(".close").each(function() {
		$(this).click(function() {
			$(this).parent().fadeOut("slow");
		});
	});
	
});