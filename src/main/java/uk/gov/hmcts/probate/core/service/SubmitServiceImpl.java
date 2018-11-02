package uk.gov.hmcts.probate.core.service;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.probate.domain.FormData;
import uk.gov.hmcts.probate.dto.CcdCaseDataDTO;
import uk.gov.hmcts.probate.service.SubmitService;

@Component
public class SubmitServiceImpl implements SubmitService {

    @Override
    public CcdCaseDataDTO submit(FormData formData) {
        return null;
    }
}
