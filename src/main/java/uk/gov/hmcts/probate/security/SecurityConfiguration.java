package uk.gov.hmcts.probate.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;


@Configuration
@ConditionalOnProperty(name = "s2s.enabled", havingValue = "true", matchIfMissing = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthCheckerServiceOnlyFilter filter;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    public SecurityConfiguration(RequestAuthorizer<Service> serviceRequestAuthorizer,
                                 AuthenticationManager authenticationManager,
                                 AuthenticationExceptionHandler authenticationExceptionHandler) {
        filter =  new AuthCheckerServiceOnlyFilter(serviceRequestAuthorizer);
        filter.setAuthenticationManager(authenticationManager);
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final ProviderManager authenticationManager = (ProviderManager) authenticationManager();
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        filter.setAuthenticationManager(authenticationManager());

        http.exceptionHandling()
            .authenticationEntryPoint(authenticationExceptionHandler);

        http.addFilter(filter)
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .authorizeRequests()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
            .antMatchers("/v2/api-docs").permitAll()
            .anyRequest().authenticated();
    }
}
