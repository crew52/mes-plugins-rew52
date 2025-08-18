package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.LookupComponent;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsListeners {
    public final void onGenderChange(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        clearLookup(view, EmployeeFields.POSITION);
    }
    public final void onPositionChange(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        clearLookup(view, EmployeeFields.DEPARTMENT);
    }

    private void clearLookup(ViewDefinitionState view, String reference) {
        LookupComponent lookupComponent = (LookupComponent) view.getComponentByReference(reference);
        lookupComponent.setFieldValue(null);
        lookupComponent.requestComponentUpdateState();
    }
}
