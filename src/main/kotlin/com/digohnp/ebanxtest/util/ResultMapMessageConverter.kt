package com.digohnp.ebanxtest.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter

class ResultMapMessageConverter: HttpMessageConverter<Any> {

    private val supportedMediaTypes = mutableListOf(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return canWrite(clazz, mediaType)
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return !(mediaType != null && !supportedMediaTypes.contains(mediaType))
    }

    override fun getSupportedMediaTypes(): MutableList<MediaType> {
        return supportedMediaTypes
    }

    override fun write(obj: Any, contentType: MediaType?, outputMessage: HttpOutputMessage) {
        if (obj == "OK") {
            outputMessage.body.write(obj.toString().toByteArray())
        } else {
            var response = jacksonObjectMapper().writeValueAsString(obj)
            outputMessage.headers["Content-Type"] = listOf(MediaType.APPLICATION_JSON.toString())

            response = response.replace(":{", ": {").replace(",", ", ")
            outputMessage.body.write(response.toByteArray())
        }
    }

    override fun read(clazz: Class<out Any>, inputMessage: HttpInputMessage) { }
}