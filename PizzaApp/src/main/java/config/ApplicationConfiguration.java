package config;

import dao.IIngredientDao;
import dao.IPizzaDao;
import dao.InMemoryIngredientDao;
import dao.InMemoryPizzaDao;
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
    public IPizzaService pizzaService(){
        return new PizzaService(inMemoryPizzaDao(), inMemoryIngredientDao());
    }
}
