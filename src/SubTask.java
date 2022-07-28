public class SubTask extends Task {
    String parentName;
    SubTask(String name, String description, String parentName, int id, String status){
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "    Подзадача: " + name +
                ", Статус = " + status +
                ", Описание = " + description +
                "\n";
    }

    public String toStringId() {
        return "   Подзадача: " + name +
                ", Статус = " + status +
                ", Описание = " + description+
                ", id = " + id;
    }

}
