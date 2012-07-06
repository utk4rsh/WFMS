package com.wfms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.wfms.dao.JdbcTemplateDao;
import com.wfms.dao.beans.FileBean;
import com.wfms.dao.beans.UserBean;

/**
 * 
 * @author Utkarsh
 *
 */
public class FilesDaoImpl extends JdbcTemplateDao {

	/**
	 * String Literals:
	 */

	private static final String FILE_ID = "file_id";
	private static final String NODE_ID = "node_id";
	private static final String FILE_NAME = "file_name";
	private static final String FILE_DESC = "file_desc";
	private static final String FILE_TYPE = "file_type";
	private static final String FILE_CONTENT = "file_content";
	private static final String LAST_MODIFIED = "last_modified";
	private static final String USER_ID = "user_id";
	private static final String USERNAME = "username";
	private static final String OWNER_NAME = "owner_name";
	/**
	 * Query Strings:
	 */
	private String saveFileQuery;
	private String listFilesQuery;
	private String deleteFileQuery;
	private String downloadFileQuery;
	private String usersQuery;

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FileBean find(int id) {
		FileBean file = this.getJdbcTemplate().queryForObject(downloadFileQuery, new Object[] {id},	new RowMapper<FileBean>() {
			public FileBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				FileBean bean = new FileBean();
				bean.setFileId(rs.getInt(FILE_ID));
				bean.setNodeId(rs.getInt(NODE_ID));
				bean.setFilename(rs.getString(FILE_NAME));
				bean.setFileDesccription(rs.getString(FILE_DESC));
				bean.setFileType(rs.getString(FILE_TYPE));
				bean.setFileContent(rs.getBytes(FILE_CONTENT));
				bean.setLastModified(rs.getString(LAST_MODIFIED));
				return bean;
			}
		});
		return file;
	}

	/**
	 * 
	 * @param nodeId
	 * @return
	 */
	public List<FileBean> listAll(int nodeId) {
		List<FileBean> fileBeans = this.getJdbcTemplate().query(listFilesQuery, new Object[]{nodeId}, new RowMapper<FileBean>() {
			public FileBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				FileBean bean = new FileBean();
				bean.setFileId(rs.getInt(FILE_ID));
				bean.setNodeId(rs.getInt(NODE_ID));
				bean.setFilename(rs.getString(FILE_NAME));
				bean.setFileDesccription(rs.getString(FILE_DESC));
				bean.setFileType(rs.getString(FILE_TYPE));
				bean.setFileContent(rs.getBytes(FILE_CONTENT));
				bean.setLastModified(rs.getString(LAST_MODIFIED));
				bean.setOwnerName(rs.getString(OWNER_NAME));
				return bean;
			}
		});
		return fileBeans;
	}

	/**
	 * 
	 * @param file
	 */
	public void save(final FileBean file) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final Date date = new Date();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(saveFileQuery);
				statement.setInt(1, file.getNodeId());
				statement.setString(2, file.getFilename());
				statement.setString(3, file.getFileDesccription());
				statement.setString(4, file.getFileType());
				statement.setBytes(5, file.getFileContent());
				statement.setString(6, dateFormat.format(date).toString());
				statement.setString(7,file.getOwnerName());
				return statement;
			}
		});
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(int id) {
		this.getJdbcTemplate().update(deleteFileQuery, new Object[] {id});
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserBean> getUsers(){
		List<UserBean> userBeans = this.jdbcTemplate.query(usersQuery, new RowMapper<UserBean>() {
			public UserBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				UserBean bean = new UserBean();
				bean.setUserId(rs.getInt(USER_ID));
				bean.setUserName(rs.getString(USERNAME));
				return bean;
			}
		});
		return userBeans;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSaveFileQuery() {
		return saveFileQuery;
	}

	/**
	 * 
	 * @param saveFileQuery
	 */
	public void setSaveFileQuery(String saveFileQuery) {
		this.saveFileQuery = saveFileQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getListFilesQuery() {
		return listFilesQuery;
	}

	/**
	 * 
	 * @param listFilesQuery
	 */
	public void setListFilesQuery(String listFilesQuery) {
		this.listFilesQuery = listFilesQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getDeleteFileQuery() {
		return deleteFileQuery;
	}

	/**
	 * 
	 * @param deleteFileQuery
	 */
	public void setDeleteFileQuery(String deleteFileQuery) {
		this.deleteFileQuery = deleteFileQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getDownloadFileQuery() {
		return downloadFileQuery;
	}

	/**
	 * 
	 * @param downloadFileQuery
	 */
	public void setDownloadFileQuery(String downloadFileQuery) {
		this.downloadFileQuery = downloadFileQuery;
	}

	/**
	 * 
	 * @return
	 */
	public String getUsersQuery() {
		return usersQuery;
	}

	/**
	 * 
	 * @param usersQuery
	 */
	public void setUsersQuery(String usersQuery) {
		this.usersQuery = usersQuery;
	}
	
}
