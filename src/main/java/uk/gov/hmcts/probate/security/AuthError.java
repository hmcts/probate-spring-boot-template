package uk.gov.hmcts.probate.security;

import lombok.Data;

@Data
public class AuthError {
    private final int code;
    private final String message;
}
