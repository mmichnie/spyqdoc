package pl.com.bottega.spyqdoc.preparation;

import java.time.YearMonth;

public class SimpleNumberGenerator {

//    private final SequenceGenerator seq;
//    private final Clock clock;
//    private final UserProvider userProvider;
//    private final SystemConfig systemConfig;


    private final String systemType;
    private final Long seq;
    private final YearMonth yearMonth;
    private final boolean isAuditor;
    private final boolean isDemo;

    public SimpleNumberGenerator(String systemType, Long seq, YearMonth yearMonth, boolean isAuditor, boolean isDemo) {
        this.systemType = systemType;
        this.seq = seq;
        this.yearMonth = yearMonth;
        this.isAuditor = isAuditor;
        this.isDemo = isDemo;
    }

    QDocNumber generate() {

//        YearMonth yearMonth = YearMonth.now(clock);
        String num = systemType + "/" + seq + "/" + yearMonth.getMonthValue() + "/" + yearMonth.getYear();

        if (isAuditor) {
            num = num + "/AUDIT";
        }

        if (isDemo) {
            num = "DEMO/" + num;
        }

        return new QDocNumber(num);
    }

}
