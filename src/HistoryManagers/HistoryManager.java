package HistoryManagers;

import Tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    void remove();

    ArrayList<Task> getHistory();
}
