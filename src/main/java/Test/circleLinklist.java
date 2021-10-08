package Test;

import demolition.enums.Direction;

import java.util.LinkedList;

public class circleLinklist {
    public static void main(String[] args) {
        int count = 0;
        for (int i = 1; i < 100000; i++) {
            if (i%7 == 0){
                continue;
            }
            count++;
        }
        System.out.println(count);
    }
}

