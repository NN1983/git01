package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.mapper.UserMapper;
import com.yjxxt.crm.model.UserModel;
import com.yjxxt.crm.query.UserQuery;
import com.yjxxt.crm.utils.AssertUtil;
import com.yjxxt.crm.utils.Md5Util;
import com.yjxxt.crm.utils.PhoneUtil;
import com.yjxxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    public UserModel userLogin(String userName,String userPwd){

        checkUserLoginParam(userName,userPwd);
        //用户不存在
        User temp = userMapper.selectUserByName(userName);
        AssertUtil.isTrue(temp == null,"用户不存在");
        //用户密码是否正确
        checkUserPwd(userPwd,temp.getUserPwd());

        return builderUserInfo(temp);
    }

    /**
     * 构造返回目标对象
     * @param temp
     * @return
     */
    private UserModel builderUserInfo(User temp) {

        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(temp.getId()));
        userModel.setUserName(temp.getUserName());
        userModel.setTrueName(temp.getTrueName());

        return userModel;
    }

    /**
     * 校验用户名密码非空
     * @param userName
     * @param userPwd
     */
    private void checkUserLoginParam(String userName, String userPwd) {

        //用户名非空
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        //用户密码非空
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空");
    }

    /**
     * 校验密码
     * @param userPwd
     * @param userPwd1
     */
    private void checkUserPwd(String userPwd, String userPwd1) {

        userPwd = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(userPwd1),"用户密码不正确");
    }

    public void updateUserPassword(Integer userId,String oldPassword,
                                   String newPassword,String confirmPassword){
        //通过userId获取用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //参数校验
        checkPasswordParams(user,oldPassword,newPassword,confirmPassword);
        //设置用户新密码
        user.setUserPwd(Md5Util.encode(newPassword));
        //执行更新操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"用户密码更新失败！");
    }

    private void checkPasswordParams(User user, String oldPassword,
                                     String newPassword, String confirmPassword) {
        //user对象非空验证
        AssertUtil.isTrue(null == user,"用户未登录或不存在");
        //原始密码非空验证
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码");
        //原密码是否正确
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原密码不正确");
        //新密码非空验证
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码");
        //新密码与原密码不能相同
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码与原密码不能相同");
        //确认密码非空验证
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码");
        //新密码与确认密码要一致
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码与确认密码不一致");
    }

    public List<Map<String,Object>> querySales(){

        return userMapper.selectSales();
    }

    /**
     * 用户模块的列表查询
     * @param userQuery
     * @return
     */
    public Map<String,Object> findUserByParams(UserQuery userQuery){

        Map<String,Object> map = new HashMap<>();

        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());

        PageInfo<User> plist = new PageInfo<>(selectByParams(userQuery));

        map.put("code",0);
        map.put("msg","success");
        map.put("count",plist.getTotal());
        map.put("data",plist.getList());

        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user){

        //验证
        checkUser(user.getUserName(),user.getEmail(),user.getPhone());

        User temp = userMapper.selectUserByName(user.getUserName());
        AssertUtil.isTrue(null != temp,"用户已存在");
        //设定默认
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //密码加密
        user.setUserPwd(Md5Util.encode("123456"));
        //验证是否添加成功
        AssertUtil.isTrue(insertHasKey(user) < 1,"添加失败");
    }

    private void checkUser(String userName, String email, String phone) {

        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"请输入合法手机号");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUser(User user){

        User temp = userMapper.selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(null == temp,"待修改的记录不存在");
        //参数验证
        checkUser(user.getUserName(),user.getEmail(),user.getPhone());

        User temp2 = userMapper.selectUserByName(user.getUserName());
        AssertUtil.isTrue(null != temp2 && !(temp2.getId().equals(user.getId())),"用户已存在");
        //设定默认值
        user.setUpdateDate(new Date());
        //判断修改是否成功
        AssertUtil.isTrue(updateByPrimaryKeySelective(user) < 1,"修改失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUserIds(Integer[] ids){
        //验证
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择删除数据");
        //判断删除成功与否
        AssertUtil.isTrue(deleteBatch(ids)<1,"删除失败了");
    }
}
