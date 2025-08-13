package com.qcadoo.mes.rew52.validators;

import java.util.Calendar;
import java.util.Date;

public class DateRangeUtils {

    private DateRangeUtils() {
        // Ngăn không cho khởi tạo
    }

    /**
     * Kiểm tra tuổi nằm trong khoảng minAge - maxAge (tính theo ngày hiện tại)
     *
     * @param dateOfBirth Ngày sinh
     * @param minAge Tuổi tối thiểu
     * @param maxAge Tuổi tối đa
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isAgeBetween(Date dateOfBirth, int minAge, int maxAge) {
        if (dateOfBirth == null) {
            return false;
        }

        Calendar now = Calendar.getInstance();

        Calendar minAllowedDate = (Calendar) now.clone();
        minAllowedDate.add(Calendar.YEAR, -minAge);

        Calendar maxAllowedDate = (Calendar) now.clone();
        maxAllowedDate.add(Calendar.YEAR, -maxAge);

        return !(dateOfBirth.after(minAllowedDate.getTime()) || dateOfBirth.before(maxAllowedDate.getTime()));
    }
}
