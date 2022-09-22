package Tasks;

public class SubTask extends Task {
    private String parentName;

    public SubTask(String name, String description, String parentName, int id, String status) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setParentName(parentName);
        this.setStatus(status);
        this.setTaskType("subTask");
    }

    public int getParentId(String name) {
            int hash = 17;
            if (name != null) {
                hash = name.hashCode();
            }
            return hash;
    }

    @Override
    public String toString() {
        return "Подзадача: " + getName() +
                ", Статус = " + getStatus() +
                ", Описание = " + getDescription() +
                ", id = " + getId() +
                ";\n";
    }

    @Override
    public String toStringCSV() {
        return getId() +
                "," + getTaskType() +
                "," + getName() +
                "," + getStatus() +
                "," + getDescription() +
                ","  + parentName +
                ";\n";
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName(){
        return this.parentName;
    }
}
