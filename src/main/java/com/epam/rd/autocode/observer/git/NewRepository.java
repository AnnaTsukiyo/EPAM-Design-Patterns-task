package com.epam.rd.autocode.observer.git;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.rd.autocode.observer.git.Event.Type.COMMIT;
import static com.epam.rd.autocode.observer.git.Event.Type.MERGE;

public class NewRepository implements Repository {
    private final List<WebHook> webHooks;
    private final Map<String, Set<Commit>> commits;

    public NewRepository() {
        this.webHooks = new ArrayList<>();
        this.commits = new HashMap<>();
    }

    @Override
    public void addWebHook(WebHook webHook) {
        webHooks.add(webHook);
    }

    @Override
    public Commit commit(String branch, String author, String[] changes) {
        Commit commit = new Commit(branch, author, changes);
        addCommitToBranch(commit, branch);
        notifyHooksAboutCommit(branch, commit);
        return commit;
    }

    private void addCommitToBranch(Commit commit, String branch) {
        Set<Commit> commits = this.commits.get(branch);
        if (commits != null) {
            commits.add(commit);
        } else {
            commits = new HashSet<>();
            commits.add(commit);
            this.commits.put(branch, commits);
        }
    }

    private void notifyHooksAboutCommit(String branch, Commit commit) {
        webHooks.stream()
                .filter(webHook -> webHook.type().equals(COMMIT) && webHook.branch().equals(branch))
                .forEach(webHook -> webHook.onEvent(new Event(COMMIT, branch, Collections.singletonList(commit))));
    }

    @Override
    public void merge(String sourceBranch, String targetBranch) {
        List<Commit> addedCommits = mergeCommits(sourceBranch, targetBranch);
        notifyHooksAboutMerge(targetBranch, addedCommits);
    }

    private List<Commit> mergeCommits(String sourceBranch, String targetBranch) {
        Set<Commit> sourceBranchCommits = commits.get(sourceBranch);
        return sourceBranchCommits.stream()
                .filter(commit -> isNotInBranch(commit, targetBranch))
                .peek(commit -> addCommitToBranch(commit, targetBranch))
                .collect(Collectors.toList());
    }

    private boolean isNotInBranch(Commit commit, String targetBranch) {
        Set<Commit> commits = this.commits.get(targetBranch);
        return commits == null || !commits.contains(commit);
    }

    private void notifyHooksAboutMerge(String branch, List<Commit> targetBranchCommits) {
        webHooks.stream()
                .filter(webHook -> webHook.type().equals(MERGE) && webHook.branch().equals(branch) && !targetBranchCommits.isEmpty())
                .forEach(webHook -> webHook.onEvent(new Event(MERGE, branch, targetBranchCommits)));
    }

    @Override
    public String toString() {
        return webHooks.toString();
    }
}