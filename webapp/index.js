$(function() {
	
	$('#btn1').click(function(e) {
		$.ajax({
			url: '/base/test/m4.do',
			data: {
				//2011-1-1 2:2:1
				a: [1, 2], b: 'bbbb', date: new Date(),
				c: {
					c1: 'c1', c2: 2
				}
			},
			success: function(data) {
				console.info(data);
			}
		})
	});
})