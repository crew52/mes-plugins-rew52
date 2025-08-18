package com.qcadoo.mes.rew52.criteriaModifiers;

import com.qcadoo.mes.rew52.constants.DepartmentFields;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCriteriaModifiers {
    private static final String DEPARTMENT_CODE_KEY = "code";

    public void selectDepartment(final SearchCriteriaBuilder scb, final FilterValueHolder filterValueHolder) {
        if (filterValueHolder.has(DEPARTMENT_CODE_KEY)) {
            String value = filterValueHolder.getString(DEPARTMENT_CODE_KEY);
            if (value != null && !value.isEmpty()) {
                scb.add(SearchRestrictions.eq(DepartmentFields.CODE, value));
            }
        }
    }
}
