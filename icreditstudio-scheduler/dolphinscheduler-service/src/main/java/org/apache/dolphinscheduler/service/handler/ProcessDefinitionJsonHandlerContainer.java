package org.apache.dolphinscheduler.service.handler;

import org.apache.dolphinscheduler.service.AbstractMapContainer;

/**
 * @author Peng
 */
public class ProcessDefinitionJsonHandlerContainer extends AbstractMapContainer<String, ProcessDefinitionJsonHandler> {

    private static ProcessDefinitionJsonHandlerContainer instance = new ProcessDefinitionJsonHandlerContainer();

    private ProcessDefinitionJsonHandlerContainer() {
    }

    public static ProcessDefinitionJsonHandlerContainer getInstance() {
        return instance;
    }

    public static ProcessDefinitionJsonHandler get(String dialect) {
        return getInstance().find(dialect);
    }
}
