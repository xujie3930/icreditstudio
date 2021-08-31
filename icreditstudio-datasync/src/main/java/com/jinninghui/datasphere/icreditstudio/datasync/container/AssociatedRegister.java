package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedType;

import java.util.List;

/**
 * @author Peng
 */
public interface AssociatedRegister extends DialectKeyRegister {
    /**
     * 获取类型
     *
     * @return
     */
    List<AssociatedType> getAssocTypes();

    /**
     * 获取关联条件
     *
     * @return
     */
    List<String> getAssocConditions();
}
