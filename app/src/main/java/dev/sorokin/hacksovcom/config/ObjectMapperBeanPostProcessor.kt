package dev.sorokin.hacksovcom.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class ObjectMapperBeanPostProcessor: BeanPostProcessor {

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if(bean.javaClass == ObjectMapper::class.java) {
            val objectMapper = bean as ObjectMapper
            objectMapper.registerModule(KotlinModule.Builder().build())
        }
        return super.postProcessAfterInitialization(bean, beanName)
    }
}