package decryption.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOMissionResult {
    private Map<String,Long> encryptionCandidatesConfigurationToAgentId = new HashMap<>();
    private String decryptString;

    public Map<String,Long> getEncryptionCandidates() {
        return encryptionCandidatesConfigurationToAgentId;
    }

    public String getDecryptString() {
        return decryptString;
    }

    public void addCandidate(String codeConfiguration, long agentId, String decrypt) {
        this.encryptionCandidatesConfigurationToAgentId.put(codeConfiguration,agentId);
        decryptString = decrypt;
    }
}
