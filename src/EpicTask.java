import java.util.ArrayList;

public class EpicTask extends Task {
    ArrayList<SubTask> subTasks;
    int taskCount = 0;

    EpicTask(String name, String description, int id) {
        this.id = id;
        this.name = name;
        this.description = description;
        subTasks = new ArrayList<>();
    }

    EpicTask(String name, String description, int id, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        subTasks = new ArrayList<>();
        this.status = status;
    }


    @Override
    public String toString() {
        return "Задача: " + name +
                ", Статус = " + status +
                ", Описание = " + description +
                ", Подзадачи: " + "\n" + subTasks.toString();
    }

    public String toStringShort() {
        return "Задача: " + name +
                ", Статус = " + status +
                ", Описание = " + description;
    }

    public String toStringShortId() {
        return "Задача: " + name +
                ", Статус = " + status +
                ", Описание = " + description +
                ", id = " + id;
    }

    public void checkStatus() {
        int changedLevel = 3;
        this.status = "NEW";
        for (SubTask subTask : subTasks) {
            if (subTask.status.equals("NEW")) {
                changedLevel = 1;
                this.status = subTask.status;
            }
            if (subTask.status.equals("IN_PROGRESS") || changedLevel > 1) {
                changedLevel = 2;
                this.status = subTask.status;
            }
            if (subTask.status.equals("DONE") || changedLevel > 2) {
                this.status = subTask.status;
            }

        }
    }
}
