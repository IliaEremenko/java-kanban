import java.util.ArrayList;

public interface TaskManager {

    void checkStatus();

    boolean findById(int id);

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

    ArrayList<Integer> getHistory();

}
