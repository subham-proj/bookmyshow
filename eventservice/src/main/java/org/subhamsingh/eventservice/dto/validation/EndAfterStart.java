package org.subhamsingh.eventservice.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class-level constraint: endTime must be strictly after startTime.
 * Applied to a whole object (not a single field) because it compares two fields.
 */
@Documented
@Constraint(validatedBy = EndAfterStartValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface EndAfterStart {

    String message() default "End time must be after start time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
