package com.wfms.dao;

import org.springframework.jdbc.core.JdbcTemplate;


public class JdbcTemplateDao extends BaseDao{

	public JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void initJdbcTemplate(){		
		jdbcTemplate = new JdbcTemplate(this.getDataSource());
	}

	/**
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	
	/**
	 * 
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
