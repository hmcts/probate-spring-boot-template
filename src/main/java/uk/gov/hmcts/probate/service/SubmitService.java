package uk.gov.hmcts.probate.service;

import uk.gov.hmcts.probate.domain.FormData;
import uk.gov.hmcts.probate.dto.CcdCaseDataDTO;

public interface SubmitService {

    CcdCaseDataDTO submit(FormData formData);
}
