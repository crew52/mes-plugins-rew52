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
    public static final String ERROR_SIGNED_AFTER_START = "rew52.contract.signedDate.afterStartDate";

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

    public boolean validateSignedDateBeforeStartDate(final DataDefinition dataDefinition,
                                                     final FieldDefinition fieldDefinition,
                                                     final Entity entity,
                                                     final Object oldValue,
                                                     final Object newValue) {

        Date signedDate = (Date) newValue;
        Date startDate = (Date) entity.getField(ContractFields.START_DATE);

        if (signedDate != null && startDate != null && signedDate.after(startDate)) {
            entity.addError(fieldDefinition, ERROR_SIGNED_AFTER_START);
            return false;
        }

        return true;
    }
}
