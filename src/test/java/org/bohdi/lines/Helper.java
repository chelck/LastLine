package org.bohdi.lines;

import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;



public class Helper {

    public static RandomAccessFile createFile(TemporaryFolder folder, String ... ss) throws IOException {
        File file = folder.newFile("file.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        for (String s : ss)
            out.write(s);

        out.close();

        return new RandomAccessFile(file, "r");
    }



    public static void assertContainsExactly(Iterator<Long> iterator, long ... expected) {
        assertEquals(asList(expected), asList(iterator));
        assertFalse("End of Iterator", iterator.hasNext());
    }

    public static void assertContainsExactly(Iterator<String> iterator, String ... expected) {
        assertEquals(asList(expected), asList(iterator));
        assertFalse("End of Iterator", iterator.hasNext());
    }

    public static <T> List<T> asList(Iterator<T> iterator) {
        List<T> list = new ArrayList<T>();

        while (iterator.hasNext())
            list.add(iterator.next());

        return list;
    }

    public static List<Long> asList(long ... ls) {
        List<Long> list = new ArrayList<Long>();

        for (long l : ls)
            list.add(l);

        return list;
    }

    public static List<String> asList(String ... ss) {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, ss);

        return list;
    }
}
