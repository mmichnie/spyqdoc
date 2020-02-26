package pl.com.bottega.spyqdoc.preparation;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PreparationFacade {

    private final QDocNumberFactory factory;
    private final QDocRepo repo;

    public PreparationFacade(QDocNumberFactory factory, QDocRepo repo) {
        this.factory = factory;
        this.repo = repo;
    }

    public void handle(DoCreateQDocDraft command) {
        QDocNumber number = factory.create();

        QDocDraft qDocDraft = new QDocDraft(number);
        repo.save(qDocDraft);
    }

    public void handle(DoUpdateContent command) {
        QDocDraft qDocDraft = repo.load(command.id);

        qDocDraft.updateContent(command.content);

        repo.save(qDocDraft);

    }

    static class QDocDraft {

        private List<Patch<String>> patches;
        private List<VersionCandidateId> vcIds;
        private QDocNumber number;

        public QDocDraft(QDocNumber number) {
            this.number = number;
            this.patches = new LinkedList<>();
            this.vcIds = new ArrayList<>();
        }

        public void updateContent(String newContent) {
            List<String> currentContent = contentLines();
            List<String> updatedContent = Arrays.asList(newContent.split("\\n"));

            try {
                Patch<String> patch = DiffUtils.diff(currentContent, updatedContent);
                patches.add(patch);
                //emit(new DraftContentWasUpdated(number.toString(), String.join("\n", UnifiedDiffUtils.generateUnifiedDiff("","",currentContent,patch,0))));
            } catch (DiffException e) {
                throw new RuntimeException(e);
            }
        }

        private List<String> contentLines() {
            List<String> content = new ArrayList<>();

            for (Patch<String> patch : patches) {
                try {
                    content = patch.applyTo(content);
                } catch (PatchFailedException e) {
                    throw new RuntimeException(e);
                }
            }
            return content;
        }

        public VersionCandidateId createVC() {
            if (vcIds.stream().findFirst().isPresent()) {

                VersionCandidateId id = new VersionCandidateId(patches.size());
                vcIds.add(id);
                return id;
            } else {
                throw new RuntimeException("");
            }
        }

        String getContent(VersionCandidateId vcId) {
            if (vcIds.contains(vcId)) {
                List<String> content = new ArrayList<>();
                for (int i = 0; i < vcId.getRevision(); i++) {
                    try {
                        content = patches.get(i).applyTo(content);
                    } catch (PatchFailedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return String.join("\n", content);
            } else {
                throw new RuntimeException("no such version: " + vcId.getRevision());
            }

        }

        void markVcAsCompleted(VersionCandidateId vcId) {

        }
    }

//    static class QDocDraft {
//        private final QDocNumber number;
//        private final String title;
//
//        public QDocDraft(QDocNumber number, String title) {
//            this.number = number;
//            this.title = title;
//        }
//    }
}
