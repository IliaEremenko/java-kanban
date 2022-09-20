package LinkedListNode;

import Tasks.Task;

public class Node {
    private final Task task;
    private Node nextLink;
    private Node prevLink;



    public Node(Task task, Node prevLink) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public Node getNextLink() {
        return nextLink;
    }

    public Node getPrevLink() {
        return prevLink;
    }

    public void setPrevLink(Node prevLink) {
        this.prevLink = prevLink;
    }

    public void setNextLink(Node nextLink) {
        this.nextLink = nextLink;
    }



}
