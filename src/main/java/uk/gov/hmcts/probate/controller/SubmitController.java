package uk.gov.hmcts.probate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.probate.dto.CcdCaseDataDTO;
import uk.gov.hmcts.probate.dto.formdata.FormDataDTO;
import uk.gov.hmcts.probate.mapper.FormDataMapper;
import uk.gov.hmcts.probate.service.SubmitService;

@Api(tags = {"SubmitController"})
@SwaggerDefinition(tags = {@Tag(name = "SubmitController", description = "Submit API")})
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

    @ApiOperation(value = "Create submission to CCD", notes = "Create submission to CCD")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Submission to CCD created"),
            @ApiResponse(code = 400, message = "Submission to CCD failed"),
            @ApiResponse(code = 422, message = "Invalid or missing attribute")
    })
    @RequestMapping(path = SUBMIT_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CcdCaseDataDTO> submit(@RequestBody FormDataDTO formDataDTO) {
        CcdCaseDataDTO ccdCaseDataDTO = submitService.submit(formDataMapper.mapFormDataDTO(formDataDTO));
        return new ResponseEntity<>(ccdCaseDataDTO, HttpStatus.CREATED);
    }
}
