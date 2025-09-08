package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.mes.basic.imports.services.XlsxImportService;
import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.mes.rew52.imports.employee.EmployeeCellBinderRegistry;
import com.qcadoo.mes.rew52.imports.employee.EmployeeXlsxImportService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriterion;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeesImportListeners {
    @Autowired
    private EmployeeXlsxImportService employeeXlsxImportService;

    @Autowired
    private EmployeeCellBinderRegistry EmployeeCellBinderRegistry;

    public void downloadImportSchema(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        employeeXlsxImportService.downloadImportSchema(view, BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_EMPLOYEE,
                XlsxImportService.L_XLSX);
    }
    public void processImportFile(final ViewDefinitionState view, final ComponentState state, final String[] args)
            throws IOException {
        employeeXlsxImportService.processImportFile(view, EmployeeCellBinderRegistry.getCellBinderRegistry(), true,
                BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_EMPLOYEE, EmployeesImportListeners::createRestrictionForEmployee);
    }

    private static SearchCriterion createRestrictionForEmployee(final Entity employee) {
        return SearchRestrictions.eq(EmployeeFields.NUMBER, employee.getStringField(EmployeeFields.NUMBER));
    }

    public void redirectToLogs(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        employeeXlsxImportService.redirectToLogs(view, BasicConstants.MODEL_EMPLOYEE);
    }

    public void onInputChange(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        employeeXlsxImportService.changeButtonsState(view, false);
    }

}
