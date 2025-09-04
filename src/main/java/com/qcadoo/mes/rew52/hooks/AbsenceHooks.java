package com.qcadoo.mes.rew52.hooks;

import com.qcadoo.mes.rew52.constants.AbsenceFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AbsenceHooks {

    private static final String START_AFTER_END_ERROR = "rew52.absence.error.startAfterEnd";

    public void onSave(final DataDefinition dataDefinition, final Entity entity) {
        Date startDate = entity.getDateField(AbsenceFields.START_DATE);
        Date endDate = entity.getDateField(AbsenceFields.END_DATE);

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            entity.addError(dataDefinition.getField(AbsenceFields.START_DATE), START_AFTER_END_ERROR);
        }
    }
}
