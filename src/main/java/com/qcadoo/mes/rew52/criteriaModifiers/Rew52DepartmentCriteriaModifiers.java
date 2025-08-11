package com.qcadoo.mes.rew52.criteriaModifiers;

import com.qcadoo.model.api.search.JoinType;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.stereotype.Service;

@Service
public class Rew52DepartmentCriteriaModifiers {

    public void filterParentDepartments(final SearchCriteriaBuilder scb) {
        // Lấy ID của bản ghi hiện tại từ filterValue
    //    Long currentId = null;
    //    if (filterValue.has("id") && !filterValue.getString("id").isEmpty()) {
    //        currentId = filterValue.getLong("id");
    //    }

        scb.add(SearchRestrictions.eq("active", true));

    //    if (currentId != null) {
    //        scb.add(SearchRestrictions.ne("id", currentId));
    //    }
    }
}
