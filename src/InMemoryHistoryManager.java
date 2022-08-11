import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Integer> memory;


    InMemoryHistoryManager(){
        memory = new ArrayList<>();
    }

    @Override
    public void clearHistory() {
        memory.clear();
    }

    @Override
    public void saveHistory(Task task) {
        memory.add(task.getId()); //сохраняю id что бы в истории была актуальная информация,задачи в истории обновляются
        if (memory.size() == 11)  //вместе с обновлением самой задачи ,удаленные задачи тоже удаляются
            memory.remove(0);
    }

    @Override
    public ArrayList<Integer> getHistory() {
        return memory;
    }
}
