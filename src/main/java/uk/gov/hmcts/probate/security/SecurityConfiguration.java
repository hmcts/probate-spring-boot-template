package uk.gov.hmcts.probate.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;


@Configuration
@ConditionalOnProperty(name = "s2s.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthCheckerServiceOnlyFilter authCheckerServiceOnlyFilter;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    public SecurityConfiguration(AuthCheckerServiceOnlyFilter authCheckerServiceOnlyFilter,
                                 AuthenticationExceptionHandler authenticationExceptionHandler) {
        this.authCheckerServiceOnlyFilter = authCheckerServiceOnlyFilter;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final ProviderManager authenticationManager = (ProviderManager) authenticationManager();
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        authCheckerServiceOnlyFilter.setAuthenticationManager(authenticationManager());

//        http.exceptionHandling()
//            .authenticationEntryPoint(authenticationExceptionHandler);

        http.addFilter(authCheckerServiceOnlyFilter)
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .authorizeRequests()
                .requestMatchers(EndpointRequest.to("health", "info")).permitAll()


                .anyRequest()
            .authenticated();
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/swagger-ui.html",
//                "/webjars/springfox-swagger-ui/**",
//                "/swagger-resources/**",
//                "/v2/**",
//                "/health",
//                "/info");
//    }
}

