package org.subhamsingh.eventservice.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.subhamsingh.eventservice.dto.CreateEventRequest;

public class EndAfterStartValidator implements ConstraintValidator<EndAfterStart, CreateEventRequest> {

    @Override
    public boolean isValid(CreateEventRequest req, ConstraintValidatorContext context) {
        // Let @NotNull report missing values; only compare when both are present.
        if (req.startTime() == null || req.endTime() == null) {
            return true;
        }

        boolean valid = req.endTime().isAfter(req.startTime());

        if (!valid) {
            // Attach the error to the endTime field so it shows up as a field
            // error (consistent with the other validation messages).
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("endTime")
                    .addConstraintViolation();
        }
        return valid;
    }
}
