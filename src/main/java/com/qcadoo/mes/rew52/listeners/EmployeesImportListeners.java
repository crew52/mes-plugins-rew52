package com.qcadoo.mes.rew52.listeners;

import com.qcadoo.mes.basic.imports.services.XlsxImportService;
import com.qcadoo.mes.rew52.constants.BasicConstants;
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
    private static final String EMPLOYEE_FIELD_NUMBER = "number";

    @Autowired
    private EmployeeXlsxImportService employeeXlsxImportService;

    public void downloadImportSchema(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        employeeXlsxImportService.downloadImportSchema(view, BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_EMPLOYEE,
                XlsxImportService.L_XLSX);
    }

}
