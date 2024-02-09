package com.ledao;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * @author LeDao
 * @company
 * @create 2022-01-24 23:48
 */
public class MyTest {

    public static void main(String[] args) {
        File file = new File("E://data//a.txt");
        FileUtils.deleteQuietly(file);
    }
}
