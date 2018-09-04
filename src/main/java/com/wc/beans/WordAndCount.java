package com.wc.beans;


public class WordAndCount {

    private String word;
    private int count;

    public WordAndCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordAndCount that = (WordAndCount) o;
        return this.word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return this.word.hashCode();
    }

    @Override

    public String toString() {

        return "WordAndCount{" +
                "word='" + word + '\'' +
                ", count=" + count +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
