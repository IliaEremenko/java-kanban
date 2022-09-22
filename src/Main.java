import CustomExceptions.ManagerSaveException;
import Enums.Statuses;
import ManagersCreator.Managers;
import TaskManagers.FileBackedTasksManager;
import Tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = (FileBackedTasksManager) Managers.getDefault(2);
        try {fileBackedTasksManager.computeLoadedFile(fileBackedTasksManager.loadFromFile());}
        catch (Exception ignored) {
        }
        final boolean IS_TEST_RUN_NEEDED = true;
        testRun(fileBackedTasksManager,IS_TEST_RUN_NEEDED); // <-----------------------------------Автоматическое создание
        //---------------------------------------------------------------------------задач по параметрам, см конец Main

        loop:
        while (true) {

            ArrayList<String> tasks = fileBackedTasksManager.getAllTasks(false);
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();
            fileBackedTasksManager.checkStatus();
            switch (command) {
                case (1):
                    try {
                        fileBackedTasksManager.taskToStringBeforeSave(tasks,false);
                    } catch (ManagerSaveException e) {
                        System.out.println("Ошибка при сохранении");
                    }
                    break;
                case (2):
                    fileBackedTasksManager.deleteAllTasks();
                    break;
                case (3):
                    System.out.println("Введите имя");
                    String name = scanner.next();
                    fileBackedTasksManager.findAll(name,true);
                    break;
                case (4):
                    fileBackedTasksManager.addTask();
                    break;
                case (5):
                    fileBackedTasksManager.updateTask();
                    break;
                case (6):
                    fileBackedTasksManager.deleteByName();
                    break;
                case (7):
                    ArrayList<Task> memory = fileBackedTasksManager.getHistory();
                    int counter = 1;
                    for (Task task : memory) {
                        if(!fileBackedTasksManager.findById(task,counter))
                            counter--;
                        counter++;
                    }
                    if(counter==1)
                        System.out.println("История пуста");
                    break ;
                case (8):
                    tasks = fileBackedTasksManager.getAllTasks(true);
                    try{
                    fileBackedTasksManager.taskToStringBeforeSave(tasks,true);
                    } catch (ManagerSaveException e) {
                        System.out.println("Ошибка при сохранении");
                    }

                    break loop;
               /* case (9):
                    tasks = fileBackedTasksManager.getAllTasks(true);;
                    fileBackedTasksManager.taskToStringBeforeSave(tasks,true);*/
            }
        }
    }

    public static void printMenu() {
        System.out.println("1 - получить список всех задач");
        System.out.println("2 - удалить все задачи");
        System.out.println("3 - получить задачу по имени");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - удалить по имени");
        System.out.println("7 - история просмотров");
        System.out.println("8 - сохранить и выйти");
    }

    public static void testRun(FileBackedTasksManager taskManager, boolean testRun) {
        if(testRun) {
            taskManager.generateEpicTask("АБВ","description", String.valueOf(Statuses.NEW));  //создание эпик задачи для тестов с именем 111
            taskManager.generateEpicTask("АБВГ","description", String.valueOf(Statuses.NEW));
            taskManager.generateSubTask("3", "description", String.valueOf(Statuses.NEW),"АБВГ",true);
            taskManager.generateSubTask("1", "description", String.valueOf(Statuses.NEW),"АБВ",true);//создание подзадачи для тестов с именем 111 у эпик задачи 111
            taskManager.generateSubTask("2", "description", String.valueOf(Statuses.NEW),"АБВ",true);
            taskManager.generateSubTask("3", "description", String.valueOf(Statuses.NEW),"АБВ",true);
            //taskManager.generateSubTaskForTest("1", "2");
            taskManager.generateTask("ААА","description", String.valueOf(Statuses.NEW)); //создание обычных задач
            taskManager.generateTask("ЬЬЬ","description", String.valueOf(Statuses.NEW));
        }
    }
}
