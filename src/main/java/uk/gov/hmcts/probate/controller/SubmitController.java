package uk.gov.hmcts.probate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitController {

    @RequestMapping(path = "/submit", method = RequestMethod.POST)
    public void submitToCcd(){

    }
}
