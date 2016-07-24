package sportszer.api.organization;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("sportszer.api.organization")
public class SportszerapiOrganizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportszerapiOrganizationApplication.class, args);
	}
	
	@Bean
	public Docket orgznizationApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("organization-api")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/v1/organizations.*"))
				.build(); 
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Sportszer Organization API")
				.version("1.0")
				.build();
	}
}
