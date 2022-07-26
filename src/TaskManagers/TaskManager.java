package TaskManagers;

import CustomExceptions.ManagerSaveException;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    void checkStatus();

    boolean findById(Task task, int counter);

    boolean findAll(String name,boolean isForHistory);

    boolean findTask(String name,boolean isForHistory);

    boolean findEpicTask(String name,boolean isForHistory);

    boolean findSubTask(String name,boolean isForHistory);

    void deleteByName();

    void deleteAllTasks();

    void addTask();

    void updateTask();

    void addOrUpdateTask(boolean isNew);

    ArrayList<String> getAllTasks(boolean isShortToString);

    int getIdForUsualTask(String name);

    int getId(String name);

    int getId(String name, String parentId);

    void generateTask(String name, String description, String status);

    void generateSubTask(String name,String description,String status, String parentId,boolean isNew);

    void generateEpicTask(String name,String description,String status);

    String convertStatusType(int statusInput);

    ArrayList<Task> getHistory();

    void save() throws IOException, ManagerSaveException;

}
