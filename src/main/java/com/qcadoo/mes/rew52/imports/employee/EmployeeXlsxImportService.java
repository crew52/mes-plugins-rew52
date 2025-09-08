package com.qcadoo.mes.rew52.imports.employee;

import com.qcadoo.mes.basic.imports.services.XlsxImportService;
import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeXlsxImportService extends XlsxImportService {
    private static final String L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_CUSTOM = "qcadooView.validate.field.error.custom";
    @Override
    public void validateEntity(final Entity employee, final DataDefinition employeeDD) {
        // Nếu người dùng nhập giá trị ở file Excel cho position
        Object positionRaw = employee.getField(EmployeeFields.POSITION);
        Entity position = employee.getBelongsToField(EmployeeFields.POSITION);
        if (positionRaw != null && position == null) {
            employee.addError(employeeDD.getField(EmployeeFields.POSITION),
                    L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_CUSTOM);
        }

        // Nếu người dùng nhập giá trị ở file Excel cho department
        Object departmentRaw = employee.getField(EmployeeFields.DEPARTMENT);
        Entity department = employee.getBelongsToField(EmployeeFields.DEPARTMENT);
        if (departmentRaw != null && department == null) {
            employee.addError(employeeDD.getField(EmployeeFields.DEPARTMENT),
                    L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_CUSTOM);
        }
    }
}
