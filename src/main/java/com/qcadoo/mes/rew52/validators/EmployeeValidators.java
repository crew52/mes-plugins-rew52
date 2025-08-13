package com.qcadoo.mes.rew52.validators;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class EmployeeValidators {
    public static final String ERROR_AGE = "rew52.employee.dateOfBirth.invalidAge";

    public boolean validateAgeBetween18And35(final DataDefinition dataDefinition,
                                             final FieldDefinition fieldDefinition,
                                             final Entity entity,
                                             final Object oldValue,
                                             final Object newValue) {
        Date dateOfBirth = (Date) newValue;

        if (!DateRangeUtils.isAgeBetween(dateOfBirth, 18, 35)) {
            entity.addError(fieldDefinition, ERROR_AGE);
            return false;
        }
        return true;
    }

}
