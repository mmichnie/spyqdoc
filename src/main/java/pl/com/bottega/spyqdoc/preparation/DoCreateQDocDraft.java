package pl.com.bottega.spyqdoc.preparation;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class DoCreateQDocDraft {

    @NotEmpty
    String tittle;
    LocalDate date;
}
