package com.wfms.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class NamedJdbcTemplateDao extends BaseDao{

	public NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void initJdbcTemplate(){
		this.jdbcTemplate = new NamedParameterJdbcTemplate(this.getDataSource());
	}
	
	/**
	 * 
	 * @return
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 
	 * @param namedParameterJdbcTemplate
	 */
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
}
