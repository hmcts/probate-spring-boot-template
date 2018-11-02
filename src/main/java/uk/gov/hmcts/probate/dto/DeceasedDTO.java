package uk.gov.hmcts.probate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class DeceasedDTO implements Serializable{

    @NotNull
    @JsonProperty("dob_date")
    private LocalDate dateOfBirth;

    @NotNull
    @JsonProperty("dod_date")
    private LocalDate dateOfDeath;
}
