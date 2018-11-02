package uk.gov.hmcts.probate.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

@Data
@JsonRootName(value = "formdata")
public class FormDataDTO implements Serializable {

    @Valid
    private ApplicantDTO applicant;

    @Valid
    private DeceasedDTO deceased;

    @Valid
    private InheritanceTaxDTO iht;
}
