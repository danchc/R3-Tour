package it.uniroma3.siw.r3tour.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/* VARIABILE ADMIN */

import java.io.IOException;
import java.util.Map;

import static it.uniroma3.siw.r3tour.spring.model.Credentials.RUOLO_ADMIN;
import static it.uniroma3.siw.r3tour.spring.model.Credentials.RUOLO_DEFAULT;


@Configuration
@EnableWebSecurity
//@EnableOAuth2Client
public class AuthConfiguration extends WebSecurityConfigurerAdapter {


    /**
     * La variabile dataSource serve per accedere direttamente ai dati all'interno del DB.
     */
    @Autowired
    //DataSource dataSource;

    /**
     * Il metodo gestisce le varie autorizzazioni che possono riguardare un utente normale (con ruolo DEFAULT)
     * e un super utente (con ruolo ADMIN). Gestisce inoltre il logout.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http// authorization paragraph: qui definiamo chi può accedere a cosa
                .antMatcher("/").authorizeRequests().anyRequest().permitAll();

    }

    /**
     * This method provides the SQL queries to get username and password.
     */


    /**
     * Questo metodo si occupa di effettuare l'encrypt e il decrypt della password dell'utente salvata nel
     * database.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
