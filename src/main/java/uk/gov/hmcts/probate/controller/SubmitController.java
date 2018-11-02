package uk.gov.hmcts.probate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.probate.dto.CcdCaseDataDTO;
import uk.gov.hmcts.probate.dto.FormDataDTO;
import uk.gov.hmcts.probate.mapper.FormDataMapper;
import uk.gov.hmcts.probate.service.SubmitService;

@RestController
public class SubmitController {

    private static final String SUBMIT_URL = "/submit";

    private final SubmitService submitService;

    private final FormDataMapper formDataMapper;

    @Autowired
    private SubmitController(SubmitService submitService, FormDataMapper formDataMapper) {
        this.submitService = submitService;
        this.formDataMapper = formDataMapper;
    }

    @RequestMapping(path = SUBMIT_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CcdCaseDataDTO submit(@RequestBody FormDataDTO formDataDTO) {
        return submitService.submit(formDataMapper.toFormData(formDataDTO));
    }
}
