package com.qcadoo.mes.rew52.imports.parsers;

import java.util.Objects;
import java.util.function.Consumer;

import com.qcadoo.mes.rew52.constants.BasicConstants;
import com.qcadoo.mes.rew52.constants.PositionFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.mes.basic.imports.helpers.CellErrorsAccessor;
import com.qcadoo.mes.basic.imports.helpers.CellParser;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;

@Component
public class PositionCellParser implements CellParser{
    private static final String L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_LOOKUP_CODE_NOT_FOUND = "qcadooView.validate.field.error.lookupCodeNotFound";

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    public void parse(final String cellValue, final String dependentCellValue, final CellErrorsAccessor errorsAccessor,
                      final Consumer<Object> valueConsumer) {
        Entity position = getPositionByName(cellValue);

        if (Objects.isNull(position)) {
            errorsAccessor.addError(L_QCADOO_VIEW_VALIDATE_FIELD_ERROR_LOOKUP_CODE_NOT_FOUND);
        } else {
            valueConsumer.accept(position);
        }
    }

    private Entity getPositionByName(final String name) {
        return getPositionDD().find().add(SearchRestrictions.eq(PositionFields.NAME, name)).setMaxResults(1).uniqueResult();
    }

    private DataDefinition getPositionDD() {
        return dataDefinitionService.get(BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_POSITION);
    }
}
