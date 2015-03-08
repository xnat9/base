$(function() {
	
	$('#btn1').click(function(e) {
		$.ajax({
			url: '/base/test/m4.do',
			data: {
				//2011-1-1 2:2:1
				a: [1, 2], b: 'bbbb', date: "2011-2-1"
			},
			success: function(data) {
				console.info(data);
			}
		})
	});
})