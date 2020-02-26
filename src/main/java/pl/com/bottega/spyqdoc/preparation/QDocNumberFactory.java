package pl.com.bottega.spyqdoc.preparation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.YearMonth;

@AllArgsConstructor
@Component
public class QDocNumberFactory {

    private final SequenceGenerator seq;
    private final Clock clock;
    private final UserProvider userProvider;
    private final SystemConfig systemConfig;


    QDocNumber create() {
        return new QDocNumber(systemConfig.getSystemType(),
                seq.next(), YearMonth.now(clock),
                userProvider.isAuditor(),
                systemConfig.isDemoMode());
    }

}
