package com.github.pasukaru.conf

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/**"))
            //Exclude endpoints from spring
            .paths(Predicates.not(PathSelectors.ant("/auditevents**")))
            .paths(Predicates.not(PathSelectors.ant("/error")))
            .paths(Predicates.not(PathSelectors.ant("/autoconfig**")))
            .paths(Predicates.not(PathSelectors.ant("/beans**")))
            .paths(Predicates.not(PathSelectors.ant("/configprops**")))
            .paths(Predicates.not(PathSelectors.ant("/dump**")))
            .paths(Predicates.not(PathSelectors.ant("/info**")))
            .paths(Predicates.not(PathSelectors.ant("/mappings**")))
            .paths(Predicates.not(PathSelectors.ant("/trace**")))
            .paths(Predicates.not(PathSelectors.ant("/env**")))
            .paths(Predicates.not(PathSelectors.ant("/env/**")))
            .paths(Predicates.not(PathSelectors.ant("/health")))
            .paths(Predicates.not(PathSelectors.ant("/health.json")))
            .paths(Predicates.not(PathSelectors.ant("/heapdump")))
            .paths(Predicates.not(PathSelectors.ant("/heapdump.json")))
            .paths(Predicates.not(PathSelectors.ant("/loggers")))
            .paths(Predicates.not(PathSelectors.ant("/loggers.json")))
            .paths(Predicates.not(PathSelectors.ant("/loggers/**")))
            .paths(Predicates.not(PathSelectors.ant("/metrics.json")))
            .paths(Predicates.not(PathSelectors.ant("/metrics/**")))
            .build()
        /*
            .globalOperationParameters(
                    newArrayList(ParameterBuilder()
                            .name("x-auth-token")
                            .description("Authentication token")
                            .modelRef(ModelRef("string"))
                            .parameterType("header")
                            .build(),
                            ParameterBuilder()
                                    .name("Accept-Language")
                                    .description("Language")
                                    .modelRef(ModelRef("string"))
                                    .parameterType("header")
                                    .defaultValue("en")
                                    .build()))
        */
    }
}