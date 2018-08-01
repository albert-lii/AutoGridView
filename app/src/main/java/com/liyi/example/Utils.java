package com.liyi.example;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Integer> getImageList(int size) {
        List<Integer> resList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            resList.add(R.drawable.img_demo_pic);
        }
        return resList;
    }
}
