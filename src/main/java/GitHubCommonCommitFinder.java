import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.*;


@RequiredArgsConstructor
public class GitHubCommonCommitFinder implements LastCommonCommitsFinder {

    @NonNull
    private final GitHubClient client;

    @Override
    public Collection<String> findLastCommonCommits(String branchA, String branchB) throws IOException {
        List<Commit> branchACommits = client.getCommitsFromBranch(branchA);
        List<Commit> branchBCommits = client.getCommitsFromBranch(branchB);

        Set<String> commonCommitShas = new HashSet<>();
        for (Commit commitA : branchACommits) {
            for (Commit commitB : branchBCommits) {
                if (commitA.getSha().equals(commitB.getSha())) {
                    commonCommitShas.add(commitA.getSha());
                    break;
                }
            }
        }
        return commonCommitShas;
    }
}
