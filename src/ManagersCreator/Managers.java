package ManagersCreator;

import HistoryManagers.HistoryManager;
import HistoryManagers.InMemoryHistoryManager;
import TaskManagers.FileBackedTasksManager;
import TaskManagers.InMemoryTaskManager;
import TaskManagers.TaskManager;

public class Managers {


    public static TaskManager getDefault(int type) {
        switch (type) { //сделал switch заранее как написано в ТЗ
            case (1):
                return new InMemoryTaskManager();
            case (2):
                return new FileBackedTasksManager();
        }
        return null;
    }

    public static HistoryManager getDefaultHistory(int type) {
        switch (type) { //сделал switch заранее как написано в ТЗ
            case (1):
                return new InMemoryHistoryManager();
            case (2):
                return new InMemoryHistoryManager();
        }
        return null;
    }
}