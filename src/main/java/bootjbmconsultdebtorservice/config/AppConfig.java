package bootjbmconsultdebtorservice.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Primary
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("postgresql.datasource.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("postgresql.datasource.url"));
        dataSource.setUsername(env.getProperty("postgresql.datasource.username"));
        dataSource.setPassword(env.getProperty("postgresql.datasource.password"));
 
        return dataSource;
    }
    
	@Primary
	@Bean
	public JdbcTemplate auditJdbcTemplate() {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}

}
