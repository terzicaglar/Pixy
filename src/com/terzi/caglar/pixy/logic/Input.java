package com.terzi.caglar.pixy.logic;

public class Input {
    private int type /* 0 int, 1 string*/, min, max;
    private String[] acceptedWords;

    public Input(int type, int min, int max) {
        this.type = type;
        this.min = min;
        this.max = max;
        this.acceptedWords = null;
    }

    public Input(int type) {
        this.type = type;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.acceptedWords = null;
    }

    public Input(int type, String[] acceptedWords) {
        this.type = type;
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.acceptedWords = acceptedWords;
    }

    public boolean checkInput(String input) {
        if (type == 0 && (!isInteger(input) || Integer.parseInt(input) < min || Integer.parseInt(input) > max))
            return false;
        else if (type == 0)
            return true;
        else if (type == 1) {
            if (acceptedWords != null) {
                for (int i = 0; i < acceptedWords.length; i++)
                    if (acceptedWords[i].equalsIgnoreCase(input))
                        return true;
            } else
                return true;
        }
        return false;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String[] getAcceptedWords() {
        return acceptedWords;
    }

    public void setAcceptedWords(String[] acceptedWords) {
        this.acceptedWords = acceptedWords;
    }
}
