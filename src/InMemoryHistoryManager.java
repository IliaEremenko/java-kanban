import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager{
    CustomLinkedList customLinkedList;
    public static class CustomLinkedList {
        private Node head;
        private Node last;
        private int size = 0;

        private final HashMap<Integer, Node> taskHashMap;

        CustomLinkedList() {
            taskHashMap = new HashMap<>();
        }

        public void linkLast(Task task) {
            Node node = new Node(task, null);
            if (size == 0) {
                head = node;
            } else if (size == 1) {
                node = new Node(task, head);
                last = node;
                head.setNextLink(last);
            } else {
                Node tempNode = last;
                node = new Node(task, tempNode);
                tempNode.setNextLink(node);
                last = node;
            }
            if(taskHashMap.containsValue(node))
                removeNode(taskHashMap.get(node.getTask().getId()));
            this.size++;
            taskHashMap.put(node.getTask().getId(),node);
        }

        public void removeNode(Node node) {
            Node nodeForDeletion = taskHashMap.get(node.getTask().getId());
            if (nodeForDeletion!=null) {
                int i = 0;
                Node prevNode = nodeForDeletion.getPrevLink();
                Node nextNode = nodeForDeletion.getNextLink();
                if (prevNode == null) {
                    head = nextNode;
                } else {prevNode.setNextLink(nextNode);}
                if (nextNode == null) {
                    last = prevNode;
                } else {
                    nextNode.setPrevLink(prevNode);
                }
                taskHashMap.remove(i);
                nodeForDeletion = null;
                size--;
            }
        }

        public void removeAllNodes(){
            for(int i = 0; i < size; i++) {
                if(this.head!=null) {
                    Node nextNode = this.head.getNextLink();
                    head = null;
                    head = nextNode;
                }
            }
            taskHashMap.clear();
            this.last=null;
            size = 0;
        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            Node currentNode = this.head;
            while (currentNode != null) {
                tasks.add(currentNode.getTask());
                currentNode = currentNode.getNextLink();
            }
            return tasks;
        }

        public HashMap<Integer, Node> getTaskHashMap() {
            return taskHashMap;
        }
    }

    InMemoryHistoryManager(){
        customLinkedList = new CustomLinkedList();
    }

    @Override
    public void remove() {
        customLinkedList.removeAllNodes();//очищаю историю вместе с удалением всех задач
    }

    @Override
    public void add(Task task) {
        customLinkedList.linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> arrayList = new ArrayList<>();
        for (Node node : customLinkedList.getTaskHashMap().values())
            arrayList.add(node.getTask());
        return arrayList;
    }
}
