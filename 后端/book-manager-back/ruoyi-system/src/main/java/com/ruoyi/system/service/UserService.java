package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.User;

/**
 * 用户 业务层
 * 
 * @author ruoyi
 */
public interface UserService
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Long userId);

    /**
     * 校验用户名称是否唯一
     * 
     * @param user 用户信息
     * @return 结果
     */
    public boolean checkUserNameUnique(User user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean checkPhoneUnique(User user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean checkEmailUnique(User user);

    /**
     * 校验用户是否允许操作
     * 
     * @param user 用户信息
     */
    public void checkUserAllowed(User user);

    /**
     * 校验用户是否有数据权限
     * 
     * @param userId 用户id
     */
    public void checkUserDataScope(Long userId);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 注册用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(User user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 修改用户状态
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserStatus(User user);

    /**
     * 修改用户基本信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserProfile(User user);

    /**
     * 修改用户头像
     * 
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetPwd(User user);

    /**
     * 重置用户密码
     * 
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String userName, String password);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);
}
