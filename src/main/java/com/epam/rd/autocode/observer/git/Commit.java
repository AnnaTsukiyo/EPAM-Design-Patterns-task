package com.epam.rd.autocode.observer.git;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class Commit {
    private String branch;
    private final String author;
    private final String[] changes;

    public Commit(String branch, final String author, final String[] changes) {
        this.branch = branch;
        this.author = author;
        this.changes = changes;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    String branch() {
        return branch;
    }

    String author() {
        return author;
    }

    String[] changes() {
        return changes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Commit commit = (Commit) o;

        if (!Objects.equals(author, commit.author)) return false;
        if (!Objects.equals(branch, commit.branch)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(changes, commit.changes);
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(changes);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Commit.class.getSimpleName() + "[", "]")
                .add(author)
                .add(Arrays.toString(changes))
                .toString();
    }
}