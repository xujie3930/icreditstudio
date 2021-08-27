package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peng
 */
public class Correlations {
    private Map<String, List<AssociatedType>> assoc = new ConcurrentHashMap<>();
    private Map<String, List<String>> conditions = new ConcurrentHashMap<>();
    private static Correlations instance = new Correlations();

    private Correlations() {
    }

    public static Correlations getInstance() {
        return instance;
    }

    public Map<String, List<AssociatedType>> getAssoc() {
        return assoc;
    }

    public Map<String, List<String>> getConditions() {
        return conditions;
    }

    public static List<AssociatedType> findAssocTypes(String dialect) {
        return getInstance().getAssoc().get(dialect);
    }

    public static List<String> findAssocConditions(String dialect) {
        return getInstance().getConditions().get(dialect);
    }
}
