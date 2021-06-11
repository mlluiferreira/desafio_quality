package com.ml.imobiliaria.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@ReportAsSingleViolation
@Pattern(regexp = "^[A-Z].*")
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface FirstLetterIsUpperCase {
    String message() default "{com.ml.imobiliaria.annotation.FirstLetterIsUpperCase}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
