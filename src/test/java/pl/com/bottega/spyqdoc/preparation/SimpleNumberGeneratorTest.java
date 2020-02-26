package pl.com.bottega.spyqdoc.preparation;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;

class SimpleNumberGeneratorTest {


    @Test
    void name() {
        SimpleNumberGenerator iso = new SimpleNumberGenerator("ISO", 2L, YearMonth.of(2020, 12), false, true);

        String number = iso.generate();


    }
}
