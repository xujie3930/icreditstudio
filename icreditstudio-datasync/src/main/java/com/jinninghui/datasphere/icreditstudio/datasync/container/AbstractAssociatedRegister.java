package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;

/**
 * @author Peng
 */
public abstract class AbstractAssociatedRegister implements AssociatedRegister {
    public AbstractAssociatedRegister() {
        register();
    }

    @Override
    public void register() {
        AssociatedDialectKeyContainer instance = AssociatedDialectKeyContainer.getInstance();
        Associated associated = new Associated();
        associated.setAssocTypes(this.getAssocTypes());
        associated.setAssocConditions(this.getAssocConditions());
        instance.put(this.getDialect(), associated);
    }
}
