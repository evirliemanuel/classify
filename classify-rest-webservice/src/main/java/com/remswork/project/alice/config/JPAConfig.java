package com.remswork.project.alice.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.remswork.project.alice.bean.DataSourcePropertiesBean;

@Configuration
public class JPAConfig {
	
	@Autowired
	private DataSourcePropertiesBean dataSourcePropertiesBean;
	
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.driverClassName(dataSourcePropertiesBean.getDriverClassName())
				.url(dataSourcePropertiesBean.getUrl())
				.username(dataSourcePropertiesBean.getUsername())
				.password(dataSourcePropertiesBean.getPassword())
				.build();
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.remswork.project.alice.model");
		return sessionFactory;
	}
	
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return properties;
	}

}
