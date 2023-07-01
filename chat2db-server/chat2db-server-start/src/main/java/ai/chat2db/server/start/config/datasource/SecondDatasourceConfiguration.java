package ai.chat2db.server.start.config.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class SecondDatasourceConfiguration {
    @Bean(name="secondProperties")
    @ConfigurationProperties("spring.datasource.second")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean(name="secondDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource datasource(@Qualifier("secondProperties") DataSourceProperties properties){
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "secondTxManager")
    public DataSourceTransactionManager secondTxManager(DataSource secondDatasource){
        return new DataSourceTransactionManager(secondDatasource);
    }
}
