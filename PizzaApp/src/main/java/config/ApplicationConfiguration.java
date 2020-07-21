package config;

import dao.*;
import database.InMemoryDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.IPizzaService;
import service.IRatingService;
import service.PizzaService;
import service.RatingService;

@Configuration
public class ApplicationConfiguration {

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
    public IPizzaService pizzaService(){
        return new PizzaService(inMemoryPizzaDao(), inMemoryIngredientDao(), inMemoryRatingDao());
    }
}
