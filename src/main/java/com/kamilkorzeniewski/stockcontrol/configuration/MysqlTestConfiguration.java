package com.kamilkorzeniewski.stockcontrol.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Profile("test")
@Configuration
public class MysqlTestConfiguration {
    @Value("${data.init-schema}")
    private  String SAMPLE_DATA;
    @Value("${data.init-data}")
    private String SCHEMA;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        Resource initSchema = new ClassPathResource(SCHEMA);
        Resource dataSource = new ClassPathResource(SAMPLE_DATA);
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataSource);
        populator.addScript(initSchema);
        return populator;
    }
}
