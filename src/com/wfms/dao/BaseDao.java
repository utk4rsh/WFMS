package com.wfms.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;


public class BaseDao {
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}
	
    @Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


}
