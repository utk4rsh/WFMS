package com.wfms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.beans.DirectoryBean;
import com.wfms.dao.beans.FileBean;
import com.wfms.dao.beans.SharedNodeBean;
import com.wfms.dao.beans.UserBean;
import com.wfms.dao.impl.DirectoryDaoImpl;
import com.wfms.dao.impl.FilesDaoImpl;

/**
 * 
 * @author Utkarsh
 *
 */
@Service
public class FilesService {

	private FilesDaoImpl filesDaoImpl;
	private DirectoryDaoImpl directoryDaoImpl;
	
	/**
	 * 
	 * @param file
	 */
	public void save(final FileBean file) {
		filesDaoImpl.save(file);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FileBean find(int id) {
		return filesDaoImpl.find(id);
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(int id) {
		filesDaoImpl.delete(id);
	}

	/**
	 * 
	 * @return
	 */
	public FilesDaoImpl getFilesDaoImpl() {
		return filesDaoImpl;
	}

	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<FileBean> listAll(int nodeId){
		return filesDaoImpl.listAll(nodeId);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<DirectoryBean> retrieveDirectoryStructure(String userName){
		return directoryDaoImpl.getDirectoryStructure(userName);
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<String> retrieveSharedNodes(String userName){
		return directoryDaoImpl.getNodesFromDepth(userName);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserBean> retrieveUsers(){
		return filesDaoImpl.getUsers();
	}
	
	/**
	 * 
	 * @param sharedNodeBean
	 */
	public void insertSharedNodeDetails(SharedNodeBean sharedNodeBean){
		directoryDaoImpl.addSharedFolder(sharedNodeBean);
	}
	
	/**
	 * 
	 * @param parentId
	 * @param nodeName
	 * @param userName
	 */
	public void insertNodeToDirectory(int parentId, String nodeName, String userName){
		directoryDaoImpl.addNodeToDirectory(parentId, nodeName, userName);
	}

	/***
	 * 
	 * @param nodeId
	 * @param userName
	 */
	public void deleteNodeInDirectory(int nodeId,String userName){
		directoryDaoImpl.deleteNode(nodeId, userName);
	}
	
	/**
	 * 
	 * @param nodeId
	 * @param newNodeName
	 */
	public void renameNode(int nodeId, String newNodeName){
		directoryDaoImpl.renameNodeInDirectory(nodeId, newNodeName);
	}
	
	/**
	 * 
	 */
	public DirectoryDaoImpl getDirectoryDaoImpl() {
		return directoryDaoImpl;
	}
	
	/**
	 * 
	 * @param directoryDaoImpl
	 */
	@Autowired
	public void setDirectoryDaoImpl(DirectoryDaoImpl directoryDaoImpl) {
		this.directoryDaoImpl = directoryDaoImpl;
	}

	/**
	 * 
	 * @param filesDaoImpl
	 */
	@Autowired
	public void setFilesDaoImpl(FilesDaoImpl filesDaoImpl) {
		this.filesDaoImpl = filesDaoImpl;
	}

}
