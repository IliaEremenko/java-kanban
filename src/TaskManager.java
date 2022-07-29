import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    private String description;
    private String status = "NEW";
    private String parentName;
    private int type;
    private HashMap<String, EpicTask> epicTasks;
    private HashMap<String, Task> tasks;

    TaskManager() {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
    }

    public void checkStatus() {
        for (EpicTask epicTask : epicTasks.values()) {
            epicTask.checkStatus();
        }
    }

    public boolean findBy(String name) {
        boolean hasBeenFound = false;
        boolean hasBeenFound2 = false;
        System.out.println("В обычных задачах найдено:");
        for (Task task : tasks.values()) {
            if (task.getName().equals(name)) {
                System.out.println(task);
                hasBeenFound = true;
                hasBeenFound2 = true;
            }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        hasBeenFound = false;
        System.out.println("В крупных задачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            if (epicTask.getName().equals(name)) {
                System.out.println(epicTask);
                hasBeenFound = true;
                hasBeenFound2 = true;
            }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        hasBeenFound = false;
        System.out.println("В подзадачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            for (SubTask subTask : epicTask.getSubTasks())
                if (subTask.getName().equals(name)) {
                    System.out.println(epicTask.toStringShort());
                    System.out.println(subTask);
                    hasBeenFound = true;
                    hasBeenFound2 = true;
                }
        }
        if (!hasBeenFound)
            System.out.println("Ничего");
        return hasBeenFound2;

    }

    public void deleteByName() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scanner.next();
        boolean hasBeenFoundToDelete = findBy(name);
        if (hasBeenFoundToDelete) {
            System.out.println("Введите id для удаления");
            int id = scanner.nextInt();
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

    public void deleteAllTasks() {
        tasks.clear();
        for (EpicTask epicTask : epicTasks.values()) {
            epicTask.getSubTasks().clear();
        }
        epicTasks.clear();
        System.out.println("Успешно");
    }

    public void addTask() {
        addOrUpdateTask(true);
    }

    public void updateTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя");
        String name = scanner.next();
        if (findBy(name))
            addOrUpdateTask(false);
        else System.out.println("Такой задачи не существует");
    }

    private void addOrUpdateTask(boolean isNew) {
        status = "NEW";
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите вид задачи:" +
                "1 - обычная задача, " +
                "2 - крупная задача, " +
                "3 - подзадача");
        type = scanner.nextInt();
        if (type == 3) {
            System.out.println("Существующие основные задачи: ");
            for (EpicTask epicTask : epicTasks.values())
                System.out.println(epicTask.toStringShort());
            System.out.println("Введите имя основной задачи");
            parentName = scanner.next();
            System.out.println("Введите имя подзадачи");

        } else
            System.out.println("Введите имя");
        name = scanner.next();
        scanner.nextLine();
        if(!isNew)
            type+=3;
        switch (type){
            case (1):
                if (tasks.get(name) != null)
                    System.out.println("Такая задача уже существует");
                else {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    tasks.put(name, new Task(name, description, getIdForUsualTask(name), status));
                }
                break;
            case(2):
                if (epicTasks.get(name) != null) {
                    System.out.println("Такая задача уже существует");
                } else {
                    System.out.println("Введите описание");

                    description = scanner.nextLine();
                    epicTasks.put(name, new EpicTask(name, description, getId(name)));
                }
                break;
            case(3):
                boolean isContains = false;
                EpicTask epicTask = epicTasks.get(String.valueOf(parentName));
                if (epicTask == null) {
                    System.out.println("Такой основной задачи не существует");
                    return;
                }
                if (epicTask.getName().equals(parentName))
                    for (SubTask subTask : epicTask.getSubTasks())
                        if (subTask.getName().equals(name)) {
                            isContains = true;
                            break;
                        }
                if (!isContains) {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    epicTasks.get(parentName).getSubTasks().add(new SubTask(name, description, parentName, getId(name,
                            parentName), status));

                } else
                    System.out.println("Такая задача уже существует");
                break;
            case (4):
                if (epicTasks.get(name) == null) {
                    System.out.println("Такой задачи не существует");
                } else {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    System.out.println("Введите статус: " +
                            "1 - New " +
                            "2 - In progress " +
                            "3 - Done");
                    status = convertStatusType(scanner.nextInt());
                    tasks.put(name, new Task(name, description, getIdForUsualTask(name), status));
                }
                break;
            case(5):
                if (epicTasks.get(name).getSubTasks().contains(name)) {
                    System.out.println("Такой задачи не существует");
                } else {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    epicTasks.put(name, new EpicTask(name, description, getId(name)));
                }
                break;
            case (6):
                for (EpicTask epicTask2 : epicTasks.values())
                    if (epicTask2.getName().equals(parentName))
                        for (SubTask subTask : epicTask2.getSubTasks())
                            if (subTask.getName().equals(name)) {
                                System.out.println("Введите описание");
                                description = scanner.nextLine();
                                System.out.println("Введите статус: " +
                                        "1 - New " +
                                        "2 - In progress " +
                                        "3 - Done");
                                status = convertStatusType(scanner.nextInt());
                                epicTasks.get(parentName).getSubTasks().get(epicTasks.get(parentName).getSubTasks().size() - 1).
                                        setDescription(description);
                                epicTasks.get(parentName).getSubTasks().get(epicTasks.get(parentName).getSubTasks().size() - 1).
                                        setStatus(status);
                                return;
                            }
                System.out.println("Такой задачи не существует");
                break;
            }
        }

    public void getAllTasks() {
        boolean isEmpty = true;
        if (tasks.size() > 0) {
            System.out.println("Обычные задачи: ");
            isEmpty = false;
            for (Task task : tasks.values())
                System.out.println(task.toString());
        }
        if (epicTasks.size() > 0) {
            System.out.println("Крупные задачи: ");
            isEmpty = false;
            System.out.println(epicTasks.toString() + "\n");

        }
        if (isEmpty) System.out.println("Задач нет");
    }

    private int getIdForUsualTask(String name) {
        int hash = 17;
        if (name != null) {
            hash -= name.hashCode();
        }
        return hash;
    }

    private int getId(String name) {
        int hash = 17;
        if (name != null) {
            hash = name.hashCode();
        }
        return hash;
    }

    private int getId(String name, String parentId) {
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

    private String convertStatusType(int statusInput) {
        while (true) {
            if (statusInput == 1)
                return "NEW";
            if (statusInput == 2)
                return "IN_PROGRESS";
            if (statusInput == 3)
                return "DONE";
            System.out.println("Неправильный выбор");
            Scanner scanner = new Scanner(System.in);
            statusInput = scanner.nextInt();
        }
    }

    public void generateTaskForTest(String name) {
        type = 1;
        description = "test";
        tasks.put(name, new Task(name, description, getIdForUsualTask(name), status));
    }

    public void generateSubTaskForTest(String name, String parentId) {
        type = 3;

        description = "test";

        if (epicTasks.size() > 0)
            epicTasks.get(parentId).getSubTasks().add(new SubTask(name, description, parentId, getId(name, parentId), status));
    }

    public void generateEpicTaskForTest(String name) {
        type = 2;
        description = "333";
        epicTasks.put(name, new EpicTask(name, description, getId(name)));
    }
}
