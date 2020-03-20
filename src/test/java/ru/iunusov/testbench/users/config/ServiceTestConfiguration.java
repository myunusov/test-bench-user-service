package ru.iunusov.testbench.users.config;

import lombok.SneakyThrows;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockFileDatabase;
import org.jooq.tools.jdbc.MockFileDatabaseConfiguration;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.io.InputStream;

@TestConfiguration
public class ServiceTestConfiguration {

  @Bean
  @Scope("prototype")
  @SneakyThrows
  public MockConnection connection() {
    final MockFileDatabase provider;
    try (final InputStream stream = ServiceTestConfiguration.class.getResourceAsStream("/mock/users_db.txt")) {
      provider = new MockFileDatabase(new MockFileDatabaseConfiguration()
              .source(stream)
              .patterns(true)
      );
    }
    return new MockConnection(provider);
  }

  @Bean
  @SneakyThrows
  public DataSource dataSource() {
    final DataSource dataSource = Mockito.mock(DataSource.class);
    Mockito.when(dataSource.getConnection()).thenAnswer(invocation -> connection());
    return dataSource;
  }
}
