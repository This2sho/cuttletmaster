package PorkCutlet.master.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List file = (List) target;
        if (file.isEmpty() || file.size() < 1) {
            errors.rejectValue("imageFile","emptyFile", "파일을 업로드해주세요.");
        }
    }
}
