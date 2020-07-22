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
    public IIngredientDao inMemoryIngredientDao(){
        return new InMemoryIngredientDao(inMemoryDatabase());
    }

    @Bean
    public IPizzaDao inMemoryPizzaDao(){
        return new InMemoryPizzaDao(inMemoryDatabase());
    }

    @Bean
    public IRatingDao inMemoryRatingDao(){return new InMemoryRatingDao(inMemoryDatabase());}

    @Bean
    public IOrderDao inMemoryOrderDao(){return new InMemoryOrderDao(inMemoryDatabase());}

    @Bean
    public IUserDao inMemoryUserDao(){return new InMemoryUserDao(inMemoryDatabase());}

    @Bean
    public IUserService userService(){
        return new UserService(inMemoryUserDao());
    }

    @Bean
    public IMailService mailService(){ return new MailService(); }

    @Bean
    public IPizzaService pizzaService(){
        return new PizzaService(inMemoryPizzaDao(), inMemoryIngredientDao(), inMemoryRatingDao());
    }

    @Bean
    public ICartService cartService(){
        return new CartService(userService(), inMemoryOrderDao(), mailService());
    }

    @Bean
    public IRatingService ratingService(){
        return new RatingService(inMemoryRatingDao(), userService(), pizzaService());
    }
}
