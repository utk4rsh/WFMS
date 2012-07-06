
function Directory_to_ul(branches){
	var ul = document.createElement("ul");
	ul.setAttribute("class","nav nav-list");
	var n = branches.length;
	for(var i= 0 ; i < n; i++){
		var child = branches[i];
		var li = document.createElement("li");
		var text = document.createElement("div");
		$(text).append("<input type='image'  src='../images/folder.png' name='"+child.nodeName+"' value='"+child.nodeName+"' onclick='getAjaxResponseForDir("+child.nodeId+");'/>");
		$(text).append(child.nodeName);
		li.appendChild(text);
		if(child.hasChild){
			li.appendChild(Directory_to_ul(child.childNodes));
		}
		ul.appendChild(li);
	}
	return ul;
}

function sharedFolder_to_ul(branches){
	var ul = document.createElement("ul");
	ul.setAttribute("class","nav nav-list");
	var n = branches.length;
	for(var i= 0 ; i < n; i++){
		var child = branches[i];
		var li = document.createElement("li");
		var text = document.createElement("div");
		$(text).append("<input type='image'  src='../images/sharedfolder.png' name='"+child.nodeName+"' value='"+child.nodeName+"' onclick='getAjaxResponseForSharedFolder("+child.nodeId+");'/>");
		$(text).append(child.nodeName);
		li.appendChild(text);
		if(child.hasChild){
			li.appendChild(sharedFolder_to_ul(child.childNodes));
		}
		ul.appendChild(li);
	}
	return ul;
}

function getAjaxResponseForDir(nodeId){
	$.ajax({
		  url: "files.html?nodeId="+nodeId,
		  cache: false,
		  success: function(html){
			  $("#right").html(html);
			  $("#folderDiv").hide();
			  $("#renameDiv").hide();
			  $("#filesDiv").hide();
			  $("#uploadDiv").hide(); 
			  $("#sharefolderFormDiv").hide();
		  }
		});
}


function getAjaxResponseForSharedFolder(nodeId){
	$.ajax({
		  url: "sharedFolder.html?nodeId="+nodeId,
		  cache: false,
		  success: function(html){
			  $("#right").html(html);
			  $("#folderDiv").hide();
			  $("#renameDiv").hide();
			  $("#filesDiv").hide();
			  $("#uploadDiv").hide(); 
			  $("#sharefolderFormDiv").hide();
		  }
		});
}
function showNewFolderForm(){
	$("#folderDiv").show();
	$("#uploadDiv").hide();
	$("#filesDiv").hide();
	$("#uploadDiv").hide();
	$("#sharefolderFormDiv").hide();
}

function showNewFolderForm(){
	$("#folderDiv").show();
	$("#uploadDiv").hide();
	$("#filesDiv").hide();
	$("#uploadDiv").hide();
	$("#sharefolderFormDiv").hide();
}

function showRenameForm(){
	$("#renameDiv").show();
	$("#folderDiv").hide();
	$("#uploadDiv").hide();
	$("#filesDiv").hide();
	$("#uploadDiv").hide();
	$("#sharefolderFormDiv").hide();
}

function showFiles(){
	$("#renameDiv").hide();
	$("#folderDiv").hide();
	$("#uploadDiv").hide();
	$("#filesDiv").show();
	$("#filesTable").tablesorter();
	$("#uploadDiv").hide();
	$("#sharefolderFormDiv").hide();
}

function uploadFile(){
	$("#renameDiv").hide();
	$("#folderDiv").hide();
	$("#uploadDiv").hide();
	$("#filesDiv").hide();
	$("#uploadDiv").show();
	$("#sharefolderFormDiv").hide();
} 


function shareThisFolder(){
	$("#renameDiv").hide();
	$("#folderDiv").hide();
	$("#uploadDiv").hide();
	$("#filesDiv").hide();
	$("#uploadDiv").hide();
	$("#sharefolderFormDiv").show();
	
} 

