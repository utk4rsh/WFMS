package com.wfms.dao.beans;

import java.util.List;

public class DirectoryBean {

	private int nodeId;
	private String nodeName;
	private int lft;
	private int rgt;
	private int children;
	private int depth;
	private boolean hasChild;
	private List<DirectoryBean> childNodes;
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getLft() {
		return lft;
	}
	public void setLft(int lft) {
		this.lft = lft;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public int getRgt() {
		return rgt;
	}
	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public List<DirectoryBean> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(List<DirectoryBean> childNodes) {
		this.childNodes = childNodes;
	}
	
}
