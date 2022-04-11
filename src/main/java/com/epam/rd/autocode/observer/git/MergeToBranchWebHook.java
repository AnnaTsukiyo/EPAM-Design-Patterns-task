package com.epam.rd.autocode.observer.git;

import java.util.ArrayList;
import java.util.List;

public class MergeToBranchWebHook implements WebHook {
    private final String branchName;
    private final List<Event> caughtEvents = new ArrayList<>();

    public MergeToBranchWebHook(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String branch() {
        return branchName;
    }

    @Override
    public Event.Type type() {return Event.Type.MERGE;}

    @Override
    public List<Event> caughtEvents() {
        return caughtDistinctEvents();
    }

    private List<Event> caughtDistinctEvents() {
        return caughtEvents;
    }

    @Override
    public void onEvent(Event event) {
        caughtEvents.add(event);
    }

    @Override
    public String toString() {
        return caughtEvents.toString();
    }
}