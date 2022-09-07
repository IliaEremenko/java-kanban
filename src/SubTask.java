public class SubTask extends Task {
    private String parentName;
    private String color = "\u001B[0m";

    SubTask(String name, String description, String parentName, int id, String status) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setParentName(parentName);
        this.setStatus(status);
    }

    @Override
    public String toString() {
        return color + "   Подзадача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                "\n";
    }

    public String toStringColor() {
        return color +"   Подзадача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                "\n";
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
