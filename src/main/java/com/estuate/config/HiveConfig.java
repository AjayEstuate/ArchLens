//package com.estuate.config;
//
//import java.io.IOException;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Scope;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@Configuration
//public class HiveConfig {
//
//	@Value("${hive.connectionURL}")
//	private String hiveConnectionURL ; // = "jdbc:hive2://"+ExternalDBConfig.host+":"+ ExternalDBConfig.port ; 
//
//	@Value("${hive.username}")
//	private String userName; // = ExternalDBConfig.userName;
//
//	@Value("${hive.password}")
//	private String password; // = ExternalDBConfig.password;
//
//
//	public DataSource getHiveDataSource() throws IOException {
//		System.out.println("url------->"+hiveConnectionURL);
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setUrl(this.hiveConnectionURL);
//		dataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
//		dataSource.setUsername(this.userName);
//		dataSource.setPassword(this.password);
//
//		return dataSource;
//	}
//
//	@Lazy
//	@Bean(name = "jdbcTemplate")
//	public JdbcTemplate getJDBCTemplate() throws IOException {
//		return new JdbcTemplate(getHiveDataSource());
//	}
//	
////	public void refreshCustomJdbc() {
////		DataSource ds = (DataSource) getSpringContext().getBean("getHiveDataSource");
////		JdbcTemplate customJdbcTemplate = (JdbcTemplate) getSpringContext().getBean(“customJdbcTemplate”);
////		customJdbcTemplate.setDataSource(ds);
////		}
//}
//
//
//
