$(function() {
	console.info('#btn1');
	$('#btn1').click(function(e) {
		$.ajax({
			url: '/base/test/m3.do',
			data: {
				a: [1, 2], b: 'bbbb'
			},
			success: function(data) {
				console.info(data);
			}
		})
		console.info(e);
	});
})