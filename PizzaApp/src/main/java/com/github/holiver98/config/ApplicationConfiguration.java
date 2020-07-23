package com.github.holiver98.config;

import com.github.holiver98.dao.*;
import com.github.holiver98.database.InMemoryDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.holiver98.service.*;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public InMemoryDatabase inMemoryDatabase(){
        return new InMemoryDatabase();
    }

    @Bean
    public IInMemoryIngredientDao inMemoryIngredientDao(){
        return new InMemoryIngredientDao(inMemoryDatabase());
    }

    @Bean
    public IInMemoryPizzaDao inMemoryPizzaDao(){
        return new InMemoryPizzaDao(inMemoryDatabase());
    }

    @Bean
    public IInMemoryRatingDao inMemoryRatingDao(){return new InMemoryRatingDao(inMemoryDatabase());}

    @Bean
    public IInMemoryOrderDao inMemoryOrderDao(){return new InMemoryOrderDao(inMemoryDatabase());}

    @Bean
    public IInMemoryUserDao inMemoryUserDao(){return new InMemoryUserDao(inMemoryDatabase());}

    @Bean
    public IUserService inMemoryUserService(){
        return new InMemoryUserService(inMemoryUserDao());
    }

    @Bean
    public IMailService mailService(){ return new MailService(); }

    @Bean
    public IPizzaService inMemoryPizzaService(){
        return new InMemoryPizzaService(inMemoryPizzaDao(), inMemoryIngredientDao(), inMemoryRatingDao());
    }

    @Bean
    public ICartService inMemoryCartService(){
        return new InMemoryCartService(inMemoryUserService(), inMemoryOrderDao(), mailService());
    }

    @Bean
    public IRatingService inMemoryRatingService(){
        return new InMemoryRatingService(inMemoryRatingDao(), inMemoryUserService(), inMemoryPizzaService());
    }
}
