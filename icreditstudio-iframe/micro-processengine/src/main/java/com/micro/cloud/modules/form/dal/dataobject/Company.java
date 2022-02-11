package com.micro.cloud.modules.form.dal.dataobject;

/**
 * author gongjl
 *
 * @description:
 * @date 2021/7/24  10:15
 */


public class Company {

    private String id;

    private String companyName;

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
