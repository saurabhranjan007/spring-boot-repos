package com.udemy.section14;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Writer implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {

        System.out.println("Inside Write..");
        System.out.println("Writing data: "+list);
    }
}
