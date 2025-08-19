package com.qcadoo.mes.rew52.validators;

import com.qcadoo.mes.rew52.constants.ContractFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ContractValidators {

    public static final String ERROR_END_BEFORE_START = "rew52.contract.endDate.beforeStartDate";
    public static final String ERROR_START_IN_FUTURE = "rew52.contract.startDate.afterToday";
    public static final String ERROR_SIGNED_IN_FUTURE = "rew52.contract.signedDate.afterToday";

    public boolean validateEndDateAfterStartDate(final DataDefinition dataDefinition,
                                                 final FieldDefinition fieldDefinition,
                                                 final Entity entity,
                                                 final Object oldValue,
                                                 final Object newValue) {

        Date startDate = (Date) entity.getField(ContractFields.START_DATE);
        Date endDate = (Date) newValue;

        if (startDate != null && endDate != null && endDate.before(startDate)) {
            entity.addError(fieldDefinition, ERROR_END_BEFORE_START);
            return false;
        }

        return true;
    }

    public boolean validateStartDateNotInFuture(final DataDefinition dataDefinition,
                                                final FieldDefinition fieldDefinition,
                                                final Entity entity,
                                                final Object oldValue,
                                                final Object newValue) {

        Date startDate = (Date) newValue;
        Date today = today();

        if (startDate != null && startDate.after(today)) {
            entity.addError(fieldDefinition, ERROR_START_IN_FUTURE);
            return false;
        }

        return true;
    }

    public boolean validateSignedDateNotInFuture(final DataDefinition dataDefinition,
                                                 final FieldDefinition fieldDefinition,
                                                 final Entity entity,
                                                 final Object oldValue,
                                                 final Object newValue) {

        Date signedDate = (Date) newValue;
        Date today = today();

        if (signedDate != null && signedDate.after(today)) {
            entity.addError(fieldDefinition, ERROR_SIGNED_IN_FUTURE);
            return false;
        }

        return true;
    }

    private Date today() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
