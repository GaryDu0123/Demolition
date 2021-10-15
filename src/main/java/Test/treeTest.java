package Test;


import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

public class treeTest implements Comparable<treeTest>{
    int a;

    public treeTest(int a) {
        this.a = a;
    }

    @Override
    public int compareTo(treeTest o) {
        return this.a - o.a;
    }

    public static void main(String[] args) {
        treeTest treeTest = new treeTest(5);
        treeTest treeTest1 = new treeTest(5);

        System.out.println(treeTest.compareTo(treeTest1));
        LinkedList<Test.treeTest> treeTests = new LinkedList<>();
        Collections.sort(treeTests);
        treeTests.add(treeTest);
        treeTests.add(treeTest1);
        System.out.println(treeTests);
    }
}
