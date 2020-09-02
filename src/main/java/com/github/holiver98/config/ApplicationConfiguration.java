package com.github.holiver98.config;

import com.github.holiver98.dal.inmemory.*;
import com.github.holiver98.service.*;
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
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.annotation.SessionScope;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public IInMemoryIngredientDao inMemoryIngredientDao(){
        return new InMemoryIngredientDao();
    }

    @Bean
    public IInMemoryPizzaDao inMemoryPizzaDao(){
        return new InMemoryPizzaDao();
    }

    @Bean
    public IInMemoryRatingDao inMemoryRatingDao(){return new InMemoryRatingDao();}

    @Bean
    public IInMemoryOrderDao inMemoryOrderDao(){return new InMemoryOrderDao();}

    @Bean
    public IInMemoryUserDao inMemoryUserDao(){return new InMemoryUserDao();}

    @Bean
    public IUserService inMemoryUserService(){
        return new InMemoryUserService(inMemoryUserDao());
    }

    @Bean
    public IMailService mailService(){ return new MailService(); }

    @Bean
    public IPizzaService inMemoryPizzaService(){
        return new InMemoryPizzaService(inMemoryPizzaDao(), inMemoryIngredientDao(), inMemoryUserService());
    }

    @Bean
    @SessionScope
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
        return new JpaRatingService(jpaUserService(), jpaPizzaService());
    }

    @Bean
    @SessionScope
    @Primary
    public ICartService jpaCartService() { return new JpaCartService(jpaUserService(), mailService()); }

    @Bean
    @Primary
    public IPizzaService jpaPizzaService(){ return new JpaPizzaService(jpaUserService()); }

    @Bean
    public ClassLoaderTemplateResolver htmlTemplateResolver() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine htmlTemplateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());

        return templateEngine;
    }
}
