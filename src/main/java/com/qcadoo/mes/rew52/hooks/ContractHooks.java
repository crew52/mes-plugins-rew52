package com.qcadoo.mes.rew52.hooks;

import com.qcadoo.mes.rew52.constants.ContractFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ContractHooks {
    private static final String ERROR_OVERLAP = "rew52.contract.overlapping.activeContracts";
    private static final String ERROR_ATTACHMENT_INVALID = "rew52.contract.attachment.invalidFormat";

    public void onSave(final DataDefinition dataDefinition, final Entity entity) {
        // 1. Kiểm tra định dạng file đính kèm
        String filePath = entity.getStringField(ContractFields.ATTACHMENT);
        if (filePath != null && !filePath.toLowerCase().endsWith(".pdf")) {
            entity.addError(dataDefinition.getField(ContractFields.ATTACHMENT), ERROR_ATTACHMENT_INVALID);
        }
        // 2. Kiểm tra chồng lấn hợp đồng
        if (entity.getId() == null || entity.isValid()) {
            Long employeeId = entity.getBelongsToField(ContractFields.EMPLOYEE).getId();
            Date startDate = entity.getDateField(ContractFields.START_DATE);
            Date endDate = entity.getDateField(ContractFields.END_DATE);

            /// Tìm tất cả các hợp đồng khác của cùng nhân viên,
            // có trạng thái ACTIVE hoặc PENDING, và có thời gian giao nhau
            SearchCriteriaBuilder scb = dataDefinition.find()

                    // Chỉ chọn các hợp đồng của cùng nhân viên
                    .add(SearchRestrictions.eq("employee.id", employeeId))

                    // Trạng thái là ACTIVE hoặc PENDING
                    .add(SearchRestrictions.in(ContractFields.STATUS,
                            Arrays.asList(ContractFields.STATUS_ACTIVE, ContractFields.STATUS_PENDING)))

                    // Loại bỏ chính bản thân hợp đồng hiện tại
                    .add(SearchRestrictions.ne("id", entity.getId() != null ? entity.getId() : -1L))

                    // Kiểm tra xem thời gian có chồng lấn không:
                    // Giao nhau khi:
                    // 1. startDate <= endDate hiện tại AND endDate >= startDate hiện tại
                    // 2. startDate <= endDate hiện tại AND endDate của hợp đồng trong DB bị null (hợp đồng chưa kết thúc)
                    .add(SearchRestrictions.or(
                            SearchRestrictions.and(
                                    SearchRestrictions.le(ContractFields.START_DATE, endDate),
                                    SearchRestrictions.ge(ContractFields.END_DATE, startDate)
                            ),
                            SearchRestrictions.and(
                                    SearchRestrictions.le(ContractFields.START_DATE, endDate),
                                    SearchRestrictions.isNull(ContractFields.END_DATE)
                            )
                    ));


            List<Entity> overlappingContracts = scb.list().getEntities();

            if (!overlappingContracts.isEmpty()) {
                entity.addError(dataDefinition.getField(ContractFields.EMPLOYEE), ERROR_OVERLAP);
            }
        }
    }
}
