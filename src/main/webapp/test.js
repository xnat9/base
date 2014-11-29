//(function() {
//	console.info(this);
//	console.info(Object.prototype.toString());
//})();
Ext.onReady(function() {
	function* fibonacci(max) {
		var pre = 0; cur = 1;
		while (cur < max) {
			var tmp = pre;
			pre = cur;
			cur = tmp + pre;
			yield cur;
		}
	}
	for (var i of fibonacci(20)) {
		console.info(i);
	}
})