package com.example.demo_multiple_datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Map;

public class ReplicationRoutingDatasource extends AbstractRoutingDataSource {
    private static final String READ = "read";
    private static final String WRITE = "write";
    private final ReadOnlyDataSourceCycle<String> readOnlyDataSourceCycle = new ReadOnlyDataSourceCycle<>();

    @Override
    public void setTargetDataSources(@NonNull Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        List<String> readOnlyDataSourceLookupKeys = targetDataSources.keySet()
                .stream()
                .map(String::valueOf)
                .filter(lookupKey -> lookupKey.contains(READ)).toList();

        readOnlyDataSourceCycle.setReadOnlyDataSourceLookupKeys(readOnlyDataSourceLookupKeys);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? readOnlyDataSourceCycle.getReadOnlyDataSourceLookupKey()
                : WRITE;
    }
}
