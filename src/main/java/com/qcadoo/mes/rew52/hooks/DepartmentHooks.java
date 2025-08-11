package com.qcadoo.mes.rew52.hooks;

import com.qcadoo.mes.rew52.constants.DepartmentFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentHooks {

    public void fillParentName(final DataDefinition dataDefinition, final Entity department) {
        Entity parent = department.getBelongsToField(DepartmentFields.PARENT);
        if (parent != null) {
            department.setField("parentName", parent.getStringField(DepartmentFields.NAME));
        }
    }
}
