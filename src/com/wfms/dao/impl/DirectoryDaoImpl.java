package com.wfms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.wfms.dao.JdbcTemplateDao;
import com.wfms.dao.beans.DirectoryBean;
import com.wfms.dao.beans.SharedNodeBean;

/**
 * 
 * @author Utkarsh
 *
 */
public class DirectoryDaoImpl extends JdbcTemplateDao {

	/**
	 * String Literals:
	 */
	private static final String NODE_ID = "node_id";
	private static final String NODE_NAME = "node_name";
	private static final String LFT = "lft";
	private static final String RGT = "rgt";
	private static final String DEPTH = "depth";
	protected static final String USER_NAME = "user_name";
	protected static final String OWNER_NAME = "owner_name";
	
	/**
	 * Query Strings:
	 */

	private String insertNodeQuery;
	private String deleteNodeQuery;
	private String renameNodeQuery;
	private String directoryStructureQuery;
	private String nodeLeftForInsertQuery;
	private String nodeRightForInsertQuery;
	private String updateRightForInsertQuery;
	private String updateLeftForInsertQuery;
	private String selectForDeleteQuery;
	private String updateRightForDeleteQuery;
	private String updateLeftForDeleteQuery;
	private String depthOfNodeQuery;
	private String nodesBelowQuery;
	private String sharedNodesQuery;
	private String insertSharedNodeQuery;

	/**
	 * 
	 * @param parentId
	 * @param nodeName
	 * @param userName
	 */
	@Transactional
	public void addNodeToDirectory(int parentId, String nodeName, String userName){
		int left = getLeftValueOfNode(parentId);
		this.jdbcTemplate.update(updateRightForInsertQuery, new Object[]{left, userName});
		this.jdbcTemplate.update(updateLeftForInsertQuery, new Object[]{left, userName});
		this.jdbcTemplate.update(insertNodeQuery, new Object[]{nodeName, userName, left+1, left+2});
	}

	/**
	 * 
	 * @param nodeId
	 * @param userName
	 */
	@Transactional
	public void deleteNode(int nodeId, String userName){
		SqlRowSet rs = this.jdbcTemplate.queryForRowSet(selectForDeleteQuery, new Object[]{nodeId, userName});
		int lft = 0;
		int rgt = 0;
		int widht =0;
		while(rs.next()){
			lft = rs.getInt(LFT);
			rgt = rs.getInt(RGT);
			widht = rgt - lft + 1;
		}
		System.out.println(widht);
		this.jdbcTemplate.update(deleteNodeQuery, new Object[]{lft, rgt, userName});
		this.jdbcTemplate.update(updateRightForDeleteQuery, new Object[]{widht, rgt, userName});
		this.jdbcTemplate.update(updateLeftForDeleteQuery, new Object[]{widht, rgt, userName});
	}
	
	/**
	 * 
	 * @param nodeId
	 * @param newNodeName
	 */
	public void renameNodeInDirectory(int nodeId, String newNodeName){
		this.jdbcTemplate.update(renameNodeQuery, new Object[]{newNodeName, nodeId});
	}
	
