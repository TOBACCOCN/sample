package com.example.sample;

public class NyPizza extends Pizza{

    public static class Builder extends Pizza.Builder<Builder> {

        @Override
        NyPizza build() {
            return null;
        }

        @Override
        protected Builder self() {
            return null;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
    }

    public static void main(String[] args) {
        String o = "abc";
        String s = String.valueOf(o);
        String s1 = String.valueOf(o);
        System.out.println(System.identityHashCode(o));
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s1));
        try {

        } catch (Exception e) {

        }

    }
}
