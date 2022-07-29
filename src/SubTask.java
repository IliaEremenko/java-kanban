public class SubTask extends Task {
    private String parentName;

    SubTask(String name, String description, String parentName, int id, String status) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setParentName(parentName);
        this.setStatus(status);
    }

    @Override
    public String toString() {
        return "   Подзадача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                "\n";
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
