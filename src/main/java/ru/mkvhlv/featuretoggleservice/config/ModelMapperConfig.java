package ru.mkvhlv.featuretoggleservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getInstanceModelMapper() {
        return new ModelMapper();
    }
}
