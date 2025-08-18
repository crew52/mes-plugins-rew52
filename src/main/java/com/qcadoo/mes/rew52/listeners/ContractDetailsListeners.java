package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.mes.rew52.constants.ContractFields;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import org.springframework.stereotype.Service;

@Service
public class ContractDetailsListeners {

    public final void onStartDateChange(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        clearField(view, ContractFields.END_DATE);
    }

    private void clearField(ViewDefinitionState view, String reference) {
        FieldComponent fieldComponent = (FieldComponent) view.getComponentByReference(reference);
        fieldComponent.setFieldValue(null);
        fieldComponent.requestComponentUpdateState();
    }
}
