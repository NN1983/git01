package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

public class UserQuery extends BaseQuery {

    private String userName;
    private String email;
    private String phone;

    public UserQuery() {
    }

    public UserQuery(String userName, String email, String phone) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserQuery userQuery = (UserQuery) o;

        if (userName != null ? !userName.equals(userQuery.userName) : userQuery.userName != null) return false;
        if (email != null ? !email.equals(userQuery.email) : userQuery.email != null) return false;
        return phone != null ? phone.equals(userQuery.phone) : userQuery.phone == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
