package uk.gov.hmcts.probate.dto.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.probate.dto.ccd.data.DataDTO;

@Data
public class CaseDTO {

    @JsonProperty(value = "event_token")
    private String eventToken;

    @JsonProperty(value = "ignore_warning")
    private String ignoreWarning;

    private EventDTO event;

    private DataDTO data;
}
