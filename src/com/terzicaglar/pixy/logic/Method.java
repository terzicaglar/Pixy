package com.terzicaglar.pixy.logic;

import java.util.ArrayList;


public class Method {
    private ArrayList<Integer> integers;
    private ArrayList<String> strings;
    private String name;

    public Method(ArrayList<Integer> integers, ArrayList<String> strings, String name) {
        this.setIntegers(integers);
        this.setStrings(strings);
        this.name = name;
    }

    public Method(String name) {
        // TODO Auto-generated constructor stu
        this.name = name;
        integers = new ArrayList<Integer>();
        strings = new ArrayList<String>();
    }

    public void addInteger(int i) {
        integers.add(i);
    }

    public void addString(String s) {
        strings.add(s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }
}
