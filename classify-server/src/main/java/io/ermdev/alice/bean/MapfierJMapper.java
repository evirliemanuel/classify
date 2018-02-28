package io.ermdev.alice.bean;

import mapfierj.ModelMapper;
import mapfierj.SimpleMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapfierJMapper {

    @Bean
    public SimpleMapper simpleMapper() {
        return new SimpleMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
