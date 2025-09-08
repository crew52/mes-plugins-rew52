package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import org.springframework.stereotype.Service;
import com.beust.jcommander.internal.Lists;
import com.lowagie.text.pdf.Barcode128;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.constants.QcadooViewConstants;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeesListListeners {
    public void openEmployeesImportPage(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        StringBuilder url = new StringBuilder("../page/rew52/employeesImport.html");
        view.openModal(url.toString());
    }
    public void openEmployeesImportPageDemo(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        StringBuilder url = new StringBuilder("../page/rew52/employeesImportDemo.html");
        view.openModal(url.toString());
    }

    public void printEmployeeLabels(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        GridComponent employeesGrid = (GridComponent) view.getComponentByReference(QcadooViewConstants.L_GRID);

        Set<Long> employeeIds = employeesGrid.getSelectedEntitiesIds();

        if (employeeIds.isEmpty()) {
            view.addMessage("rew52.employeeList.error.notSelected", ComponentState.MessageType.INFO);
        } else {
            List<String> invalidCodes = Lists.newArrayList();

            employeesGrid.getSelectedEntities().forEach(employee -> {
                String code = employee.getStringField("code");

                try {
                    Barcode128.getRawText(code, false);
                } catch (RuntimeException exception) {
                    invalidCodes.add(code);
                }
            });

            if (invalidCodes.isEmpty()) {
                String redirectUrl = new StringBuilder("/rew52/employeeLabelsReport.pdf?")
                        .append(employeeIds.stream()
                                .map(id -> "ids=" + id)
                                .collect(Collectors.joining("&")))
                        .toString();

                view.redirectTo(redirectUrl, true, false);
            } else {
                view.addMessage("rew52.employeeList.error.invalidCodes",
                        ComponentState.MessageType.FAILURE,
                        String.join(", ", invalidCodes));
            }
        }
    }
}
