import java.util.ArrayList;

public interface HistoryManager {
    void saveHistory(Task task);

    void clearHistory();

    ArrayList<Integer> getHistory();
}
