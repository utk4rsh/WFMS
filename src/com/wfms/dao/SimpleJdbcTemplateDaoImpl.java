package com.wfms.dao;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public class SimpleJdbcTemplateDaoImpl extends BaseDao{
	public SimpleJdbcTemplate  jdbcTemplate ;
	

	/**
	 * 
	 */
	public void initJdbcTemplate(){
		this.jdbcTemplate = new SimpleJdbcTemplate (this.getDataSource());
	}
	

	/**
	 * 
	 * @return
	 */
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 
	 * @param simpleJdbcTemplate
	 */
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	
}
