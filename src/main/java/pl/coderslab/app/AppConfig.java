package pl.coderslab.app;


import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.coderslab.converter.AuthorConverter;
import pl.coderslab.converter.PublisherConverter;

//DispatcherServlet Context: defines this servlet's request-processing infrastructure 
/*Enables the Spring MVC @Controller programming model
<annotation-driven />
*/
@Configuration
@EnableJpaRepositories(basePackages	= "pl.coderslab.app.repository") // lub @EnableJpaRepositories(basePackageClasses	=	PersonRepository.class) lub <jpa:repositories	base-package="pl.coderslab.repository"	/>
@EnableWebMvc
@ComponentScan(basePackages = "pl.coderslab.app")
@EnableTransactionManagement
public class AppConfig extends WebMvcConfigurerAdapter {
	
	
	//////////////////// NEW IN WEEK 6 ////////////////////////////////////

	@Bean(name = "localeResolver")
	public LocaleContextResolver getLocaleContextResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver(); // informacje będą trzymane	w sesji.
		localeResolver.setDefaultLocale(new Locale("pl", "PL")); // Set a default Locale that this resolver will return if no other locale found.
		return localeResolver;
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean(); // Spring implementation of interface javax.validation.Validator
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(getAuthorConverter());
		registry.addConverter(getPublisherConverter());
	}
	
	/*
	https://docs.spring.io/spring/docs/current/spring-framework-
		reference/html/validation.html#format-Formatter-SPI
    */
	/*
	https://docs.spring.io/spring/docs/current/sp
		ring-framework-
		reference/html/validation.html#core-
		convert*/
	
	@Bean
	public AuthorConverter getAuthorConverter() {
		return new AuthorConverter();
	}
	
	@Bean
	public PublisherConverter getPublisherConverter() {
		return new PublisherConverter();
	}
	/*
	Więcej	na	ten	temat:
		https://docs.spring.io/spring/docs/current/sp
		ring-framework-
		reference/html/validation.html#core-
		convert-ConverterFactory-SPI
		https://digitaljoel.nerd-
		herders.com/2011/06/15/spring-
		converterfactory-implementation/*/
	
	/*Warto	również	zapoznać	się	z	artykułem:
		http://springinpractice.com/2012/01/07/ma
		king-formselect-work-nicely-using-spring-3-
		formatters/*/
	
	
	//------------------------------------
	
	
	/*
	LocalEntityManagerFactoryBean	to 	podstawowa	implementacja,	którą 	udostępnia	Spring.
	Dzięki	takiej	konfiguracji	będziemy	mogli 	użyć	wstrzykiwania	zależności	aby
	pozyskać	instancję	obiektu 	EntityManager.
	Obiektu	EntityManager	będziemy	używać w	celu	wykonywania	operacji	których
	wyniki	będą	zapisywane	w	bazie	danych.
	*/
	
	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory() {
	    LocalEntityManagerFactoryBean emfb =
	        new LocalEntityManagerFactoryBean();
	    emfb.setPersistenceUnitName("bookstorePersistenceUnit");
	    return emfb;
	}
	 
	@Bean //  włączamy zarządzanie	transakcjami przez Springa
	public JpaTransactionManager transactionManager(
	                            EntityManagerFactory emf) {
	    JpaTransactionManager tm = new JpaTransactionManager(emf);
	    return tm;
	}
	
	///////////////////////////////// OLD STUFF ////////////////////////////////////////
	
	/* ---------------------------------------------------------------------
	<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
	<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<beans:property name="prefix" value="/WEB-INF/views/" />
		<beans:property name="suffix" value=".jsp" />
	</beans:bean>
	*/
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	/*
	 Handles HTTP GET requests for /resources*//** by efficiently serving up static resources in the ${webappRoot}/resources directory
	<resources mapping="/resources/**" location="/resources/" />
	  */
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
