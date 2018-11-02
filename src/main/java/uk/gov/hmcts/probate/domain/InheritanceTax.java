package uk.gov.hmcts.probate.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InheritanceTax {

    private BigDecimal netValue;

    private BigDecimal grossValue;
}
