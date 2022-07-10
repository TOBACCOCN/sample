package com.example.sample;

public class NyPizza extends Pizza {

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
        String s1 = new String(o);
        System.out.println(System.identityHashCode(o));
        System.out.println(System.identityHashCode(s));
        System.out.println(System.identityHashCode(s1));
        o = "test";
        System.out.println(System.identityHashCode(o));

        String str1 = "abc";
        String str2 = "ab" + "c";
        boolean f = str1 == str2;
        System.out.println(f);

        str1 = "abc";
        str2 = "ab";
        String str3 = str2 + "c";
        f = str1 == str3;
        System.out.println(f);

        try {

        } catch (Exception e) {

        }

    }
}
