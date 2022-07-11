package it.uniroma3.siw.r3tour.authentication;

import it.uniroma3.siw.r3tour.oauth.CustomOAuth2User;
import it.uniroma3.siw.r3tour.spring.service.CredentialsService;
import it.uniroma3.siw.r3tour.spring.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/* VARIABILE ADMIN */

import java.io.IOException;

import static it.uniroma3.siw.r3tour.spring.model.Credentials.RUOLO_ADMIN;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class AuthConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    /**
     * La variabile dataSource serve per accedere direttamente ai dati all'interno del DB.
     */
    @Autowired
    DataSource dataSource;

    /**
     * Il metodo gestisce le varie autorizzazioni che possono riguardare un utente normale (con ruolo DEFAULT)
     * e un super utente (con ruolo ADMIN). Gestisce inoltre il logout.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestCache().disable()
                /*definiamo le pagine accessibili da tutti e dall'admin*/
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/terms" , "/login", "/signup",
                        "/css/**", "/images/**", "/destinazioni", "/chisiamo", "/pacchetti/**", "/pacchetto/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)
                .anyRequest().authenticated()

                /* oauth2 */
                /*
                   da qui viene gestita l'autenticazione con OAuth o normalmente
                */
                .and().oauth2Login().loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler((request, response, authentication) -> {

                    CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                    /* vengono stampati in console tutti gli attributi dopo il login */
                    System.out.println("Attributi: " + oauthUser.getAttributes() + " /// ");

                    //viene creato un nuovo utente anche nel database
                    credentialsService.processOAuthPostLogin(oauthUser.getUsername(), oauthUser);
                    response.sendRedirect("/default");

                })

                /* gestione del login */
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                /*gestione logout */
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true).permitAll();
    }

    /**
     * Questo metodo recupera i dati nel DB.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                //use the autowired datasource to access the saved credentials
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
                .usersByUsernameQuery("SELECT username, password, 1 as isEnabled FROM credentials WHERE username=?");
    }

    /**
     * Questo metodo si occupa di effettuare l'encrypt della password dell'utente salvata nel
     * database.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
