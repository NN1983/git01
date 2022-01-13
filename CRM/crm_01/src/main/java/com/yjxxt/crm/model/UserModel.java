package com.yjxxt.crm.model;

public class UserModel {

    private String userIdStr;
    private String userName;
    private String trueName;

    public UserModel() {
    }

    public UserModel(String userIdStr, String userName, String trueName) {
        this.userIdStr = userIdStr;
        this.userName = userName;
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (userIdStr != null ? !userIdStr.equals(userModel.userIdStr) : userModel.userIdStr != null) return false;
        if (userName != null ? !userName.equals(userModel.userName) : userModel.userName != null) return false;
        return trueName != null ? trueName.equals(userModel.trueName) : userModel.trueName == null;
    }

    @Override
    public int hashCode() {
        int result = userIdStr != null ? userIdStr.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (trueName != null ? trueName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userIdStr='" + userIdStr + '\'' +
                ", userName='" + userName + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }
}
