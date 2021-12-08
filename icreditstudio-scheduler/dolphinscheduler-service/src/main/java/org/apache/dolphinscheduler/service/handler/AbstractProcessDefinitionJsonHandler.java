package org.apache.dolphinscheduler.service.handler;

import cn.hutool.json.JSONUtil;
import org.apache.dolphinscheduler.common.model.Configuration;

/**
 * @author Peng
 */
public abstract class AbstractProcessDefinitionJsonHandler implements ProcessDefinitionJsonHandler {

    @Override
    public void register() {
        ProcessDefinitionJsonHandlerContainer.getInstance().put(getDialect(), this);
    }

    public Object getValue(String json, String path) {
        Configuration from = Configuration.from(json);
        Object js = from.get("tasks[0].params.json");
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        return content.get(path);
    }

    public Configuration setValue(String json, String path, String newValue) {
        Configuration from = Configuration.from(json);
        Object js = from.get("tasks[0].params.json");
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        content.set(path, newValue);

        from.set("tasks[0].params.json", content.toJSON());
        return from;
    }
}
