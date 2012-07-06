<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<body>
	<div id="operationsDiv">
		<table id="operationTable" class="table">
			<tr>
				<td><input type="button" class="btn" name="Show Files"
					value="Show Files" onclick="showFiles();" />
				</td>
				<td><input type="button" class="btn" name="Upload File"
					value="Upload File" onclick="uploadFile();" />
				</td>
			</tr>
		</table>
	</div>
	<div id="filesDiv">
		<table id="filesTable"
			class="tablesorter table table-striped table-condensed">
			<thead>
				<tr>
					<th>No</th>
					<th>Filename</th>
					<th>Notes</th>
					<th>Type</th>
					<th>Last Modified</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${files != null}">
						<c:forEach var="file" items="${files}" varStatus="counter">
							<tr>
								<td>${counter.index + 1}</td>
								<td>${file.filename}</td>
								<td>${file.fileDesccription}</td>
								<td>${file.fileType}</td>
								<td>${file.lastModified}</td>
								<td><div align="center">
										<a href="download.html?id=${file.fileId}"><img
											src="../images/download.jpg" />
										</a> / <a href="delete.html?id=${file.fileId}"><img
											src="../images/delete.png" />
										</a>
									</div></td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div id="uploadDiv">
		<h4>Add New File</h4>
		<form action="upload.html" method="post" enctype="multipart/form-data">
			<table class="table table-striped table-condensed">
				<tr>
					<td>File to upload</td>
					<td><input type="file" name="file" class="btn" /> <input
						type="hidden" name="nodeId" value="${nodeId}" />
					</td>
				</tr>
				<tr>
					<td><strong>Notes</strong>
					</td>
					<td><input type="text" name="notes" width="60" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" name="submit" value="Add" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>