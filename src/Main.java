import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = Managers.getDefault(1);
        testRun(inMemoryTaskManager); // <------------------------------------------------------Автоматическое создание
        //---------------------------------------------------------------------------задач по параметрам, см конец Main
        loop:
        while (true) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();
            inMemoryTaskManager.checkStatus();
            switch (command) {
                case (1):
                    inMemoryTaskManager.getAllTasks();
                    break;
                case (2):
                    inMemoryTaskManager.deleteAllTasks();
                    break;
                case (3):
                    System.out.println("Введите имя");
                    String name = scanner.next();
                    inMemoryTaskManager.findAll(name,true);
                    break;
                case (4):
                    inMemoryTaskManager.addTask();
                    break;
                case (5):
                    inMemoryTaskManager.updateTask();
                    break;
                case (6):
                    inMemoryTaskManager.deleteByName();
                    break;
                case (7):
                    ArrayList<Task> memory = inMemoryTaskManager.getHistory();
                    int counter = 1;
                    for (Task task : memory) {
                        if(!inMemoryTaskManager.findById(task,counter))
                            counter--;
                        counter++;
                    }
                    if(counter==1)
                        System.out.println("История пуста");
                    break ;
                case (8):
                    break loop;
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
        System.out.println("8 - выход");
    }

    public static void testRun(InMemoryTaskManager taskManager) {
        taskManager.generateEpicTaskForTest("1");  //создание эпик задачи для тестов с именем 111
        taskManager.generateEpicTaskForTest("2");
        taskManager.generateSubTaskForTest("1", "1"); //создание подзадачи для тестов с именем 111 у эпик задачи 111
        taskManager.generateSubTaskForTest("2", "1");
        taskManager.generateSubTaskForTest("3", "1");
        //taskManager.generateSubTaskForTest("1", "2");
        taskManager.generateTaskForTest("1"); //создание обычных задач
        taskManager.generateTaskForTest("2");
    }
}
