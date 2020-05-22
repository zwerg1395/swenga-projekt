package at.fhj.ima.swengaprojekt.cloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter


@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
class EmployeeManagerApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(EmployeeManagerApplication::class.java)
    }

    @Bean
    fun resourceUrlEncodingFilterRegistration(): FilterRegistrationBean<ResourceUrlEncodingFilter> {
        val registration = FilterRegistrationBean<ResourceUrlEncodingFilter>()
        registration.setFilter(ResourceUrlEncodingFilter())
        registration.addUrlPatterns("/*")
        registration.setName("resourceUrlEncodingFilter")
        return registration
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}

fun main(args: Array<String>) {
    runApplication<EmployeeManagerApplication>(*args)
}
