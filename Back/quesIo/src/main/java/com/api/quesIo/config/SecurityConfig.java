package com.api.quesIo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 3. Este Bean configura a cadeia de filtros de segurança.
     * Por padrão, o Spring Security bloqueia TUDO.
     * Este código desabilita a proteção CSRF (necessária para Postman)
     * e permite que todas as requisições (anyRequest().permitAll()) passem.
     *
     * SEM ISSO, você receberia erros 401 ou 403 ao tentar usar sua API.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção contra CSRF (Cross-Site Request Forgery)
                // É comum desabilitar para APIs REST que usam tokens
                .csrf(AbstractHttpConfigurer::disable)

                // Configura as regras de autorização de requisições
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite TODAS as requisições sem autenticação
                );

        // Constrói e retorna a cadeia de filtros de segurança
        return http.build();
    }
}
