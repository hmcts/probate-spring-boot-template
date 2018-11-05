package uk.gov.hmcts.probate.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.probate.domain.FormData;
import uk.gov.hmcts.probate.dto.formdata.FormDataDTO;

@Component
@Mapper(componentModel = "spring")
public interface FormDataMapper {

    FormData mapFormDataDTO(FormDataDTO formDataDTO);
}
