package com.qcadoo.mes.rew52.criteriaModifiers;

import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.stereotype.Service;

@Service
public class Rew52CriteriaHelper {

    public void filterActiveEmployees(final SearchCriteriaBuilder scb) {
        scb.add(SearchRestrictions.eq(EmployeeFields.ACTIVE, true));
    }
}