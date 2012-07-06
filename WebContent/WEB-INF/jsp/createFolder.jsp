<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<body>
	<form action="createFolder.html" method="post" id="createFolderForm">
		<table class="table table-striped table-condensed">
			<tr>
				<td>File to upload</td>
				<td><input type="text" name="folderName" /> 
			</tr>
			<tr>
				<td><strong>Notes</strong></td>
				<td><input type="text" name="notes" width="60" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submit" value="Add" /></td>
			</tr>
		</table>
	</form>
</body>
</html>