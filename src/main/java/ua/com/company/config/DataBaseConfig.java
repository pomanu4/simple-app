package ua.com.company.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScans({ @ComponentScan("ua.com.company.daoImpl"), @ComponentScan("ua.com.company.serviceImpl") })
@PropertySource("classpath:/dataBaseProp/database.properties")
public class DataBaseConfig {

	@Autowired
	private Environment env;

	@Bean("dataSource")
	public DataSource getDataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(env.getProperty("db.driver"));
		config.setJdbcUrl(env.getProperty("db.url"));
		config.setUsername(env.getProperty("db.username"));
		config.setPassword(env.getProperty("db.password"));

		return new HikariDataSource(config);
	}

	@Bean("sessionFactory")
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		Properties properties = new Properties();

		properties.put(org.hibernate.cfg.Environment.DIALECT, env.getProperty("db.dialect"));
		properties.put(env.getProperty("db.hbm"), env.getProperty("db.hbm.value"));
		properties.put(env.getProperty("db.showSQL"), env.getProperty("db.showSQL.value"));

		properties.put(env.getProperty("hb.hkr.connTimeout"), env.getProperty("hb.hkr.connTimeout.value"));
		properties.put(env.getProperty("hb.hkr.minId"), env.getProperty("hb.hkr.minId.value"));
		properties.put(env.getProperty("hb.hik.maxPoolSize"), env.getProperty("hb.hik.maxPoolSize.value"));
		properties.put(env.getProperty("hb.hik.idTimeout"), env.getProperty("hb.hik.idTimeout.value"));

		factoryBean.setHibernateProperties(properties);
		factoryBean.setPackagesToScan(env.getProperty("entity.pack"));

		return factoryBean;
	}

	@Bean("transactionManager")
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());

		return transactionManager;
	}

}
