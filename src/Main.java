import java.util.ArrayList;
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
                    ArrayList<Integer> memory = inMemoryTaskManager.getHistory();
                    int i = 1;
                    for (Integer task : memory) {
                        System.out.print(i + ") ");
                        if(!inMemoryTaskManager.findById(task))
                            System.out.println("Удаленная задача");
                        i++;
                    }
                    if(i==1)
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
        taskManager.generateEpicTaskForTest("111");  //создание эпик задачи для тестов с именем 111
        taskManager.generateEpicTaskForTest("222");
        taskManager.generateSubTaskForTest("111", "111"); //создание подзадачи для тестов с именем 111 у эпик задачи 111
        taskManager.generateSubTaskForTest("333", "111");
        taskManager.generateSubTaskForTest("444", "111");
        taskManager.generateSubTaskForTest("111", "222");
        taskManager.generateTaskForTest("111"); //создание обычных задач
        taskManager.generateTaskForTest("222");
    }
}
