$(function() {
	$('.case ul li a').each(function(i) {
		$(this).hover(function() {
			$(this).parent().find('.tips').addClass('hover');
			$(this).parent().addClass('lihover');
		}, function() {
			$(this).parent().find('.tips').removeClass('hover');
			$(this).parent().removeClass('lihover');
		});
	});
})