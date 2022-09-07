import java.awt.*;
import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<SubTask> subTasks;

    EpicTask(String name, String description, int id) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        subTasks = new ArrayList<>();
    }

    EpicTask(String name, String description, int id, String status) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        subTasks = new ArrayList<>();
        this.setStatus(status);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "Большая задача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                ", Подзадачи: " + "\n" +subTasks.toString();
    }

    public String toStringShort() {
        return "Большая задача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId();
    }

    public void checkStatus() {
        int changedLevel = 3;
        this.setStatus("NEW");
        for (SubTask subTask : subTasks) {
            if (subTask.getStatus().equals("NEW")) {
                changedLevel = 1;
                this.setStatus(subTask.getStatus());
            }
            if (subTask.getStatus().equals("IN_PROGRESS") && changedLevel > 1) {
                changedLevel = 2;
                this.setStatus(subTask.getStatus());
            }
            if (subTask.getStatus().equals("DONE") && changedLevel > 2) {
                this.setStatus(subTask.getStatus());
            }

        }
    }
}
