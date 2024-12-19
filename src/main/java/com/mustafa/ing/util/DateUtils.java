package com.mustafa.ing.util;

import com.mustafa.ing.constant.DefaultConstantValues;
import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class DateUtils {

    public Date getNextNthInstallmentDueDate(int numberOfInstallment) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, (1 + numberOfInstallment));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public long getDayDiff(Date targetDate, Date currentDate) {
        long diff = targetDate.getTime() - currentDate.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public boolean inPayableDateRange(Date dueDate) {
        long diff = dueDate.getTime() - getCurrentDate().getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < DefaultConstantValues.MAX_DIFF_FOR_PAYABLE_INSTALLMENT;
    }
}
