package pl.com.bottega.spyqdoc.preparation;

import java.time.YearMonth;

public class QDocNumber {

    final String number;

    public QDocNumber(String systemType, Long seq, YearMonth yearMonth, boolean isAuditor, boolean isDemo) {
        String num = systemType + "/" + seq + "/" + yearMonth.getMonthValue() + "/" + yearMonth.getYear();

        if (isAuditor) {
            num = num + "/AUDIT";
        }

        if (isDemo) {
            num = "DEMO/" + num;
        }

        this.number = num;
    }
}
