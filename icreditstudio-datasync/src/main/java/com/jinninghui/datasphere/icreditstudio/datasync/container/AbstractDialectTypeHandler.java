package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.FormatterDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;

/**
 * @author Peng
 */
public abstract class AbstractDialectTypeHandler implements AssociatedRegister, Formatter<AssociatedFormatterVo> {

    public AbstractDialectTypeHandler() {
        register();
    }

    @Override
    public void register() {
        //注册formatter
        FormatterDialectKeyContainer FormatterInstance = FormatterDialectKeyContainer.getInstance();
        FormatterInstance.put(this.getDialect(), this);
        //注册关系
        AssociatedDialectKeyContainer AssociatedInstance = AssociatedDialectKeyContainer.getInstance();
        Associated associated = new Associated();
        associated.setAssocTypes(this.getAssocTypes());
        associated.setAssocConditions(this.getAssocConditions());
        AssociatedInstance.put(this.getDialect(), associated);
    }
}
