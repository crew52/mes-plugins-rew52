package com.qcadoo.mes.rew52.imports.employee;

import com.qcadoo.mes.basic.imports.dtos.CellBinderRegistry;
import com.qcadoo.mes.basic.imports.helpers.CellParser;
import com.qcadoo.mes.rew52.constants.EmployeeFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.qcadoo.mes.basic.imports.dtos.CellBinder.optional;
import static com.qcadoo.mes.basic.imports.dtos.CellBinder.required;

import javax.annotation.PostConstruct;

@Component
public class EmployeeCellBinderRegistry {
    private CellBinderRegistry cellBinderRegistry = new CellBinderRegistry();

    @Autowired
    private CellParser departmentCellParser;

    @Autowired
    private CellParser positionCellParser;

    @PostConstruct
    private void init() {
        cellBinderRegistry.setCellBinder(required(EmployeeFields.NUMBER));
        cellBinderRegistry.setCellBinder(required(EmployeeFields.FIRST_NAME));
        cellBinderRegistry.setCellBinder(required(EmployeeFields.LAST_NAME));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.GENDER));
        cellBinderRegistry.setCellBinder(required(EmployeeFields.DATE_OF_BIRTH));
        cellBinderRegistry.setCellBinder(required(EmployeeFields.CITIZEN_ID));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.EMAIL));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.PHONE));
        cellBinderRegistry.setCellBinder(required(EmployeeFields.HIRE_DATE));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.STATUS));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.NOTES));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.POSITION, positionCellParser));
        cellBinderRegistry.setCellBinder(optional(EmployeeFields.DEPARTMENT, departmentCellParser));
    }

    public CellBinderRegistry getCellBinderRegistry() {
        return cellBinderRegistry;
    }
}

