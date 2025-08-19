package com.qcadoo.mes.rew52.criteriaModifiers;

import com.qcadoo.mes.rew52.constants.EmployeeFields;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;
import org.springframework.stereotype.Service;

@Service
public class Rew52ContractCriteriaModifiers {
    public void filterActiveEmployees(final SearchCriteriaBuilder scb) {
        scb.add(SearchRestrictions.eq(EmployeeFields.ACTIVE, true));
    }
}
