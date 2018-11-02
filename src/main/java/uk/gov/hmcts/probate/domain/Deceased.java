package uk.gov.hmcts.probate.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Deceased {

    private LocalDate dateOfBirth;

    private LocalDate dateOfDeath;
}
