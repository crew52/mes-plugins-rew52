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

/**
 * Hook class for Contract entity.
 * <p>
 * Contains business validations when saving contract entities, including:
 * <ul>
 *     <li>Validating uploaded attachment format</li>
 *     <li>Preventing overlapping contracts for the same employee</li>
 * </ul>
 */
@Service
public class ContractHooks {

    public static final String ERROR_INVALID_ATTACHMENT_FORMAT = "rew52.contract.attachment.invalidFormat";
    public static final String ERROR_OVERLAPPING_CONTRACTS = "rew52.contract.overlapping.activeContracts";
    public static final String FILE_PDF = ".pdf";

    /**
     * Triggered when a Contract entity is saved.
     * <p>
     * Performs the following validations:
     * <ul>
     *     <li>Checks if the uploaded attachment is a PDF file</li>
     *     <li>Checks if the employee already has overlapping active/pending contracts</li>
     * </ul>
     *
     * @param dataDefinition the data definition of the Contract entity
     * @param entity         the contract entity being saved
     */
    public void onSave(final DataDefinition dataDefinition, final Entity entity) {
        validateAttachmentFormat(dataDefinition, entity);

        if (entity.getId() == null || entity.isValid()) {
            validateOverlappingContracts(dataDefinition, entity);
        }
    }

    /**
     * Validates that the uploaded file (if any) is in PDF format.
     *
     * @param dataDefinition the data definition of the Contract entity
     * @param entity         the contract entity
     */
    private void validateAttachmentFormat(final DataDefinition dataDefinition, final Entity entity) {
        String filePath = entity.getStringField(ContractFields.ATTACHMENT);

        if (filePath != null && !filePath.toLowerCase().endsWith(FILE_PDF)) {
            entity.addError(dataDefinition.getField(ContractFields.ATTACHMENT),
                    ERROR_INVALID_ATTACHMENT_FORMAT);
        }
    }

    /**
     * Validates that the employee does not have another overlapping contract.
     * <p>
     * An overlap occurs when:
     * <ul>
     *     <li>The current contract's date range intersects with another contract's date range</li>
     *     <li>The other contract is in ACTIVE or PENDING status</li>
     * </ul>
     *
     * @param dataDefinition the data definition of the Contract entity
     * @param entity         the contract entity being saved
     */
    private void validateOverlappingContracts(final DataDefinition dataDefinition, final Entity entity) {
        Long employeeId = entity.getBelongsToField(ContractFields.EMPLOYEE).getId();
        Date startDate = entity.getDateField(ContractFields.START_DATE);
        Date endDate = entity.getDateField(ContractFields.END_DATE);

        SearchCriteriaBuilder scb = dataDefinition.find()
                .add(SearchRestrictions.eq("employee.id", employeeId))
                .add(SearchRestrictions.in(ContractFields.STATUS,
                        Arrays.asList(ContractFields.STATUS_ACTIVE, ContractFields.STATUS_PENDING)))
                .add(SearchRestrictions.ne("id", entity.getId() != null ? entity.getId() : -1L))
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
            entity.addError(dataDefinition.getField(ContractFields.EMPLOYEE),
                    ERROR_OVERLAPPING_CONTRACTS);
        }
    }
}

