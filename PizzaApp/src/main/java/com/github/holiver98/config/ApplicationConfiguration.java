package com.github.holiver98.config;

import com.github.holiver98.dal.inmemory.*;
import com.github.holiver98.database.InMemoryDatabase;
import com.github.holiver98.service.inmemory.InMemoryCartService;
import com.github.holiver98.service.inmemory.InMemoryPizzaService;
import com.github.holiver98.service.inmemory.InMemoryRatingService;
import com.github.holiver98.service.inmemory.InMemoryUserService;
import com.github.holiver98.service.jpa.JpaCartService;
import com.github.holiver98.service.jpa.JpaPizzaService;
import com.github.holiver98.service.jpa.JpaRatingService;
import com.github.holiver98.service.jpa.JpaUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.holiver98.service.*;
import org.springframework.context.annotation.Primary;

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

    @Bean
    @Primary
    public IUserService jpaUserService(){
        return new JpaUserService();
    }

    @Bean
    @Primary
    public IRatingService jpaRatingService(){
        return new JpaRatingService();
    }

    @Bean
    @Primary
    public ICartService jpaCartService() { return new JpaCartService(); }

    @Bean
    @Primary
    public IPizzaService jpaPizzaService(){ return new JpaPizzaService(); }
}
