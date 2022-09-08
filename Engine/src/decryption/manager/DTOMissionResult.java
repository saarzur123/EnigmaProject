package decryption.manager;

import java.util.ArrayList;
import java.util.List;

public class DTOMissionResult {
    private List<String> encryptionCandidates = new ArrayList<>();

    public List<String> getEncryptionCandidates() {
        return encryptionCandidates;
    }

    public void addCandidate(String encryptionCandidates) {
        this.encryptionCandidates.add(encryptionCandidates);
    }
}
