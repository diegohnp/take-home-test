package com.digohnp.ebanxtest

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