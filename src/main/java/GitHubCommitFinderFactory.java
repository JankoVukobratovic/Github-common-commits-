public class GitHubCommitFinderFactory implements LastCommonCommitsFinderFactory{
    @Override
    public LastCommonCommitsFinder create(String owner, String repo, String token) {
        return new GitHubCommonCommitFinder(new GitHubClient(owner, repo, token));
    }
}