	/**
	 * 
	 * @param sharedNodeBean
	 */
	public void addSharedFolder(final SharedNodeBean sharedNodeBean){
		int nodeId = sharedNodeBean.getNodeId();
		int left = getLeftValueOfNode(nodeId);
		int right = getRightValueOfNode(nodeId);
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(insertSharedNodeQuery);
				statement.setInt(1, sharedNodeBean.getNodeId());
				statement.setString(3, sharedNodeBean.getOwnerName());
				statement.setString(2, sharedNodeBean.getUserName());
				return statement;
			}
		});
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<DirectoryBean> getDirectoryStructure(String userName){
		List<DirectoryBean> directoryBeans =this.getJdbcTemplate().query(directoryStructureQuery, new Object[]{userName, userName}, new RowMapper<DirectoryBean>() {
			public DirectoryBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				DirectoryBean bean = new DirectoryBean();
				bean.setNodeId(rs.getInt(NODE_ID));
				bean.setNodeName(rs.getString(NODE_NAME));
				bean.setLft(rs.getInt(LFT));
				bean.setRgt(rs.getInt(RGT));
				bean.setDepth(rs.getInt(DEPTH));
				bean.setChildren((int)(bean.getRgt()-bean.getLft())/2);
				return bean;
			}
		});
		getNodesFromDepth(userName);
		return buildSingleBeanForTree(directoryBeans);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<String> getNodesFromDepth(String userName){
		List<SharedNodeBean> sharedNodeBeans = this.jdbcTemplate.query(sharedNodesQuery, new Object[]{userName}, new RowMapper<SharedNodeBean>() {
			public SharedNodeBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				SharedNodeBean bean = new SharedNodeBean();
				bean.setNodeId(rs.getInt(NODE_ID));
				bean.setUserName(rs.getString(USER_NAME));
				bean.setOwnerName(rs.getString(OWNER_NAME));
				return bean;
			}
		});
		List<String> sharedDirectoryJsons = new ArrayList<String>();
		for(int i = 0 ; i < sharedNodeBeans.size(); i++){
			int left = getLeftValueOfNode(sharedNodeBeans.get(i).getNodeId());
			int right = getRightValueOfNode(sharedNodeBeans.get(i).getNodeId());
			List<DirectoryBean> directoryBeans = getDirectoryAfterDepth(sharedNodeBeans.get(i).getNodeId(), left, right);	
			List<DirectoryBean> finaldirectoryBean = buildSingleBeanForTree(directoryBeans);
			Gson gson = new Gson();
			String json = gson.toJson(finaldirectoryBean);
			sharedDirectoryJsons.add(json);
		}
		return sharedDirectoryJsons;
	}
	
	
	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public int getLeftValueOfNode(int nodeId){
		int left = this.jdbcTemplate.queryForInt(nodeLeftForInsertQuery, new Object[]{nodeId});
		return left;
	}
	
	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public int getRightValueOfNode(int nodeId){
		int right = this.jdbcTemplate.queryForInt(nodeRightForInsertQuery, new Object[]{nodeId});
		return right;
	}
	/**
	 * 
	 * @param nodeId
	 * @param left
	 * @param right
	 * @return
	 */
	public List<DirectoryBean> getDirectoryAfterDepth(int nodeId, int left, int right){
		List<DirectoryBean> directoryBeans =this.getJdbcTemplate().query(nodesBelowQuery, new Object[]{nodeId, left, right, right}, new RowMapper<DirectoryBean>() {
			public DirectoryBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				DirectoryBean bean = new DirectoryBean();
				bean.setNodeId(rs.getInt(NODE_ID));
				bean.setNodeName(rs.getString(NODE_NAME));
				bean.setLft(rs.getInt(LFT));
				bean.setRgt(rs.getInt(RGT));
				bean.setDepth(rs.getInt(DEPTH));
				bean.setChildren((int)(bean.getRgt()-bean.getLft())/2);
				return bean;
			}
		});
		return directoryBeans;
	}
	
	/**
	 * 
	 * @param directoryBeans
	 * @return
	 */
	public List<DirectoryBean> buildSingleBeanForTree(List<DirectoryBean> directoryBeans){
		int minDepth = 200;
		for(int i =0 ; i < directoryBeans.size(); i++){
			DirectoryBean outerBean = directoryBeans.get(i);
			List<DirectoryBean> beans = new ArrayList<DirectoryBean>();
			outerBean.setChildNodes(beans);
			if(outerBean.getDepth() < minDepth)
				minDepth = outerBean.getDepth() ;
			for(int j = 0 ; j < directoryBeans.size(); j++){
				DirectoryBean innerBean = directoryBeans.get(j);
				if(innerBean.getLft() > outerBean.getLft() && innerBean.getRgt() < outerBean.getRgt() && innerBean.getDepth() - outerBean.getDepth() ==1){
					outerBean.getChildNodes().add(innerBean);
					outerBean.setHasChild(true);
				}
			}
		}
		List<DirectoryBean> finalBeans = new ArrayList<DirectoryBean>();
		for(int i =0 ; i < directoryBeans.size(); i++){
			if(directoryBeans.get(i).getDepth()==minDepth){
				finalBeans.add(directoryBeans.get(i));
			}
		}
		return finalBeans;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInsertNodeQuery() {
		return insertNodeQuery;
	}

	/**
	 * 
	 * @param insertNodeQuery
	 */
	public void setInsertNodeQuery(String insertNodeQuery) {
		this.insertNodeQuery = insertNodeQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getDeleteNodeQuery() {
		return deleteNodeQuery;
	}

	/**
	 * 
	 * @param deleteNodeQuery
	 */
	public void setDeleteNodeQuery(String deleteNodeQuery) {
		this.deleteNodeQuery = deleteNodeQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getRenameNodeQuery() {
		return renameNodeQuery;
	}

	/**
	 * 
	 * @param renameNodeQuery
	 */
	public void setRenameNodeQuery(String renameNodeQuery) {
		this.renameNodeQuery = renameNodeQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getDirectoryStructureQuery() {
		return directoryStructureQuery;
	}

	/**
	 * 
	 * @param directoryStructureQuery
	 */
	public void setDirectoryStructureQuery(String directoryStructureQuery) {
		this.directoryStructureQuery = directoryStructureQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getNodeLeftForInsertQuery() {
		return nodeLeftForInsertQuery;
	}

	/**
	 * 
	 * @param nodeLeftForInsertQuery
	 */
	public void setNodeLeftForInsertQuery(String nodeLeftForInsertQuery) {
		this.nodeLeftForInsertQuery = nodeLeftForInsertQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getUpdateRightForInsertQuery() {
		return updateRightForInsertQuery;
	}

	/**
	 * 
	 * @param updateRightForInsertQuery
	 */
	public void setUpdateRightForInsertQuery(String updateRightForInsertQuery) {
		this.updateRightForInsertQuery = updateRightForInsertQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getUpdateLeftForInsertQuery() {
		return updateLeftForInsertQuery;
	}

	/**
	 * 
	 * @param updateLeftForInsertQuery
	 */
	public void setUpdateLeftForInsertQuery(String updateLeftForInsertQuery) {
		this.updateLeftForInsertQuery = updateLeftForInsertQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getSelectForDeleteQuery() {
		return selectForDeleteQuery;
	}

	/**
	 * 
	 * @param selectForDeleteQuery
	 */
	public void setSelectForDeleteQuery(String selectForDeleteQuery) {
		this.selectForDeleteQuery = selectForDeleteQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getUpdateLeftForDeleteQuery() {
		return updateLeftForDeleteQuery;
	}

	/**
	 * 
	 * @param updateLeftForDeleteQuery
	 */
	public void setUpdateLeftForDeleteQuery(String updateLeftForDeleteQuery) {
		this.updateLeftForDeleteQuery = updateLeftForDeleteQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getUpdateRightForDeleteQuery() {
		return updateRightForDeleteQuery;
	}

	/**
	 * 
	 * @param updateRightForDeleteQuery
	 */
	public void setUpdateRightForDeleteQuery(String updateRightForDeleteQuery) {
		this.updateRightForDeleteQuery = updateRightForDeleteQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getNodeRightForInsertQuery() {
		return nodeRightForInsertQuery;
	}

	/**
	 * 
	 * @param nodeRightForInsertQuery
	 */
	public void setNodeRightForInsertQuery(String nodeRightForInsertQuery) {
		this.nodeRightForInsertQuery = nodeRightForInsertQuery;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSharedNodesQuery() {
		return sharedNodesQuery;
	}

	/**
	 * 
	 * @param sharedNodesQuery
	 */
	public void setSharedNodesQuery(String sharedNodesQuery) {
		this.sharedNodesQuery = sharedNodesQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getNodesBelowQuery() {
		return nodesBelowQuery;
	}

	/**
	 * 
	 * @param nodesBelowQuery
	 */
	public void setNodesBelowQuery(String nodesBelowQuery) {
		this.nodesBelowQuery = nodesBelowQuery;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDepthOfNodeQuery() {
		return depthOfNodeQuery;
	}

	/**
	 * 
	 * @param depthOfNodeQuery
	 */
	public void setDepthOfNodeQuery(String depthOfNodeQuery) {
		this.depthOfNodeQuery = depthOfNodeQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getInsertSharedNodeQuery() {
		return insertSharedNodeQuery;
	}

	/**
	 * 
	 * @param insertSharedNodeQuery
	 */
	public void setInsertSharedNodeQuery(String insertSharedNodeQuery) {
		this.insertSharedNodeQuery = insertSharedNodeQuery;
	}
	
}
