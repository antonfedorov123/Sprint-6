package ru.sber.adressbook.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig (
    private val dataSource: DataSource
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {

        auth.jdbcAuthentication().dataSource(dataSource)
            .authoritiesByUsernameQuery("select username, password, enabled from user where username = ?")
            .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
    }

    @Bean
    fun authenticationProvider(passwordEncoder: PasswordEncoder): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder)

        return authProvider
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/resources/**")
            .and()
            .ignoring().antMatchers("/h2-console/**")
    }

    override fun configure(http: HttpSecurity) {

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/app/**").hasAnyRole("ADMIN", "USER")
            .antMatchers("/api/**").hasAnyRole("ADMIN", "API")
            .antMatchers().permitAll()
            .antMatchers("/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll().successForwardUrl("/app")
            .and()
            .logout().permitAll()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

}
