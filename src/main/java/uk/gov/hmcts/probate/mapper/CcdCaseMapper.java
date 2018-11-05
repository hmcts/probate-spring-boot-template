package uk.gov.hmcts.probate.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.probate.domain.FormData;
import uk.gov.hmcts.probate.dto.ccd.CaseDTO;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CcdCaseMapper {

    private static final String PERSONAL_APPLICATION_TYPE = "Personal";
    private static final String ENG_WALES_DOMICILE = "live (domicile) permanently in England or Wales";


    @Mappings({
            @Mapping(target = "data.primaryApplicantForenames", source = "applicant.firstName"),
            @Mapping(target = "data.primaryApplicantSurname", source = "applicant.lastName"),
            @Mapping(target = "data.primaryApplicantAddress", source = "applicant.address"),
            @Mapping(target = "data.applicationType", constant = PERSONAL_APPLICATION_TYPE),

            @Mapping(target = "data.declaration.confirm", source = "applicant.declaration.declaration.confirm"),
            @Mapping(target = "data.declaration.confirmItem1", source = "applicant.declaration.declaration.confirmItem1"),
            @Mapping(target = "data.declaration.confirmItem2", source = "applicant.declaration.declaration.confirmItem2"),
            @Mapping(target = "data.declaration.confirmItem3", source = "applicant.declaration.declaration.confirmItem3"),
            @Mapping(target = "data.declaration.requests", source = "applicant.declaration.declaration.requests"),
            @Mapping(target = "data.declaration.requestsItem1", source = "applicant.declaration.declaration.requestsItem1"),
            @Mapping(target = "data.declaration.requestsItem2", source = "applicant.declaration.declaration.requestsItem2"),
            @Mapping(target = "data.declaration.understand", source = "applicant.declaration.declaration.understand"),
            @Mapping(target = "data.declaration.understandItem1", source = "applicant.declaration.declaration.understandItem1"),
            @Mapping(target = "data.declaration.understandItem2", source = "applicant.declaration.declaration.understandItem2"),
            @Mapping(target = "data.declaration.accept", source = "applicant.declaration.declaration.accept")

    })
    public abstract CaseDTO toCaseDTO(FormData formData);

    @AfterMapping
    public void mapDeceasedDomicileInEngWales(FormData formData, @MappingTarget CaseDTO caseDTO) {
        String domicile = ENG_WALES_DOMICILE.equalsIgnoreCase(formData.getDeceased().getDomicile())  ? "Yes" : "No";
        caseDTO.getData().setDeceasedDomicileInEngWales(domicile);
    }
}
