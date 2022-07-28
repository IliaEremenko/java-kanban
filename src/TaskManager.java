import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    String name;
    String description;
    String status = "NEW";
    String parentName;
    int type;
    HashMap<String, EpicTask> epicTasks;
    HashMap<String, Task> tasks;

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
            for (SubTask subTask : epicTask.subTasks)
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
        boolean hasBeenFound = false;
        boolean hasBeenFoundToDelete = false;
        System.out.println("Введите имя");
        String name = scanner.next();
        System.out.println("В обычных задачах найдено:");
        for (Task task : tasks.values()) {
            if (task.getName().equals(name)) {
                System.out.println(task.toStringId());
                hasBeenFound = true;
                hasBeenFoundToDelete = true;
            }
        }
        if (!hasBeenFound) {
            System.out.println("Ничего");
        }
        hasBeenFound = false;
        System.out.println("В крупных задачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            if (epicTask.getName().equals(name)) {
                System.out.println(epicTask.toStringShortId());
                hasBeenFound = true;
                hasBeenFoundToDelete = true;
            }
        }
        if (!hasBeenFound) {
            System.out.println("Ничего");
        }
        hasBeenFound = false;
        System.out.println("В подзадачах найдено:");
        for (EpicTask epicTask : epicTasks.values()) {
            for (SubTask subTask : epicTask.subTasks)
                if (subTask.getName().equals(name)) {
                    System.out.println(epicTask.toStringShort());
                    System.out.println(subTask.toStringId());
                    hasBeenFound = true;
                    hasBeenFoundToDelete = true;
                }
        }
        if (!hasBeenFound) {
            System.out.println("Ничего");
        }
        if (hasBeenFoundToDelete) {
            System.out.println("Введите id для удаления");
            int id = scanner.nextInt();
            for (Task task : tasks.values()) {
                if (task.getId() == id) {
                    tasks.remove(task.name);
                    break;
                }
            }
            for (EpicTask epicTask : epicTasks.values()) {
                if (epicTask.getId() == id) {
                    epicTask.subTasks.clear();
                    epicTasks.remove(String.valueOf(epicTask.getName()));
                    break;
                }
            }
            for (EpicTask epicTask : epicTasks.values()) {
                int i = 0;
                for (SubTask subTask : epicTask.subTasks) {
                    if (subTask.getId() == id) {
                        epicTask.subTasks.remove(i);

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
            epicTask.subTasks.clear();
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
        if (isNew) {
            if (type == 1)
                if (tasks.get(name) != null)
                    System.out.println("Такая задача уже существует");
                else {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    tasks.put(name, new Task(name, description, getIdForUsualTask(name), status));
                }
            else if (type == 2) {
                if (epicTasks.get(name) != null) {
                    System.out.println("Такая задача уже существует");
                } else {
                    System.out.println("Введите описание");

                    description = scanner.nextLine();
                    epicTasks.put(name, new EpicTask(name, description, getId(name)));
                }
            } else if (type == 3) {
                boolean isContains = false;
                EpicTask epicTask = epicTasks.get(String.valueOf(parentName));
                if (epicTask == null) {
                    System.out.println("Такой основной задачи не существует");
                    return;
                }
                if (epicTask.getName().equals(parentName))
                    for (SubTask subTask : epicTask.subTasks)
                        if (subTask.name.equals(name)) {
                            isContains = true;
                            break;
                        }
                if (!isContains) {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    epicTasks.get(parentName).subTasks.add(new SubTask(name, description, parentName, getId(name,
                            parentName), status));

                } else
                    System.out.println("Такая задача уже существует");
            }
        } else {
            if (type == 1) {
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
            } else if (type == 2) {
                if (epicTasks.get(name).subTasks.contains(name)) {
                    System.out.println("Такой задачи не существует");
                } else {
                    System.out.println("Введите описание");
                    description = scanner.nextLine();
                    epicTasks.put(name, new EpicTask(name, description, getId(name)));
                }
            } else {
                boolean isContains = false;
                for (EpicTask epicTask : epicTasks.values())
                    if (epicTask.getName().equals(parentName))
                        for (SubTask subTask : epicTask.subTasks)
                            if (subTask.name.equals(name)) {
                                isContains = true;
                                System.out.println("Введите описание");
                                description = scanner.nextLine();
                                System.out.println("Введите статус: " +
                                        "1 - New " +
                                        "2 - In progress " +
                                        "3 - Done");
                                status = convertStatusType(scanner.nextInt());
                                epicTasks.get(parentName).subTasks.get(epicTasks.get(parentName).subTasks.size() - 1).
                                        setDescription(description);
                                epicTasks.get(parentName).subTasks.get(epicTasks.get(parentName).subTasks.size() - 1).
                                        setStatus(status);
                                return;
                            }
                if (!isContains) {
                    System.out.println("Такой задачи не существует");
                }
            }
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
            epicTasks.get(parentId).subTasks.add(new SubTask(name, description, parentId, getId(name, parentId), status));
    }

    public void generateEpicTaskForTest(String name) {
        type = 2;
        description = "333";
        epicTasks.put(name, new EpicTask(name, description, getId(name)));
    }
}
