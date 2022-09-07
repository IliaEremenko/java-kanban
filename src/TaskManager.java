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

    void getAllTasks();

    int getIdForUsualTask(String name);

    int getId(String name);

    int getId(String name, String parentId);

    String convertStatusType(int statusInput);

    ArrayList<Task> getHistory();

}
