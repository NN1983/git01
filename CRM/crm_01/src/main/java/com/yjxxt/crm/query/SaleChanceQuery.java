package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {

    private String customerName;
    private String createMan;
    private String state;

    public SaleChanceQuery() {
    }

    public SaleChanceQuery(String customerName, String createMan, String state) {
        this.customerName = customerName;
        this.createMan = createMan;
        this.state = state;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleChanceQuery that = (SaleChanceQuery) o;

        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (createMan != null ? !createMan.equals(that.createMan) : that.createMan != null) return false;
        return state != null ? state.equals(that.state) : that.state == null;
    }

    @Override
    public int hashCode() {
        int result = customerName != null ? customerName.hashCode() : 0;
        result = 31 * result + (createMan != null ? createMan.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SaleChanceQuery{" +
                "customerName='" + customerName + '\'' +
                ", createMan='" + createMan + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
