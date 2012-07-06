<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<body>
<div id="operationsDiv">
		<table id="operationTable" class="table">
		<tr>
				<td><input type="button"  class="btn" name="Create Folder"
					value="Create New Folder" onclick="showNewFolderForm();" /></td>
				<td><form:form action="deleteFolder.html" method="post"
						id="deleteFolderForm">
						<input class="btn" type="submit" name="Delete" value="Delete Folder" />
						<input type="hidden"name="nodeId" value="${nodeId}" />
					</form:form></td>
				<td><input type="button" class="btn" name="Rename Folder"
					value="Rename Folder" onclick="showRenameForm();" /></td>
				<td><input type="button" class="btn" name="Show Files"
					value="Show Files" onclick="showFiles();" /></td>
				<td><input type="button" class="btn" name="Upload File"
					value="Upload File" onclick="uploadFile();" /></td>
				<td><input type="button" class="btn" name="Share This Folder"
					value="Share This Folder" onclick="shareThisFolder();" /></td>
			</tr>
		</table>
</div>
<div id="filesDiv">
	<table id="filesTable"
		class="tablesorter table table-striped table-condensed">
		<thead>
			<tr>
				<th>S.N.</th>
				<th>Filename</th>
				<th>Description</th>
				<th>Type</th>
				<th>Owner</th>
				<th>Last Modified</th>
				<th>Download/Delete</th>
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
							<td>${file.ownerName}</td>
							<td>${file.lastModified}</td>
							<td><div align="center">
									<a href="download.html?id=${file.fileId}"><img src="../images/download.jpg" /></a>  / <a
										href="delete.html?id=${file.fileId}"><img src="../images/delete.png" /></a>/ 
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>	

<div id="folderDiv">
	<form:form action="createFolder.html" method="post" id="createFolderForm">
		<table class="table table-striped table-condensed">
			<tr>
				<td>Folder Name</td>
				<td>
				<input type="text" name="nodeName" /></td>
			</tr>
			<tr>
				<td><input type="hidden"name="nodeId" value="${nodeId}" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input class="btn" type="submit" name="submit" value="Add" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div id="sharefolderFormDiv">
	<form:form action="shareFolder.html" method="post" id="createFolderForm">
		<table class="table table-striped table-condensed">
			<tr>
				<td>Enter User</td>
				<td>
				<input type="text" name="sharedToUserName" /></td>
				</tr>
			<tr>
				<td><input type="hidden"name="nodeId" value="${nodeId}" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input class="btn" type="submit" name="submit" value="Share" />
				</td>
			</tr>
		</table>
	</form:form>
</div>

<div id="renameDiv">
	<form:form action="renameFolder.html" method="post" id="renameForm">
		<table class="table table-striped table-condensed">
			<tr>
				<td>New Name</td>
				<td>
				<input type="text" name="newNodeName" /></td>
			</tr>
			<tr>
				<td><input type="hidden"name="nodeId" value="${nodeId}" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input class="btn" type="submit" name="rename" value="Rename" />
				</td>
			</tr>
		</table>
	</form:form>
</div>
<div id="uploadDiv">
	<h4>Add New File</h4>
	<form action="upload.html" method="post" enctype="multipart/form-data">
		<table class="table table-striped table-condensed">
			<tr>
				<td>File to upload</td>
				<td><input type="file" name="file" class="btn" /> <input type="hidden"
					name="nodeId" value="${nodeId}" /></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><input type="text" name="notes" width="60" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submit" value="Upload" /></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>