package uk.gov.hmcts.probate.dto.ccd;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {

    private String id;

    private String description;

    private String summary;
}
