package com.qcadoo.mes.rew52.services;

import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FieldComponent;
import com.qcadoo.view.api.components.LookupComponent;
import com.qcadoo.view.api.components.lookup.FilterValueHolder;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    public void onBeforeRender(final ViewDefinitionState view) {
        setFilters(view);
    }
    private void setFilters(final ViewDefinitionState view) {
        // Lấy name hiện tại của bản ghi
        FieldComponent nameField = (FieldComponent) view.getComponentByReference("name");
        Object nameValue = nameField.getFieldValue();

        // Lấy lookup parent
        LookupComponent parent = (LookupComponent) view.getComponentByReference("parent");

        // Lấy filter hiện tại
        FilterValueHolder filterValueHolder = parent.getFilterValue();

        if (nameValue != null && !nameValue.toString().isEmpty()) {
            // Truyền name hiện tại vào filter (String)
            filterValueHolder.put("currentName", nameValue.toString());
        } else {
            // Xóa nếu không có name
            filterValueHolder.remove("currentName");
        }

        // Gán filter vào lookup
        parent.setFilterValue(filterValueHolder);
    }
}
