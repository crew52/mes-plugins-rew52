package com.qcadoo.mes.rew52.services;

import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.LookupComponent;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;
import com.qcadoo.view.api.utils.NumberGeneratorService;
import com.qcadoo.view.constants.QcadooViewConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmployeeService {

    private static final String DEPARTMENT_CODE_KEY = "code";
    private static final String DEPARTMENT_CODE_VALUE = "IT";
    private static final String POSITION_NAME = "name";

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    public void onBeforeRender(final ViewDefinitionState view) {
        generateEmployeeNumber(view);
        setFilters(view);
    }

    public void generateEmployeeNumber(final ViewDefinitionState view) {
        numberGeneratorService.generateAndInsertNumber(view, BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_EMPLOYEE,
                QcadooViewConstants.L_FORM, EmployeeFields.NUMBER);
    }

    private void setFilters(final ViewDefinitionState view) {
        LookupComponent position = (LookupComponent) view.getComponentByReference(EmployeeFields.POSITION);
        LookupComponent department = (LookupComponent) view.getComponentByReference(EmployeeFields.DEPARTMENT);
        Entity positionEntity = position.getEntity();

        // Filter cho department based on position entity
        FilterValueHolder departmentFilterValueHolder = department.getFilterValue();

        if (positionEntity != null) {
            String positionName = positionEntity.getStringField(POSITION_NAME);

            if ("Developer".equalsIgnoreCase(positionName)) {
                departmentFilterValueHolder.put(DEPARTMENT_CODE_KEY, DEPARTMENT_CODE_VALUE);
            } else {
                departmentFilterValueHolder.remove(DEPARTMENT_CODE_KEY);
            }
        } else {
            departmentFilterValueHolder.remove(DEPARTMENT_CODE_KEY);
        }

        department.setFilterValue(departmentFilterValueHolder);
    }
}
