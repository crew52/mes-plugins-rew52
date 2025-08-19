package com.qcadoo.mes.rew52.services;

import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.ContractFields;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.utils.NumberGeneratorService;
import com.qcadoo.view.constants.QcadooViewConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {
    @Autowired
    private NumberGeneratorService numberGeneratorService;

    public void onBeforeRender(final ViewDefinitionState view) {
        generateEmployeeNumber(view);
        updateEndDateField(view);
    }

    public void generateEmployeeNumber(final ViewDefinitionState view) {
        numberGeneratorService.generateAndInsertNumber(view, BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_CONTRACT,
                QcadooViewConstants.L_FORM, ContractFields.NUMBER);
    }

    private void updateEndDateField(final ViewDefinitionState viewDefinitionState) {
        FieldComponent startDateField = (FieldComponent) viewDefinitionState.getComponentByReference(ContractFields.START_DATE);
        FieldComponent endDateField = (FieldComponent) viewDefinitionState.getComponentByReference(ContractFields.END_DATE);

        Object startDateValue = startDateField.getFieldValue();
        boolean hasStartDate = (startDateValue != null && !String.valueOf(startDateValue).trim().isEmpty());
        endDateField.setEnabled(hasStartDate);
        if (!hasStartDate) {
            endDateField.setFieldValue(null);
        }
    }
}
