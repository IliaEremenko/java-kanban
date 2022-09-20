package HistoryManagers;

import LinkedListNode.Node;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
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
            Node newNode = new Node(task, null);
            if (size == 0) {
                head = newNode;
            } else if (size == 1) {
                newNode = new Node(task, head);
                last = newNode;
                head.setNextLink(last);
            } else {
                Node tempNode = last;
                newNode = new Node(task, tempNode);
                tempNode.setNextLink(newNode);
                last = newNode;
            }
            if(taskHashMap.containsValue(newNode))
                removeNode(taskHashMap.get(newNode.getTask().getId()));
            this.size++;
            taskHashMap.put(newNode.getTask().getId(),newNode);
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

    public InMemoryHistoryManager(){
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
        Node node = customLinkedList.head;
        for (int i = 0; i < customLinkedList.size; i++) {
            arrayList.add(node.getTask());
            node = node.getNextLink();
        }
        return arrayList;
    }
}
