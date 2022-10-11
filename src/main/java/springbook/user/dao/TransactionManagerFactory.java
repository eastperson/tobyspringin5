package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerFactory {

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager();
    }
}
