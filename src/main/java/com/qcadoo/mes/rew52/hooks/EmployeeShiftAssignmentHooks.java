package com.qcadoo.mes.rew52.hooks;

import com.qcadoo.mes.rew52.constants.EmployeeShiftAssignmentFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeShiftAssignmentHooks {
    private static final String ERROR_OVERLAPPING_SHIFT_ASSIGNMENTS = "rew52.employeeShiftAssignment.error.overlappingAssignments";

    public void onSave(final DataDefinition dataDefinition, final Entity entity) {

        if (entity.getId() == null || entity.isValid()) {
            validateNoOverlappingAssignments(dataDefinition, entity);
        }
    }

    private void validateNoOverlappingAssignments(final DataDefinition dataDefinition, final Entity entity) {
        Long employeeId = entity.getBelongsToField(EmployeeShiftAssignmentFields.EMPLOYEE).getId();
        Date startDate = entity.getDateField(EmployeeShiftAssignmentFields.START_DATE);
        Date endDate = entity.getDateField(EmployeeShiftAssignmentFields.END_DATE);

        if (startDate.after(endDate)) {
            entity.addError(dataDefinition.getField(EmployeeShiftAssignmentFields.START_DATE),
                    "rew52.employeeShiftAssignment.error.invalidDateRange");
            return;
        }

        SearchCriteriaBuilder scb = dataDefinition.find()
                .add(SearchRestrictions.eq("employee.id", employeeId))
                .add(SearchRestrictions.ne("id", entity.getId() != null ? entity.getId() : -1L))
                .add(SearchRestrictions.and(
                        SearchRestrictions.le(EmployeeShiftAssignmentFields.START_DATE, endDate),
                        SearchRestrictions.ge(EmployeeShiftAssignmentFields.END_DATE, startDate)
                ));

        List<Entity> overlappingAssignments = scb.list().getEntities();

        if (!overlappingAssignments.isEmpty()) {
            entity.addError(dataDefinition.getField(EmployeeShiftAssignmentFields.EMPLOYEE),
                    "rew52.employeeShiftAssignment.error.overlappingAssignments");
        }
    }
}
