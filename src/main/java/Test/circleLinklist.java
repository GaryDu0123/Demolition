package Test;

import demolition.enums.Direction;

import java.util.LinkedList;

public class circleLinklist {
    public static void main(String[] args) {
        node s = new node(1);
        node s1 = new node(2);
        node s3 = new node(3);
        final LinkedList<node> nodes = new LinkedList<>();
        nodes.add(s1);
        nodes.add(s);
        nodes.add(s3);

    }
}
class node{
    int num;

    public node(int num) {
        this.num = num;
    }
}
