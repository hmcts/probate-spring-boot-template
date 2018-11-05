package uk.gov.hmcts.probate.dto.ccd.data;

import lombok.Data;
import uk.gov.hmcts.probate.dto.ccd.data.legalstatement.LegalStatementDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DataDTO {

    private String applicationId;

    private LocalDate applicationSubmittedDate;

    private String deceasedDomicileInEngWales;

    private String ihtFormId;

    private String ihtFormCompletedOnline;

    private String softStop;

    private String registryLocation;

    private String applicationType;

    private Long outsideUKGrantCopies;

    private Long extraCopiesOfGrant;

    private String deceasedAnyOtherNames;

    private Long numberOfExecutors;

    private String willHasCodicils;

    private Long willNumberOfCodicils;

    private String deceasedMarriedAfterWillOrCodicilDate;

    private String deceasedForenames;

    private Long numberOfApplicants;

    private String willAccessOriginal;

    private String primaryApplicantEmailAddress;

    private String primaryApplicantIsApplying;

    private String willExists;

    private String primaryApplicantForenames;

    private String primaryApplicantSurname;

    private String primaryApplicantSameWillName;

    private String primaryApplicantAlias;

    private String primaryApplicantAliasReason;

    private String primaryApplicantOtherReason;

    private String ihtReferenceNumber;

    private String primaryApplicantPhoneNumber;

    private String willLatestCodicilHasDate;

    private String deceasedSurname;

    private LocalDate deceasedDateOfDeath;

    private LocalDate deceasedDateOfBirth;

    private BigDecimal totalFee;

    private BigDecimal ihtGrossValue;

    private BigDecimal ihtNetValue;

    private List<CollectionMemberDTO<AdditionalExecutorDTO>> executorsApplyingForLegalStatement;

    private List<CollectionMemberDTO<AdditionalExecutorDTO>> executorsNotApplyingForLegalStatement;

    private List<CollectionMemberDTO<AliasNameDTO>> deceasedAliasNameList;

    private DeclarationDTO declaration;

    private LegalStatementDTO legalStatement;

    private AddressDTO deceasedAddress;

    private String primaryApplicantAddress;
}
