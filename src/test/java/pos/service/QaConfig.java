package pos.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import pos.spring.SpringConfig;

@Configuration
@ComponentScan(//
        basePackages = { "pos" }, //
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringConfig.class })//
)
@PropertySources({ //
        @PropertySource(value = "classpath:./pos/test.properties", ignoreResourceNotFound = true) //
})
public class QaConfig {
}