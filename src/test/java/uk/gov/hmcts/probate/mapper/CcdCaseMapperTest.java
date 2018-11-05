package uk.gov.hmcts.probate.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import uk.gov.hmcts.probate.domain.Applicant;
import uk.gov.hmcts.probate.domain.Deceased;
import uk.gov.hmcts.probate.domain.FormData;
import uk.gov.hmcts.probate.domain.InheritanceTax;
import uk.gov.hmcts.probate.dto.CcdCaseDataDTO;
import uk.gov.hmcts.probate.dto.ccd.CaseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class CcdCaseMapperTest {

    private static final String APPLICANT_FIRST_NAME = "Rob";
    private static final String APPLICANT_LAST_NAME = "Stark";
    private static final String APPLICANT_ADDRESS = "Winterfell, Westeros";

    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1950, 1, 1);
    private static final LocalDate DATE_OF_DEATH = LocalDate.of(2018, 1, 1);
    private static final BigDecimal GROSS_VALUE = BigDecimal.valueOf(200000);
    private static final BigDecimal NET_VALUE = BigDecimal.valueOf(100000);

    private CcdCaseMapper ccdCaseMapper;

    private FormData formData;

    @Before
    public void setUp(){
        setUpDomain();
        ccdCaseMapper = Mappers.getMapper(CcdCaseMapper.class);
    }

    private void setUpDomain() {
        Applicant applicant = new Applicant();
        applicant.setAddress(APPLICANT_ADDRESS);
        applicant.setFirstName(APPLICANT_FIRST_NAME);
        applicant.setLastName(APPLICANT_LAST_NAME);

        Deceased deceased = new Deceased();
        deceased.setDateOfBirth(DATE_OF_BIRTH);
        deceased.setDateOfDeath(DATE_OF_DEATH);
        deceased.setDomicile("live (domicile) permanently in England or Wales");

        InheritanceTax inheritanceTax = new InheritanceTax();
        inheritanceTax.setGrossValue(GROSS_VALUE);
        inheritanceTax.setNetValue(NET_VALUE);

        formData = new FormData();
        formData.setDeceased(deceased);
        formData.setApplicant(applicant);
        formData.setIht(inheritanceTax);
    }

    @Test
    public void toCaseDTO() {
        CaseDTO caseDTO = ccdCaseMapper.toCaseDTO(formData);
    }
}