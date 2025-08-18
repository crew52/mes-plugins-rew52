package com.qcadoo.mes.rew52.services;

import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
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
    private static final String POSITION_KEY = "notName";
    private static final String POSITION_KEY_VALUE_HR = "HR";
    private static final String POSITION_KEY_VALUE_DEVELOPER = "Developer";

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
        FieldComponent gender = (FieldComponent) view.getComponentByReference(EmployeeFields.GENDER);
        LookupComponent position = (LookupComponent) view.getComponentByReference(EmployeeFields.POSITION);
        LookupComponent department = (LookupComponent) view.getComponentByReference(EmployeeFields.DEPARTMENT);

        Entity positionEntity = position.getEntity();

        Object genderValue = gender.getFieldValue();

        // Filter cho position
        FilterValueHolder positionFilterValueHolder = position.getFilterValue();
        if (EmployeeFields.GENDER_ENUM_FEMALE.equalsIgnoreCase(String.valueOf(genderValue))) {
            positionFilterValueHolder.put(POSITION_KEY, POSITION_KEY_VALUE_DEVELOPER);
        } else if (EmployeeFields.GENDER_ENUM_MALE.equalsIgnoreCase(String.valueOf(genderValue))) {
            positionFilterValueHolder.put(POSITION_KEY, POSITION_KEY_VALUE_HR);
        }else {
            if (positionFilterValueHolder.has(POSITION_KEY)) {
                positionFilterValueHolder.remove(POSITION_KEY);
            }
        }
        position.setFilterValue(positionFilterValueHolder);

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
