package ru.iunusov.testbench.users.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * The Persistence Context Spring Configuration.
 * see https://www.petrikainulainen.net/programming/jooq/using-jooq-with-spring-configuration/
 * Enable the annotation-driven transaction management by annotating the configuration class
 * with EnableTransactionManagement
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.yaml")
public class PersistenceContext {

  private final Environment env;

  public PersistenceContext(final Environment env) {
    this.env = env;
  }

  /**
   * Configure the DataSource bean.
   * @return The HikariDataSource instance.
   */
  @Bean(destroyMethod = "close")
  public HikariDataSource dataSource() {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
    config.setUsername(env.getRequiredProperty("spring.datasource.username"));
    config.setPassword(env.getRequiredProperty("spring.datasource.password"));
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    return new HikariDataSource(config);
  }

  /**
   * Configure the LazyConnectionDataSourceProxy bean.
   * This bean ensures that the database connection are fetched lazily (i.e. when the first statement is created)
   * @param dataSource The DataSource bean.
   * @return The LazyConnectionDataSourceProxy instance.
   */
  @Bean
  public LazyConnectionDataSourceProxy lazyConnectionDataSource(@Qualifier("dataSource") final DataSource dataSource) {
    return new LazyConnectionDataSourceProxy(dataSource);
  }

  /**
   * Configure the TransactionAwareDataSourceProxy bean. This bean ensures that all JDBC connection are aware of
   * Spring-managed transactions. In other words, JDBC connections participate in thread-bound transactions.
   * @param dataSource The LazyConnectionDataSourceProxy bean.
   * @return The TransactionAwareDataSourceProxy instance.
   */
  @Bean
  public TransactionAwareDataSourceProxy transactionAwareDataSource(final LazyConnectionDataSourceProxy dataSource) {
    return new TransactionAwareDataSourceProxy(dataSource);
  }

  /**
   * Configure the DataSourceTransactionManager bean. We must pass the LazyConnectionDataSourceProxy bean
   * as constructor argument when we create a new DataSourceTransactionManager object.
   * @param dataSource The LazyConnectionDataSourceProxy bean.
   * @return The DataSourceTransactionManager instance.
   */
  @Bean
  public DataSourceTransactionManager transactionManager(final LazyConnectionDataSourceProxy dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  /**
   * Configure the DataSourceConnectionProvider bean. jOOQ will get the used connections from the DataSource
   * given as a constructor argument. We must pass the TransactionAwareDataSourceProxy bean as a constructor
   * argument when we create a new DataSourceConnectionProvider object. This ensures that the queries created by
   * jOOQ participate in Spring-managed transactions.
   * @param dataSource The TransactionAwareDataSourceProxy bean.
   * @return The DataSourceConnectionProvider instance.
   */
  @Bean
  public DataSourceConnectionProvider connectionProvider(final TransactionAwareDataSourceProxy dataSource) {
    return new DataSourceConnectionProvider(dataSource);
  }

  /**
   * Configure the JOOQToSpringExceptionTransformer bean.
   * @return The JOOQToSpringExceptionTransformer instance.
   */
  @Bean
  public JOOQToSpringExceptionTransformer jooqToSpringExceptionTransformer() {
    return new JOOQToSpringExceptionTransformer();
  }

  /**
   * Configure the DefaultConfiguration bean. This class is the default implementation of the
   * Configuration interface, and we can use it to configure jOOQ.
   * We have to configure three things:
   * 1. We have to set the ConnectionProvider which is used to obtain and release database
   * connections.
   * 2. We have to configure the custom execute listeners. In other words, we have to add
   * JOOQToSpringExceptionTransformer bean to the created DefaultConfiguration object. This ensures
   * that the exceptions thrown by jOOQ are transformed into Spring DataAccessExceptions.
   * 3. We have to configure the used SQL dialect.
   *
   * @param connectionProvider The DataSourceConnectionProvider bean.
   * @return The DefaultConfiguration instance.
   */
  @Bean
  public DefaultConfiguration configuration(final DataSourceConnectionProvider connectionProvider) {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider);
    jooqConfiguration.set(new DefaultExecuteListenerProvider(jooqToSpringExceptionTransformer()));
    String sqlDialectName = env.getRequiredProperty("spring.jooq.sql-dialect");
    SQLDialect dialect = SQLDialect.valueOf(sqlDialectName.toUpperCase());
    jooqConfiguration.set(dialect);
    return jooqConfiguration;
  }

  /**
   * Configure the DefaultDSLContext bean. We use this bean when we are creating database queries with jOOQ.
   * @param configuration The DefaultConfiguration bean.
   * @return The DefaultDSLContext instance.
   */
  @Bean
  public DefaultDSLContext dsl(final DefaultConfiguration configuration) {
    return new DefaultDSLContext(configuration);
  }

  public static class JOOQToSpringExceptionTransformer extends DefaultExecuteListener {
    @Override
    public void exception(@NotNull ExecuteContext ctx) {
      SQLDialect dialect = ctx.configuration().dialect();
      SQLExceptionTranslator translator =
          (dialect != null)
              ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
              : new SQLStateSQLExceptionTranslator();

      ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
    }
  }
}
