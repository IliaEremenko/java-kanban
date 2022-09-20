package Tasks;

import TaskManagers.InMemoryTaskManager;

public class Task extends InMemoryTaskManager {
    private int id;
    private String status;
    private String name;
    private String description;
    private String taskType;

    Task() {
        super();
    }

    public Task(String name, String description, int id, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = "task";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Задача :" + name +
                ", Статус = " + status +
                ", Описание = " + description +
                ", id = " + id + "\n";
    }

    public String toStringCSV(){
        return  getId() +
                "," + getTaskType() +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                ";\n";
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}


