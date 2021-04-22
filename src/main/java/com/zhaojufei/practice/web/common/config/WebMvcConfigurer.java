package com.zhaojufei.practice.web.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.zhaojufei.practice.web.common.validate.ParamsValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter json = (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper mapper = json.getObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.setDateFormat(new DateFormatter());
                // 设置为东八区的时区
                mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                // 设置返回前端的时间为时间戳
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
                mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerialize());
                // 忽略null值
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                break;
            }
        }
    }

    @Override
    public Validator getValidator() {
        return createValidator();
    }

    @Bean
    public ParamsValidator createValidator() {
        return new ParamsValidator();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
    }

    public static class NullKeySerialize extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeFieldName("");
        }
    }
}
