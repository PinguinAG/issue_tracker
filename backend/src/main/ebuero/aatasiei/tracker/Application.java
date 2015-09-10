package ebuero.aatasiei.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableWebMvc
@ComponentScan(basePackages = {"ebuero.aatasiei.tracker"})
public class Application extends SpringBootServletInitializer {

    private static final String[] RESOURCE_LOCATIONS = {};

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    // Defined as a nested config to ensure WebMvcConfigurerAdapter is not read when not
    // on the classpath
    @Configuration
    public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!registry.hasMappingForPattern("/webjars/**")) {
                registry.addResourceHandler("/webjars/**").addResourceLocations(
                        "classpath:/META-INF/resources/webjars/");
            }
        }
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

}
