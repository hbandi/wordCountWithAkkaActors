package com.wc.beans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LineNoWordMapper {

    private ConcurrentHashMap<Integer, Map<String, WordAndCount>> lineNoVsWordMap;

    public LineNoWordMapper() {
        lineNoVsWordMap = new ConcurrentHashMap<Integer, Map<String, WordAndCount>>();
    }

    public ConcurrentHashMap<Integer, Map<String, WordAndCount>> getLineNoVsWordMap() {
        return lineNoVsWordMap;
    }

    public void setLineNoVsWordMap(ConcurrentHashMap<Integer, Map<String, WordAndCount>> lineNoVsWordMap) {
        this.lineNoVsWordMap = lineNoVsWordMap;
    }
}
