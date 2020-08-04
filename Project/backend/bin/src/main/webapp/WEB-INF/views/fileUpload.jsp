<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>Board</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>


<div class="container">

	<h4 class="text-center">File Upload</h4>       

	<div class="col-xl-12 form-group">
		<input type="text" id="inputMessage">
	</div>
	<div class="col-xl-12 form-group">
		<input type="file" id="inputFileUpload">
		<div class="thumbnail-wrapper" style="width:200px; margin-top: 10px;">
			<img id="imgFileUpload" style="max-width: 100%;">
		</div>
	</div>
	<!-- / New for FileUpload -->
	<div class="col-md-12 float-right">
		<button id="btnFileUpload" class="btn btn-sm btn-primary btn-outline" data-dismiss="modal" type="button">Upload</button>
	</div>
									
</div>


<script>



$(document).ready(function(){

	$("#btnFileUpload").click(function(){
		fileUpload();
	});
	
	$("#inputFileUpload").change(function(e){

		if( this.files && this.files[0] ){
			var reader = new FileReader();
			reader.readAsDataURL(this.files[0]);
			reader.onload = function(e){
				$("#imgFileUpload").attr("src", e.target.result);
			}
		}
	});

});

function fileUpload(){

	var formData = new FormData();

	formData.append("message", $("#inputMessage").val());
	formData.append("file", $("#inputFileUpload")[0].files[0]);
	
	$.ajax(
	{
        type : 'post',
        url : '<%= contextPath%>/fileupload',
        dataType : 'json',
        data : formData,
        contentType: false,
        processData: false,
        success : function(data, status, xhr) { 
        	alert('파일을 업로드했습니다.');
        }, 
        error: function(jqXHR, textStatus, errorThrown) 
        {
        	alert('파일을 업로드하지 못했습니다.');
        	console.log(jqXHR.responseText); 
        }
    });
}


</script>

</body>
</html>