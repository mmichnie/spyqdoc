package pl.com.bottega.spyqdoc.preparation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QDocRepo extends JpaRepository<PreparationFacade.QDocDraft,Long> {
    PreparationFacade.QDocDraft load(UUID id);
}
