package pl.com.bottega.spyqdoc.preparation;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class QDocController {

    private final PreparationFacade preparationFacade;

    @PostMapping
    public String create(@Valid @RequestBody DoCreateQDocDraft command) {
        preparationFacade.handle(command);
        return "";
    }

}
