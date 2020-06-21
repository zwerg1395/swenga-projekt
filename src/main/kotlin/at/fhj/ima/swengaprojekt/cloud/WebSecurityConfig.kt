package at.fhj.ima.swengaprojekt.cloud

import at.fhj.ima.swengaprojekt.cloud.security.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService;

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/createCloudUser").anonymous()
                // you anonymous urls here
                .antMatchers("/register").permitAll()
                .antMatchers("/registerUser").permitAll()
                .antMatchers("/home").permitAll()
                //.antMatchers("/anonymous1").permitAll()
                //.antMatchers("/anonymous2").permitAll()
                //.antMatchers("/anonymous3").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                   .defaultSuccessUrl("/cloud?username=user", true)
                .and()
                .rememberMe().key("uniqueAndSecret").userDetailsService(myUserDetailsService);
    }

}
