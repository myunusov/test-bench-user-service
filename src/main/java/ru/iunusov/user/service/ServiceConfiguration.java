package ru.iunusov.user.service;

import javax.sql.DataSource;

import org.jooq.ExecuteListener;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class ServiceConfiguration {

  @Bean
  public DataSourceConnectionProvider connectionProvider(final DataSource dataSource) {
    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  }

  @Bean
  public DefaultDSLContext dsl(final DataSource dataSource) {
    return new DefaultDSLContext(configuration(dataSource));
  }

  public DefaultConfiguration configuration(final DataSource dataSource) {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider(dataSource));
//    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
    return jooqConfiguration;
  }

  private ExecuteListener exceptionTransformer() {
    return null;
  }
}
