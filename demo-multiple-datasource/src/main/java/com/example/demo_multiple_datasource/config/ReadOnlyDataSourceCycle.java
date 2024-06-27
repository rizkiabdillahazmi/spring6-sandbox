package com.example.demo_multiple_datasource.config;

import lombok.Setter;

import java.util.List;

public class ReadOnlyDataSourceCycle<T> {
    @Setter
    private List<T> readOnlyDataSourceLookupKeys;
    private int index = 0;

    public T getReadOnlyDataSourceLookupKey() {
        if (index + 1 >= readOnlyDataSourceLookupKeys.size()) {
            index = -1;
        }
        return readOnlyDataSourceLookupKeys.get(++index);
    }
}
