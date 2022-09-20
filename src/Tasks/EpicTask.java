package Tasks;

import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<SubTask> subTasks;

    public EpicTask(String name, String description, int id) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setTaskType("epicTask");
        subTasks = new ArrayList<>();
    }

    public EpicTask(String name, String description, int id, String status) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setStatus(status);
        this.setTaskType("epicTask");
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public ArrayList<String> getSubTasksCSV() {
        ArrayList<String> subTaskArrayList = new ArrayList<>();
        for (SubTask subTask : subTasks)
            subTaskArrayList.add(subTask.toStringCSV());
        return subTaskArrayList;
    }

    public int getId() {
        return super.getId();
    }
    @Override
    public String toStringCSV() {
        if (subTasks.size() != 0) {
            return getId() +
                    "," + getTaskType()+
                    "," + getName() +
                    "," + getStatus() +
                    "," + getDescription() + ";\n" + getSubTasksCSV();
        } else return getId() +
                "," + getTaskType()+
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                ";";
    }

    @Override
    public String toString() {
        return "Большая задача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                ", Подзадачи: " + "\n" + subTasks.toString();
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
