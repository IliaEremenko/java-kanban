package TaskManagers;

import CustomExceptions.ManagerSaveException;
import Enums.Statuses;
import ManagersCreator.Managers;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager{
    private String status = "NEW";
    private String parentName;
    private int type;
    private final HashMap<String, EpicTask> epicTasks;
    private final HashMap<String, Task> tasks;
    ArrayList<String> stringToSave;
    static InMemoryTaskManager inMemoryTaskManager;
    private static final String FILE_NAME = "Tasks.txt";


    static final boolean IS_TEST_RUN_NEEDED = true;
    static final boolean IS_TASK_GENERATION_NEEDED = false;

    public static void main(String[] args) {
        if (IS_TEST_RUN_NEEDED) {
            inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault(1);
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
            try {
                fileBackedTasksManager.computeLoadedFile(loadFromFile());
            } catch (Exception ignored) {
            }
            if (IS_TASK_GENERATION_NEEDED) {
                fileBackedTasksManager.generateEpicTask("АБВ", "description", String.valueOf(Statuses.NEW));  //создание эпик задачи для тестов с именем АБВ
                fileBackedTasksManager.generateEpicTask("АБВГ", "description", String.valueOf(Statuses.NEW));
                fileBackedTasksManager.generateSubTask("3", "description", String.valueOf(Statuses.NEW), "АБВГ", true);
                fileBackedTasksManager.generateSubTask("1", "description", String.valueOf(Statuses.NEW), "АБВ", true);//создание подзадачи для тестов с именем 1 у эпик задачи АБВ
                fileBackedTasksManager.generateSubTask("2", "description", String.valueOf(Statuses.NEW), "АБВ", true);
                fileBackedTasksManager.generateSubTask("3", "description", String.valueOf(Statuses.NEW), "АБВ", true);
                fileBackedTasksManager.generateTask("ААА", "description", String.valueOf(Statuses.NEW)); //создание обычных задач
                fileBackedTasksManager.generateTask("ЬЬЬ", "description", String.valueOf(Statuses.NEW));
            }
            ArrayList<String> tasks = fileBackedTasksManager.getAllTasks(false);
            if(tasks.size()==0)
                System.out.println("Задач нет");
            else
                System.out.println("Загружены задачи: ");
            try {
                fileBackedTasksManager.taskToStringBeforeSave(tasks,false);
            } catch (ManagerSaveException ignored) {
            }
            if(inMemoryTaskManager.getHistory().isEmpty())
                System.out.println("Истории нет");
            else
                System.out.println("Загружена история: ");
            System.out.println("Поиск задачи с именем 3 в истории");
            fileBackedTasksManager.findAll("3",true);
            ArrayList<Task> memory = fileBackedTasksManager.getHistory();
            int counter = 1;
            for (Task task : memory) {
                if(!fileBackedTasksManager.findById(task,counter))
                    counter--;
                counter++;
            }
            System.out.println("Сохранить новую историю? 1 - да; 2 - нет");
            Scanner scanner = new Scanner(System.in);
            String name = "1";//scanner.next();
            if (name.equals("1")) {
                tasks = fileBackedTasksManager.getAllTasks(true);
                try{
                    fileBackedTasksManager.taskToStringBeforeSave(tasks,true);
                } catch (ManagerSaveException e) {
                    System.out.println("Ошибка при сохранении");
                }
                System.out.println("Задачи и история сохранены, тестовая программа завершена");
            } else {
                System.out.println("Тестовая программа завершена");
            }
        }
    }

    public FileBackedTasksManager () {
        tasks = new HashMap<>();
        epicTasks = new HashMap<>();
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault(1);
    }

    public String getDescription() {
       return super.getDescription();
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
        return inMemoryTaskManager.findById(task2,counter);
    }

    @Override
    public boolean findAll(String name,boolean isForHistory) {
        return inMemoryTaskManager.findAll(name,isForHistory);
    }

    @Override
    public boolean findTask(String name,boolean isForHistory){
        return inMemoryTaskManager.findTask(name,isForHistory);
    }

    @Override
    public boolean findEpicTask(String name,boolean isForHistory){
        return inMemoryTaskManager.findEpicTask(name,isForHistory);
    }

    @Override
    public boolean findSubTask(String name,boolean isForHistory) {
       return inMemoryTaskManager.findSubTask(name,isForHistory);
    }

    @Override
    public void deleteByName() {
        inMemoryTaskManager.deleteByName();
    }

    @Override
    public void deleteAllTasks() {
        inMemoryTaskManager.deleteAllTasks();
    }

    @Override
    public void addTask() {
        inMemoryTaskManager.addTask();
    }

    @Override
    public void updateTask() {
        inMemoryTaskManager.updateTask();
    }

    @Override
    public void addOrUpdateTask(boolean isNew) {
        inMemoryTaskManager.addOrUpdateTask(isNew);
    }

    @Override
    public ArrayList<String> getAllTasks(boolean isShortToString) {
        return inMemoryTaskManager.getAllTasks(isShortToString);
    }

    @Override
    public int getIdForUsualTask(String name) {
        return inMemoryTaskManager.getIdForUsualTask(name);
    }

    @Override
    public int getId(String name) {
        return inMemoryTaskManager.getId(name);
    }

    @Override
    public int getId(String name, String parentId) {
        return inMemoryTaskManager.getId(name,parentId);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return inMemoryTaskManager.getHistory();
    }

    @Override
    public String convertStatusType(int statusInput) {
        return inMemoryTaskManager.convertStatusType(statusInput);
    }

    public void generateTask(String name, String description, String status) {
        inMemoryTaskManager.generateTask1(name,description,status);
    }

    public void generateSubTask(String name,String description,String status,String parentId,boolean isNew) {
        inMemoryTaskManager.generateSubTask1(name,description,status,parentId,isNew);
    }

    public void generateEpicTask(String name,String description,String status) {
        inMemoryTaskManager.generateEpicTask1(name,description,status);
    }

    public void taskToStringBeforeSave(ArrayList<String> taskList, boolean isToSave) throws ManagerSaveException {
        if(!isToSave)
            System.out.println(taskList);
        stringToSave = new ArrayList<>();
        stringToSave.add ("id,type,name,status,description,epic\n");
        for (String task : taskList) {
            if (!task.equals("Обычные задачи: \n")
                    && !task.equals("Крупные задачи: \n")
            ) {
                task = task.replace("[","");
                task = task.replace("]","");
                task = task.replace(";\n, ",";\n");
                stringToSave.add(task);
            }
        }
        stringToSave.add("\n");
        ArrayList<Task> historyList = getHistory();
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : historyList){
            switch (task.getTaskType()){
                case "epicTask":
                case "task":
                    stringBuilder.append(task.getId() + ",");
                    stringBuilder.append(task.getTaskType() + ",");
                    stringBuilder.append(task.getName() + ",");
                    stringBuilder.append(task.getStatus() + ",");
                    stringBuilder.append(task.getDescription() + ";\n");
                    stringToSave.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    break;
                case "subTask":
                    int parentId = 0;
                    EpicTask foundParentforSubTask;
                    for (EpicTask epicTask1 : inMemoryTaskManager.getEpicTasks().values()) {
                        if (epicTask1.getId() == Integer.parseInt(task.getParentName())) {
                            foundParentforSubTask = epicTask1;
                            parentId = foundParentforSubTask.getId();
                        }
                    }
                    stringBuilder.append(task.getId() + ",");
                    stringBuilder.append(task.getTaskType() + ",");
                    stringBuilder.append(task.getName() + ",");
                    stringBuilder.append(task.getStatus() + ",");
                    stringBuilder.append(task.getDescription() + ",");
                    stringBuilder.append(parentId + ";\n");
                    stringToSave.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    break;
            }
        }
        if(isToSave) {
            try {
                save();
            } catch (IOException e) {
                throw new ManagerSaveException(e);
            }
        }
    }

    static public String loadFromFile() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        char tempChar;
        FileReader fileReader = new FileReader(FILE_NAME);
        int data = fileReader.read();
        while(data!=-1){
            tempChar =(char)data;
            stringBuilder.append(tempChar);
            data = fileReader.read();
        }
        fileReader.close();
        return stringBuilder.toString();
    }

    public void computeLoadedFile(String loadedString){
        StringBuilder stringBuilder = new StringBuilder(loadedString);
        boolean isHistory = false;
        ArrayList<String> loadedFromFile = new ArrayList<>();
        String tempString;
        String[] sprintArrayToSplitTasks;
        String[] sprintArrayToSplitOneTask;
        stringBuilder.replace(0,37,"");
        tempString = stringBuilder.toString();
        sprintArrayToSplitTasks = tempString.split(";");
        for (String tempString1 : sprintArrayToSplitTasks){
            sprintArrayToSplitOneTask = tempString1.split(",");
            for (String tempString2 : sprintArrayToSplitOneTask) {
                loadedFromFile.add(tempString2);
            }
            tempString = String.valueOf(loadedFromFile.get(0).charAt(0))
                    +String.valueOf(loadedFromFile.get(0).charAt(1));
            if(tempString.equals("\n\n")){
                isHistory = true;
            }
            if (!isHistory){
                switch (loadedFromFile.get(1)){
                    case ("epicTask"):
                        inMemoryTaskManager.generateEpicTask1(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                loadedFromFile.get(3));
                        break;
                    case ("task"):
                        inMemoryTaskManager.generateTask1(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                loadedFromFile.get(3));
                        break;
                    case ("subTask"):
                        inMemoryTaskManager.generateSubTask1(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                loadedFromFile.get(3),
                                loadedFromFile.get(5),
                                false);
                        break;
                }
            }
            if (isHistory){
                loadedFromFile.set(0,loadedFromFile.get(0).replaceAll("\n",""));
                switch (loadedFromFile.get(1)){
                    case ("subTask"):
                        SubTask subTask = new SubTask(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                loadedFromFile.get(5),
                                Integer.parseInt(loadedFromFile.get(0)),
                                loadedFromFile.get(3));
                        /*for (EpicTask epicTask : epicTasks.values()){
                            if (epicTask.getId()==subTask.getParentId(loadedFromFile.get(5))){
                                inMemoryTaskManager.addHistory(epicTask);
                            }
                        }*/
                        inMemoryTaskManager.addHistory(subTask);
                        break;
                    case ("epicTask"):
                        EpicTask epicTask = new EpicTask(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                Integer.parseInt(loadedFromFile.get(0)),
                                loadedFromFile.get(3));
                        inMemoryTaskManager.addHistory(epicTask);
                        break;
                    case ("task"):
                        Task task = new Task(loadedFromFile.get(2),
                                loadedFromFile.get(4),
                                Integer.parseInt(loadedFromFile.get(0)),
                                loadedFromFile.get(3));
                        inMemoryTaskManager.addHistory(task);
                        break;
                }
            }
            loadedFromFile.clear();
        }
    }

    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_NAME);
        for (String string : stringToSave){
            fileWriter.append(string);
        }
        fileWriter.close();
    }
}
