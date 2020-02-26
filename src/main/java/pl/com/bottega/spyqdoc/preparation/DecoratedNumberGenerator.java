package pl.com.bottega.spyqdoc.preparation;

import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.YearMonth;

@AllArgsConstructor
public class DecoratedNumberGenerator {

    private final SequenceGenerator seq;
    private final Clock clock;
    private final UserProvider userProvider;
    private final SystemConfig systemConfig;


    NumberGenerator create() {
        return new DemoNumberDecorator(new AuditNumberDecorator(new IsoNumberGenerator(seq,clock),userProvider),systemConfig);
    }


    interface NumberGenerator {
        String generate();
    }

    static class IsoNumberGenerator implements NumberGenerator {

        private final SequenceGenerator seq;
        private final Clock clock;

        IsoNumberGenerator(SequenceGenerator seq, Clock clock) {
            this.seq = seq;
            this.clock = clock;
        }

        @Override
        public String generate() {
            YearMonth yearMonth = YearMonth.now(clock);
            return  "ISO" + "/" + seq.next() + "/" + yearMonth.getMonthValue() + "/" + yearMonth.getYear();

        }

    }

    static class AuditNumberDecorator implements NumberGenerator {

        private final NumberGenerator generator;
        private final UserProvider userProvider;

        AuditNumberDecorator(NumberGenerator generator, UserProvider userProvider) {
            this.generator = generator;
            this.userProvider = userProvider;
        }

        @Override
        public String generate() {
            final String number = generator.generate();

            return userProvider.isAuditor() ? number + "/AUDIT" : number;
        }
    }

    static class DemoNumberDecorator implements NumberGenerator {

        private final NumberGenerator generator;
        private final SystemConfig systemConfig;

        DemoNumberDecorator(NumberGenerator generator, SystemConfig systemConfig) {
            this.generator = generator;
            this.systemConfig = systemConfig;
        }

        @Override
        public String generate() {
            final String docNumber = generator.generate();
            return systemConfig.isDemoMode() ? "DEMO/" + docNumber : docNumber;
        }
    }

    static class QepNumberGenerator implements NumberGenerator {
        private final SequenceGenerator seq;
        private final Clock clock;

        QepNumberGenerator(SequenceGenerator seq, Clock clock) {
            this.seq = seq;
            this.clock = clock;
        }

        @Override
        public String generate() {
            YearMonth yearMonth = YearMonth.now(clock);
            return "QEP" + "/" + seq.next() + "/" + yearMonth.getMonthValue() + "/" + yearMonth.getYear();
        }
    }
}
