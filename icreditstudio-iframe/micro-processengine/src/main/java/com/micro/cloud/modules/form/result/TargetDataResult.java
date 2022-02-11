package com.micro.cloud.modules.form.result;

/**
 * 〈目标字段查询结果〉
 *
 * @author roy
 * @create 2021/12/16
 * @since 1.0.0
 */
public class TargetDataResult {

  private String businessId;

  private Object value;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TargetDataResult{" +
            "businessId='" + businessId + '\'' +
            ", value=" + value +
            '}';
    }
}
