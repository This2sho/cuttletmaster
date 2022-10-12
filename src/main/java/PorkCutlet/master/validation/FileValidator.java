package PorkCutlet.master.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Slf4j
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<MultipartFile> file = (List<MultipartFile>) target;

        if (file.get(0).isEmpty()) {
            errors.rejectValue("imageFiles", "emptyFile", "파일을 업로드해주세요.");
        }
    }
}
