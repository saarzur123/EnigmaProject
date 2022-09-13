package decryption.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOMissionResult {
    private Map<String,Long> encryptionCandidatesConfigurationToAgentId = new HashMap<>();

    public Map<String,Long> getEncryptionCandidates() {
        return encryptionCandidatesConfigurationToAgentId;
    }

    public void addCandidate(String codeConfiguration, long agentId) {
        this.encryptionCandidatesConfigurationToAgentId.put(codeConfiguration,agentId);
    }
}
