package com.wfms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.wfms.dao.beans.DirectoryBean;
import com.wfms.dao.beans.FileBean;
import com.wfms.dao.beans.SharedNodeBean;
import com.wfms.dao.beans.UserBean;
import com.wfms.service.FilesService;

/**
 * 
 * @author Utkarsh
 *
 */

@Controller	
@RequestMapping("/home")
public class FileController {

	private FilesService filesService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/myFiles")
	protected ModelAndView landingPage(HttpServletRequest request, HttpServletResponse response) {
		String userName = getLoggedInUser(); 
		Gson gson = new Gson();
		List<DirectoryBean> directoryBeans = filesService.retrieveDirectoryStructure(userName);
		String json = gson.toJson(directoryBeans);
		ModelAndView modelAndView = new ModelAndView("home");
		List<String> sharedNodesJson = filesService.retrieveSharedNodes(userName);
		modelAndView.addObject("json", json);
		modelAndView.addObject("sharedNodesJson", sharedNodesJson);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/files")
	protected ModelAndView listFiles(HttpServletRequest request, HttpServletResponse response) {
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		List<FileBean> files = filesService.listAll(nodeId);
		List<UserBean> userBeans = filesService.retrieveUsers();
		ModelAndView modelAndView = new ModelAndView("files");
		modelAndView.addObject("userBeans", userBeans);
		modelAndView.addObject("files", files);
		modelAndView.addObject("nodeId", nodeId);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sharedFolder")
	protected ModelAndView sharedFiles(HttpServletRequest request, HttpServletResponse response){
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		List<FileBean> files = filesService.listAll(nodeId);
		ModelAndView modelAndView = new ModelAndView("sharedFolder");
		modelAndView.addObject("files", files);
		modelAndView.addObject("nodeId", nodeId);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		System.out.println(nodeId);
		FileBean file = new FileBean();
		file.setFilename(multipartFile.getOriginalFilename());
		file.setFileDesccription(request.getParameter("notes"));
		file.setFileType(multipartFile.getContentType());
		file.setFileContent(multipartFile.getBytes());
		file.setNodeId(nodeId);
		file.setOwnerName(getLoggedInUser());
		this.filesService.save(file);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = ServletRequestUtils.getRequiredIntParameter(request, "id");
		FileBean file = this.filesService.find(id);
		response.setContentType(file.getFileType());
		response.setContentLength(file.getFileContent().length);
		response.setHeader("Content-Disposition","attachment; filename=\"" + file.getFilename() +"\"");
		FileCopyUtils.copy(file.getFileContent(), response.getOutputStream());
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/delete")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response){
		int id =Integer.parseInt( request.getParameter("id"));
		this.filesService.delete(id);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/createFolder")
	public ModelAndView createFolder(HttpServletRequest request, HttpServletResponse response){
		int parentId = Integer.parseInt(request.getParameter("nodeId"));
		String nodeName = request.getParameter("nodeName");
		String userName = getLoggedInUser(); 
		filesService.insertNodeToDirectory(parentId, nodeName, userName);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/deleteFolder")
	public ModelAndView renameFolder(HttpServletRequest request, HttpServletResponse response){
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String userName = getLoggedInUser(); 
		filesService.deleteNodeInDirectory(nodeId, userName);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/renameFolder")
	public ModelAndView deleteFolder(HttpServletRequest request, HttpServletResponse response){
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String newNodeName = request.getParameter("newNodeName");
		filesService.renameNode(nodeId, newNodeName);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/shareFolder")
	protected ModelAndView shareFolder(HttpServletRequest request, HttpServletResponse response){
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String sharedToUserName = request.getParameter("sharedToUserName");
		System.out.println(sharedToUserName);
		SharedNodeBean bean = new SharedNodeBean();
		bean.setNodeId(nodeId);
		bean.setOwnerName(getLoggedInUser());
		bean.setUserName(sharedToUserName);
		filesService.insertSharedNodeDetails(bean);
		return new ModelAndView("redirect:myFiles.html");
	}

	/**
	 * 
	 * @return
	 */
	public String getLoggedInUser(){
		User loginUser =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		String userName = loginUser.getUsername(); 
		return userName;
	}

	/***
	 * 
	 * @return
	 */
	public FilesService getFilesService() {
		return filesService;
	}

	/**
	 * 
	 * @param filesService
	 */
	@Autowired
	public void setFilesService(FilesService filesService) {
		this.filesService = filesService;
	}

}
