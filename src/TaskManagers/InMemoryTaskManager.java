package TaskManagers;

import Enums.Statuses;
import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;
import HistoryManagers.InMemoryHistoryManager;
import ManagersCreator.Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class InMemoryTaskManager implements TaskManager {
    private String description;
    private String status = "NEW";
    private String parentName;
    private int type;
    private final HashMap<String, EpicTask> epicTasks;
    private final HashMap<String, Task> tasks;
    InMemoryHistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory(1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void checkStatus() {
        for (EpicTask epicTask : epicTasks.values()) {
            epicTask.checkStatus();
        }
    }

    @Override
    public boolean findById(Task task2, int counter){
        boolean hasBeenFound = false;
        for (Task task : tasks.values()) {
            if (task.getId()==task2.getId()) {
                System.out.print(counter + ") ");
                System.out.println(task);
                hasBeenFound = true;
            }
        }
        for (EpicTask epicTask : epicTasks.values()) {
            if (epicTask.getId()==task2.getId()) {
                System.out.print(counter + ") ");
                System.out.println(epicTask);
                hasBeenFound = true;
            }
        }
        for (EpicTask epicTask : epicTasks.values()) {
            for (SubTask subTask : epicTask.getSubTasks())
                if (subTask.getId()==task2.getId()) {
                    System.out.print(counter + ") ");
                    //System.out.println(epicTask.toStringCSV());
                    System.out.println(subTask);
                    hasBeenFound = true;
                }
        }

        return hasBeenFound;
    }

    @Override
    public boolean findAll(String name,boolean isForHistory){
        boolean hasBeenFound;
        boolean hasBeenFound2 = false;
        for (int i = 0; i < 1; i++) {
            hasBeenFound = findTask(name,isForHistory);
            if(hasBeenFound)
                hasBeenFound2 = true;
            hasBeenFound = findEpicTask(name,isForHistory);
            if(hasBeenFound)
                hasBeenFound2 = true;
            hasBeenFound = findSubTask(name,isForHistory);
            if(hasBeenFound)
                hasBeenFound2 = true;
        }
        return hasBeenFound2;
    }

    @Override
    public boolean findTask(String name,boolean isForHistory){
        boolean hasBeenFound = false;
        System.out.println("В обычных задачах найдено:");
        for (Task task : tasks.values()) {
            if (task.getName().equals(name)) {
                System.out.println(task);
                hasBeenFound = true;
                if(isForHistory)
                    inMemoryHistoryManager.add(task);
            }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        return hasBeenFound;
    }

    @Override
    public boolean findEpicTask(String name,boolean isForHistory){
        boolean hasBeenFound = false;
        System.out.println("В крупных задачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            if (epicTask.getName().equals(name)) {
                System.out.println(epicTask);
                hasBeenFound = true;
                if(isForHistory)
                    inMemoryHistoryManager.add(epicTask);
            }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        return hasBeenFound;
    }

    @Override
    public boolean findSubTask(String name,boolean isForHistory) {
        boolean hasBeenFound = false;
        System.out.println("В подзадачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            for (SubTask subTask : epicTask.getSubTasks())
                if (subTask.getName().equals(name)) {
                    System.out.println(epicTask.toStringShort());
                    System.out.println(subTask);
                    hasBeenFound = true;
                    if(isForHistory) {
                        inMemoryHistoryManager.add(epicTask);
                        inMemoryHistoryManager.add(subTask);
                    }
                }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        return hasBeenFound;

    }

    @Override
    public void deleteByName() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scanner.next();
        int id;
        if (findAll(name,false)) {
            System.out.println("Введите id для удаления");
            id = scanner.nextInt();
            for (Task task : tasks.values()) {
                if (task.getId() == id) {
                    tasks.remove(task.getName());
                    break;
                }
            }
            for (EpicTask epicTask : epicTasks.values()) {
                if (epicTask.getId() == id) {
                    epicTask.getSubTasks().clear();
                    epicTasks.remove(String.valueOf(epicTask.getName()));
                    break;
                }
            }
            for (EpicTask epicTask : epicTasks.values()) {
                int i = 0;
                for (SubTask subTask : epicTask.getSubTasks()) {
                    if (subTask.getId() == id) {
                        epicTask.getSubTasks().remove(i);

                        break;
                    }
                    i++;
                }
            }
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        for (EpicTask epicTask : epicTasks.values()) {
            epicTask.getSubTasks().clear();
        }
        inMemoryHistoryManager.remove();
        epicTasks.clear();
        System.out.println("Успешно");
    }

    @Override
    public void addTask() {
        addOrUpdateTask(true);
    }

    @Override
    public void updateTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scanner.next();
        if (findAll(name,false))
            addOrUpdateTask(false);
        else System.out.println("Такой задачи не существует");
    }

    @Override
    public void addOrUpdateTask(boolean isNew) {
        status = "NEW";
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите вид задачи:" +
                "1 - обычная задача, " +
                "2 - крупная задача, " +
                "3 - подзадача");
        setType(scanner.nextInt());
        if (getType() == 3) {
            System.out.println("Существующие основные задачи: ");
            for (EpicTask epicTask : epicTasks.values())
                System.out.println(epicTask.toStringCSV());
            System.out.println("Введите имя основной задачи");
            setParentName(scanner.next());
            System.out.println("Введите имя подзадачи");
        } else
            System.out.println("Введите имя");
        name = scanner.next();
        scanner.nextLine();
        if(!isNew)
            setType(getType()+3);
        switch (getType()){
            case (1):
                if (tasks.get(name) != null)
                    System.out.println("Такая задача уже существует");
                else {
                    System.out.println("Введите описание");
                    setDescription(scanner.nextLine());
                    Task task = new Task(name, getDescription(), getIdForUsualTask(name), getStatus());
                    tasks.put(name, task);
                    inMemoryHistoryManager.add(task);
                }
                break;
            case(2):
                if (epicTasks.get(name) != null) {
                    System.out.println("Такая задача уже существует");
                } else {
                    System.out.println("Введите описание");

                    setDescription(scanner.nextLine());
                    EpicTask epicTask = new EpicTask(name, getDescription(), getIdForUsualTask(name), getStatus());
                    epicTasks.put(name, epicTask);
                    inMemoryHistoryManager.add(epicTask);
                }
                break;
            case(3):
                boolean isContains = false;
                EpicTask epicTask = epicTasks.get(String.valueOf(getParentName()));
                if (epicTask == null) {
                    System.out.println("Такой основной задачи не существует");
                    return;
                }
                if (epicTask.getName().equals(getParentName()))
                    for (SubTask subTask : epicTask.getSubTasks())
                        if (subTask.getName().equals(name)) {
                            isContains = true;
                            break;
                        }
                if (!isContains) {
                    System.out.println("Введите описание");
                    setDescription(scanner.nextLine());
                    SubTask subTask = new SubTask(name, getDescription(), String.valueOf(getParentName().hashCode()), getId(name,
                            getParentName()), getStatus());
                    epicTasks.get(getParentName()).getSubTasks().add(subTask);
                    inMemoryHistoryManager.add(subTask);

                } else
                    System.out.println("Такая задача уже существует");
                break;
            case (4):
                if (tasks.get(name) == null) {
                    System.out.println("Такой задачи не существует");
                } else {
                    System.out.println("Введите описание");
                    setDescription(scanner.nextLine());
                    System.out.println("Введите статус: " +
                            "1 - New " +
                            "2 - In progress " +
                            "3 - Done");
                    setStatus(convertStatusType(scanner.nextInt()));
                    Task task = new Task(name, getDescription(), getIdForUsualTask(name), getStatus());
                    tasks.put(name, task);
                    inMemoryHistoryManager.add(task);
                }
                break;
            case(5):
                if (epicTasks.get(name).getSubTasks().contains(name)) {
                    System.out.println("Такой задачи не существует");
                } else {
                    System.out.println("Введите описание");
                    setDescription(scanner.nextLine());
                    EpicTask epicTask2 = new EpicTask(name, getDescription(), getIdForUsualTask(name), getStatus());
                    epicTasks.put(name, epicTask2);
                    inMemoryHistoryManager.add(epicTask2);
                }
                break;
            case (6):
                int i = -1;
                for (EpicTask epicTask2 : epicTasks.values())
                    if (epicTask2.getName().equals(getParentName()))
                        for (SubTask subTask : epicTask2.getSubTasks()) {
                            i++;
                            if (subTask.getName().equals(name)) {
                                System.out.println("Введите описание");
                                setDescription(scanner.nextLine());
                                System.out.println("Введите статус: " +
                                        "1 - New " +
                                        "2 - In progress " +
                                        "3 - Done");
                                setStatus(convertStatusType(scanner.nextInt()));
                                epicTasks.get(getParentName()).getSubTasks().get(i).setDescription(getDescription());
                                epicTasks.get(getParentName()).getSubTasks().get(i).setStatus(getStatus());
                                inMemoryHistoryManager.add(epicTasks.get(getParentName()).getSubTasks().get(i));
                                return;
                            }
                        }
                System.out.println("Такой задачи не существует");
                break;
            }
        }

    @Override
    public ArrayList<String> getAllTasks(boolean isShortToString) {
        String toCheckIfEmpty;
        ArrayList<String> tasksString = new ArrayList<>();
        boolean isEmpty = true;
        if (tasks.size() > 0) {
           tasksString.add("Обычные задачи: \n");
            isEmpty = false;
            for (Task task : tasks.values())
                if (isShortToString)
                    tasksString.add(task.toStringCSV());
                else tasksString.add(task.toString());
        }
        if (epicTasks.size() > 0) {
            tasksString.add("Крупные задачи: \n");
            isEmpty = false;
            for (EpicTask task : epicTasks.values()) {
                if (isShortToString) {
                    toCheckIfEmpty = task.toStringCSV();
                    if (!toCheckIfEmpty.equals(""))
                        tasksString.add(toCheckIfEmpty);
                }
                else tasksString.add(task.toString());
            }

        }
        if (isEmpty) {
            System.out.println("Задач нет");
        }
        return tasksString;
    }

    @Override
    public int getIdForUsualTask(String name) {
        int hash = 17;
        if (name != null) {
            hash -= name.hashCode();
        }
        return hash;
    }

    @Override
    public int getId(String name) {
        int hash = 17;
        if (name != null) {
            hash = name.hashCode();
        }
        return hash;
    }

    @Override
    public int getId(String name, String parentId) {
        int hash = 17;
        if (name != null) {
            hash = name.hashCode();
        }
        hash *= 31;
        if (parentId != null) {
            hash = hash + parentId.hashCode();
        }
        return hash;
    }

    @Override
    public ArrayList<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public void addHistory(Task task){
        inMemoryHistoryManager.add(task);
    }

    @Override
    public String convertStatusType(int statusInput) {
        while (true) {
            if (statusInput == 1)
                return Statuses.NEW.name();
            if (statusInput == 2)
                return Statuses.IN_PROGRESS.name();
            if (statusInput == 3)
                return Statuses.DONE.name();
            System.out.println("Неправильный выбор");
            Scanner scanner = new Scanner(System.in);
            statusInput = scanner.nextInt();
        }
    }

    public HashMap<String, EpicTask> getEpicTasks() {
        return epicTasks;
    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    @Override
    public void generateTask(String name, String description, String status) {
        tasks.put(name, new Task(name, description, getIdForUsualTask(name), status));
    }

    @Override
    public void generateSubTask(String name,String description,String status, String parentId,boolean isNew) {

        if (epicTasks.size() > 0) {
            SubTask subTask;
            for (EpicTask epic : epicTasks.values()) {
                if (isNew){
                    subTask = new SubTask(name,
                            description,
                            String.valueOf(getId(parentId)),
                            getId(name,parentId),
                            status);
                    if (epic.getId() == getId(parentId)) {
                        epic.getSubTasks().add(subTask);
                        }
                } else {
                    String tempName = "";
                    for (EpicTask epicTask : epicTasks.values())
                        if (epicTask.getId()==Integer.parseInt(parentId))
                            tempName = epicTask.getName();
                    subTask = new SubTask(name,
                            description,
                            parentId,
                            getId(name,tempName),
                            status);
                    if (epic.getId() == getId(tempName)) {
                            epic.getSubTasks().add(subTask);
                    }
                }
            }
        }
    }

    @Override
    public void generateEpicTask(String name,String description,String status) {
        epicTasks.put(name, new EpicTask(name, description, getId(name),status));
    }
    @Override
    public void save() throws IOException{
    }

}
