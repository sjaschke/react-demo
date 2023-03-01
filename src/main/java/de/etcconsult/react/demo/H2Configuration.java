package de.etcconsult.react.demo;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.r2dbc.OptionsCapableConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;


@Configuration
@EnableR2dbcRepositories(entityOperationsRef = "configEntityTemplate", basePackages = "de.etc.service.config")
@Slf4j
class H2Configuration {

  @Bean
  @Qualifier("h2config")
  public ConnectionFactory h2ConnectionFactory() {
    var h2UrlStr = "r2dbc:h2:mem:///testDB?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    var defaultConnectionFactory = ConnectionFactories.get(h2UrlStr);
    var config = ConnectionPoolConfiguration
        .builder(defaultConnectionFactory)
        .acquireRetry(2)
        .build();
    return new OptionsCapableConnectionFactory(
        ConnectionFactoryOptions.parse(h2UrlStr),
        new ConnectionPool(config)
    );
  }

  @Bean
  public R2dbcEntityOperations configEntityTemplate(@Qualifier("h2config") ConnectionFactory connectionFactory) {
    DefaultReactiveDataAccessStrategy strategy = new DefaultReactiveDataAccessStrategy(H2Dialect.INSTANCE);
    DatabaseClient databaseClient = DatabaseClient.builder()
        .connectionFactory(connectionFactory)
        .bindMarkers(H2Dialect.INSTANCE.getBindMarkersFactory())
        .build();
    return new R2dbcEntityTemplate(databaseClient, strategy);
  }

}