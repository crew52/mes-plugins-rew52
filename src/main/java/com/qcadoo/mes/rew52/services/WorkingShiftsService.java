package com.qcadoo.mes.rew52.services;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import org.springframework.stereotype.Service;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalTime;
import org.springframework.util.StringUtils;

@Service
public class WorkingShiftsService {

    private static final String L_SUNDAY = "sunday";

    private static final String L_SATURDAY = "saturday";

    private static final String L_FRIDAY = "friday";

    private static final String L_THURSDAY = "thursday";

    private static final String L_WENSDAY = "wensday";

    private static final String L_TUESDAY = "tuesday";

    private static final String L_MONDAY = "monday";

    private static final String HOURS_LITERAL = "Hours";

    private static final String WORKING_LITERAL = "Working";

    private static final String[] WEEK_DAYS = { L_MONDAY, L_TUESDAY, L_WENSDAY, L_THURSDAY, L_FRIDAY, L_SATURDAY, L_SUNDAY };

    public boolean validateWorkingShiftHoursField(final DataDefinition dataDefinition, final Entity entity) {
        boolean valid = true;

        for (String day : WEEK_DAYS) {
            if (!validateHourField(day, dataDefinition, entity)) {
                valid = false;
            }
        }

        return valid;
    }

    private boolean validateHourField(final String day, final DataDefinition dataDefinition, final Entity entity) {
        boolean isDayActive = (Boolean) entity.getField(day + WORKING_LITERAL);

        String fieldValue = entity.getStringField(day + HOURS_LITERAL);

        if (!isDayActive) {
            return true;
        }

        if (fieldValue == null || "".equals(fieldValue.trim())) {
            entity.addError(dataDefinition.getField(day + HOURS_LITERAL), "qcadooView.validate.field.error.missing");
            return false;
        }

        try {
            convertDayHoursToInt(fieldValue);
        } catch (IllegalStateException e) {
            entity.addError(dataDefinition.getField(day + HOURS_LITERAL),
                    "rew52.validate.global.error.workingShift.hoursFieldWrongFormat");

            return false;
        }

        return true;
    }

    public LocalTime[][] convertDayHoursToInt(final String string) {
        if (!StringUtils.hasText(string)) {
            return new LocalTime[][] {};
        }

        String[] parts = string.trim().split(",");

        LocalTime[][] hours = new LocalTime[parts.length][];

        for (int i = 0; i < parts.length; i++) {
            hours[i] = convertRangeHoursToInt(parts[i]);
        }

        return hours;
    }

    private LocalTime[] convertRangeHoursToInt(final String string) {
        String[] parts = string.trim().split("-");

        if (parts.length != 2) {
            throw new IllegalStateException("Invalid time range " + string + ", should be hh:mm-hh:mm");
        }

        LocalTime[] range = new LocalTime[2];

        range[0] = convertHoursToInt(parts[0]);
        range[1] = convertHoursToInt(parts[1]);

        return range;
    }

    private LocalTime convertHoursToInt(final String string) {
        String[] parts = string.trim().split(":");

        if (parts.length != 2) {
            throw new IllegalStateException("Invalid time " + string + ", should be hh:mm");
        }

        try {
            return new LocalTime(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        } catch (IllegalFieldValueException e) {
            throw new IllegalStateException("Invalid time " + string, e);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid time " + string + ", should be hh:mm", e);
        }
    }
}
