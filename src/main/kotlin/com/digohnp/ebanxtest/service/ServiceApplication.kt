package com.digohnp.ebanxtest.service

import com.digohnp.ebanxtest.util.ResultMapMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceApplication {

    @Bean
    fun resultMapMessageConverter(): ResultMapMessageConverter {
        return ResultMapMessageConverter()
    }
}