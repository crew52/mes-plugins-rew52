package com.qcadoo.mes.rew52.hooks;

import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.components.WindowComponent;
import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.api.ribbon.RibbonGroup;
import org.springframework.stereotype.Service;

@Service
public class EmployeesListHooks {
    private static final String L_GRID = "grid";

    private static final String L_WINDOW = "window";

    private static final String L_LABELS = "labels";

    private static final String L_PRINT_EMPLOYEE_LABELS = "printEmployeeLabels";

    public void updateRibbonState(final ViewDefinitionState view) {
        GridComponent employeesGrid = (GridComponent) view.getComponentByReference(L_GRID);

        WindowComponent window = (WindowComponent) view.getComponentByReference(L_WINDOW);
        RibbonGroup labels = window.getRibbon().getGroupByName(L_LABELS);
        RibbonActionItem printEmployeeLabels = labels.getItemByName(L_PRINT_EMPLOYEE_LABELS);

        boolean isEnabled = !employeesGrid.getSelectedEntities().isEmpty();

        printEmployeeLabels.setEnabled(isEnabled);
        printEmployeeLabels.requestUpdate(true);
    }
}
