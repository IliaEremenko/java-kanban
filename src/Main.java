import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        testRun(taskManager); // <------------------------------------------------------------Автоматическое создание
        //---------------------------------------------------------------------------задач по параметрам, см конец Main
        loop:
        while(true){
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();
            taskManager.checkStatus();
            switch (command) {
                case (1):
                    taskManager.getAllTasks();
                    break;
                case (2):
                    taskManager.deleteAllTasks();
                    break;
                case (3):
                    System.out.println("Введите имя");
                    String name = scanner.next();
                    taskManager.findBy(name);
                    break;
                case (4):
                    taskManager.addTask();
                    break;
                case (5):
                    taskManager.updateTask();
                    break;
                case (6):
                    taskManager.deleteByName();
                    break;
                case (7):
                    break loop;
                }
            }
    }

    public static void printMenu(){
        System.out.println("1 - получить список всех задач");
        System.out.println("2 - удалить все задачи");
        System.out.println("3 - получить задачу по имени");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - удалить по имени");
        System.out.println("7 - выход");
    }
    public static void testRun(TaskManager taskManager){
        taskManager.generateEpicTaskForTest("111");  //создание эпик задачи для тестов с именем 111
        taskManager.generateEpicTaskForTest("222");
        taskManager.generateSubTaskForTest("111","111"); //создание подзадачи для тестов с именем 111 у эпик задачи 111
        taskManager.generateSubTaskForTest("333","111");
        taskManager.generateSubTaskForTest("444","111");
        taskManager.generateSubTaskForTest("111","222");
        taskManager.generateTaskForTest("111"); //создание обычных задач
        taskManager.generateTaskForTest("222");
    }
}
