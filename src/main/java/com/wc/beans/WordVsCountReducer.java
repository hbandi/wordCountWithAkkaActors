package com.wc.beans;

import java.util.HashMap;
import java.util.Map;

public class WordVsCountReducer {

    private Map<String, Integer> wordVsCount;

    public WordVsCountReducer() {
        wordVsCount = new HashMap<>();
    }

    public Map<String, Integer> getWordVsCount() {
        return wordVsCount;
    }

    public void setWordVsCount(Map<String, Integer> wordVsCount) {
        this.wordVsCount = wordVsCount;
    }


}
