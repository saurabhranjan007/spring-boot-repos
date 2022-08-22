package com.udemy.section20;

import java.util.function.Consumer;

public class OrderConsumer implements Consumer<String> {
    @Override
    public void accept(String data) {
        System.out.println(data);
    }
}
