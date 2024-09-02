package com.smartcontactupgrade.smartcontact.validator;
import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file format. Only jpg, png, jpeg, gif are allowed.";
    Class<?>[] groups()default {};
    Class<? extends Payload>[] payload() default {}; 
     
}
