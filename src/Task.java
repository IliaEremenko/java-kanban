
public class Task {
    int id;
    String status;
    String name;
    String description;

    Task() {
    }

    Task(String name, String description, int id, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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

    @Override
    public String toString() {
        return "Задача :" + name +
                ", Статус = " + status +
                ", Описание = " + description;
    }

    public String toStringId() {
        return "Задача :" + name +
                ", Статус = " + status +
                ", Описание = " + description +
                ", id = " + id;
    }
}


