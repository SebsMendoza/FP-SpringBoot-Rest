package es.mendoza.fpspringbootrest.config.security;

import es.mendoza.fpspringbootrest.config.APIConfig;
import es.mendoza.fpspringbootrest.config.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //Permisos Usuarios
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/usuarios/**").permitAll()
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/usuarios/**").hasAnyRole("USER", "ADMIN")
                //Permisos Curso
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/curso/**").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/curso/**").permitAll()
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/curso/**").permitAll()
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/curso/**").permitAll()
                //Permisos Módulo
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/modulo/**").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/modulo/**").permitAll()
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/modulo/**").permitAll()
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/modulo/**").permitAll()
                //Permisos Alumno
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/alumno/**").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/alumno/**").permitAll()
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/alumno/**").permitAll()
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/alumno/**").permitAll()
                //Permisos Calificación
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/calificacion/**").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/calificacion/**").permitAll()
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/calificacion/**").permitAll()
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/calificacion/**").permitAll()
                //Permisos files
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/files/**").permitAll()
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/files/**").permitAll()
                //Permisos Auth Curso
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/auth/curso/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/auth/curso/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/auth/curso/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/auth/curso/**").hasRole("ADMIN")
                //Permisos Auth Modulo
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/auth/modulo/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/auth/modulo/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/auth/modulo/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/auth/modulo/**").hasRole("ADMIN")
                //Permisos Auth Alumno
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/auth/alumno/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/auth/alumno/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/auth/alumno/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/auth/alumno/**").hasRole("ADMIN")
                //Permisos Auth Calificacion
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/auth/calificacion/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/auth/calificacion/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/auth/calificacion/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/auth/calificacion/**").hasRole("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}