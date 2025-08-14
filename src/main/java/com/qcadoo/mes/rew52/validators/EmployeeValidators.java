package com.qcadoo.mes.rew52.validators;

import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class EmployeeValidators {
    public static final String ERROR_AGE = "rew52.employee.dateOfBirth.invalidAge";
    public static final String ERROR_HIRE_DATE = "rew52.employee.hireDate.invalidRange";

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

    public boolean validateHireDateAfter18AndBeforeFuture1Month(final DataDefinition dataDefinition,
                                                                final FieldDefinition fieldDefinition,
                                                                final Entity entity,
                                                                final Object oldValue,
                                                                final Object newValue) {
        Date hireDate = (Date) newValue;
        Date dateOfBirth = (Date) entity.getField(EmployeeFields.DATE_OF_BIRTH);

        if (hireDate == null || dateOfBirth == null) {
            entity.addError(fieldDefinition, ERROR_HIRE_DATE);
            return false;
        }

        Calendar minAllowedDate = Calendar.getInstance();
        minAllowedDate.setTime(dateOfBirth);
        minAllowedDate.add(Calendar.YEAR, 18);

        Calendar maxAllowedDate = Calendar.getInstance();
        maxAllowedDate.add(Calendar.MONTH, 1); 
        boolean valid = !(hireDate.before(minAllowedDate.getTime()) || hireDate.after(maxAllowedDate.getTime()));
        if (!valid) {
            entity.addError(fieldDefinition, ERROR_HIRE_DATE);
            return false;
        }
        return true;
    }

}
