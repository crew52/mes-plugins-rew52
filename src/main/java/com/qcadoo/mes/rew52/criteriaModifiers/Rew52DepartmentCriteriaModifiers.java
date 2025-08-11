package com.qcadoo.mes.rew52.criteriaModifiers;

import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;
import org.springframework.stereotype.Service;

@Service
public class Rew52DepartmentCriteriaModifiers {

    public void filterParentDepartments(final SearchCriteriaBuilder scb, final FilterValueHolder filterValueHolder) {
        // Luôn chỉ lấy bản ghi active
        scb.add(SearchRestrictions.eq("active", true));

        // Loại bỏ bản ghi hiện tại nếu có name
        if (filterValueHolder.has("currentName") && !filterValueHolder.getString("currentName").isEmpty()) {
            String currentName = filterValueHolder.getString("currentName");
            scb.add(SearchRestrictions.ne("name", currentName));
        }
    }
}
