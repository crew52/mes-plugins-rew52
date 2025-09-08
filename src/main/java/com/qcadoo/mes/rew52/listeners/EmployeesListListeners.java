package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import org.springframework.stereotype.Service;

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
}
