package com.qcadoo.mes.rew52.imports.parsers;

import java.util.Objects;
import java.util.function.Consumer;

import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.DepartmentFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.mes.basic.imports.helpers.CellErrorsAccessor;
import com.qcadoo.mes.basic.imports.helpers.CellParser;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;

@Component
public class DepartmentCellParser implements CellParser{

    private static final String L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_LOOKUP_CODE_NOT_FOUND = "qcadooView.validate.field.error.lookupCodeNotFound";

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    public void parse(final String cellValue, final String dependentCellValue, final CellErrorsAccessor errorsAccessor,
                      final Consumer<Object> valueConsumer) {
        Entity department = getDepartmentByCode(cellValue);

        if (Objects.isNull(department)) {
            errorsAccessor.addError(L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_LOOKUP_CODE_NOT_FOUND);
        } else {
            valueConsumer.accept(department);
        }
    }

    private Entity getDepartmentByCode(final String code) {
        return getDepartmentDD().find().add(SearchRestrictions.eq(DepartmentFields.CODE, code)).setMaxResults(1).uniqueResult();
    }

    private DataDefinition getDepartmentDD() {
        return dataDefinitionService.get(BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_DEPARTMENT);
    }
}
