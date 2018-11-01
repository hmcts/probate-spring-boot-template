package uk.gov.hmcts.probate.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Configuration
public class AuthCheckerConfiguration {

    @Value("#{'${authorised.services}'.split(',\\s*')}")
    private List<String> authorisedServices;

    @Bean
    public Function<HttpServletRequest, Collection<String>> authorizedServicesExtractor() {
        return request -> authorisedServices;
    }

    @Bean
    public AuthCheckerServiceOnlyFilter authCheckerServiceOnlyFilter(AuthenticationManager authenticationManager,
                                                                         RequestAuthorizer<Service> serviceRequestAuthorizer){
        AuthCheckerServiceOnlyFilter authCheckerServiceOnlyFilter =  new AuthCheckerServiceOnlyFilter(serviceRequestAuthorizer);
        authCheckerServiceOnlyFilter.setAuthenticationManager(authenticationManager);
        return authCheckerServiceOnlyFilter;
    }
}
