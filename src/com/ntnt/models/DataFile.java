package com.ntnt.models;

import com.ntnt.annotations.IsCounted;

@IsCounted(name = "count")
public class DataFile implements Comparable<DataFile> {
    private String word;
    private int count;
    private int[] row;

    public DataFile() {
    }

    public DataFile(String word){
        this.word = word;
    }

    public DataFile(String word, int[] row) {
        this.word = word;
        this.row = row;
        this.count = 1;
    }

    public DataFile(String word, int count, int[] row) {
        this.word = word;
        this.count = count;
        this.row = row;
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

    public int[] getRow() {
        return row;
    }

    public void setRow(int[] row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "DataFile{" +
                "word='" + word + '\'' +
                ", count=" + count +
                ", row=" + row +
                '}';
    }

    @Override
    public int compareTo(DataFile o) {
        return this.word.compareTo(((DataFile)o).getWord());
    }
}
