<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jquery 文件上传测试</title>
<!-- Bootstrap styles -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- Generic page styles -->
<link rel="stylesheet" href="css/style.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="css/jquery.fileupload.css">
</head>
<body>
	<div class="container">
		<!-- The fileinput-button span is used to style the file input field as button -->
		<span class="btn btn-success fileinput-button"> <i
			class="glyphicon glyphicon-plus"></i> <span>Add files...</span> <!-- The file input field used as target for the file upload widget -->
			<input id="fileupload" type="file" name="file">
		</span>
		<br> <br>
		<!-- The global progress bar -->
		<div id="progress" class="progress">
			<div class="progress-bar progress-bar-success"></div>
		</div>
		<!-- The container for the uploaded files -->
		<div id="files" class="files"></div>
		<br>
	</div>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="js/vendor/jquery.ui.widget.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="js/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="js/jquery.fileupload-process.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- The File Upload validation plugin -->
<script src="js/jquery.fileupload-validate.js"></script>
<script type="text/javascript">
	$(function() {
		'use strict';
		var uploadButton = $('<button/>')
		.addClass('btn btn-primary')
		//.prop('disabled', true)
		.text('开始上传')
		.on('click', function() {
			var $this = $(this), data = $this.data();
			$this.off('click')
			.text('Abort')
			.on('click', function() {
				$this.remove();
				data.abort();
			});
			data.submit().always(function() {
				$this.remove();
			});
		})
		
		$('#fileupload').fileupload({
			url : '/base/test/m5',
			autoUpload: false,
			dataType : 'json',
			acceptFileTypes: /.+\.apk$/,
			maxFileSize: 5000000, // 5 MB,
			add: function(e, data) {
				console.info(data);
				$('#files').html('');
				data.context = $('<div/>').appendTo('#files');
				$.each(data.files, function(index, file) {
					var node = $('<p/>').append($('<span/>').text(file.name));
					if (!index) {
						node.append('<br/>').append(uploadButton.clone(true).data(data));
					}
					node.appendTo(data.context);
				});
			},
			done: function(e, data) {
				$.each(data.result.files, function(index, file) {
					if (file.url) {
						var link = $('<a>').attr('target', '_blank').prop('href', file.url);
						$(data.context.children()[index]).wrap(link);
					} else if (file.error) {
						var error = $('<span class="text-danger"/>').text(file.error);
						$(data.context.children()[index]).append('<br>').append(error);
					}
				})
			}
		}).on('fileuploadprocessalways', function(e, data) {
			var index = data.index,
				file = data.files[index],
				node = $(data.context.children()[index]);
			if (file.preview) {
				node.prepend('<br/>').prepend(file.preview);
			}
			if (file.error) {
				node.append('<br/>').append($('<span class="text-danger"/>')).text(file.error);
			}
			if (index +　1 === data.files.length) {
				data.context.find('button').text('Upload').prop('disabled', !!data.files.error);
			}
		}).on('fileuploadprogressall', function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			$('#progress .progress-bar').css('width', progress + '%');
		}).on('fileuploadfail', function(e, data) {
			console.info("fileuploadfail:")
			console.info(data);
		}).prop('disabled', !$.support.fileInput)
		.parent().addClass($.support.fileInput ? undefined : 'disabled');
	})
</script>
</body>
</html>