package com.estuate.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class HiveConfig {

	@Value("${hive.connectionURL}")
	private String hiveConnectionURL ; //= "jdbc:hive2://192.168.0.112:1000/ajay_test";
	
	@Value("${hive.username}")
	private String userName;

	@Value("${hive.password}")
	private String password;

	
	public DataSource getHiveDataSource() throws IOException {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(this.hiveConnectionURL);
		dataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
		dataSource.setUsername(this.userName);
		dataSource.setPassword(this.password);
		
		return dataSource;
	}
	
	@Bean(name = "jdbcTemplate")
	public JdbcTemplate getJDBCTemplate() throws IOException {
//		JdbcTemplate jc = new JdbcTemplate(getHiveDataSource());
		return new JdbcTemplate(getHiveDataSource());
//		return jc;
	}
}

