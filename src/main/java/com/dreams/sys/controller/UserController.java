package com.dreams.sys.controller;

import com.dreams.sys.bo.UserBo;
import com.dreams.sys.po.UserPo;
import com.dreams.sys.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dreams-linxi
 * @date 2020/5/10 15:29
 */
@RestController
@RequestMapping("/sys/user")
public class UserController
{
    @Resource
    private UserService userService;

    @RequestMapping("/getAllUser.json")
    public Map<String, Object> getAllUser(UserBo userBo) {
        return this.userService.getAllUser(userBo);
    }

    @RequestMapping("/addUser.json")
    public Map<String, Object> addUser(UserPo userPo) {
        return this.userService.addUser(userPo);
    }

    @RequestMapping("/getUserByUserId.json")
    public Map<String, Object> getUserByUserId(String userId) {

        Map<String, Object> result = new HashMap<>();

        UserPo userByUserId = this.userService.getUserByUserId(userId);
        int row = 0;
        if (userByUserId != null){
            row = 1;
        }
        result.put("row",row);
        return result;
    }

    @RequestMapping("/updateUserByUserId.json")
    public Map<String, Object> updateUserByUserId(UserPo userPo) {
        return this.userService.updateUserByUserId(userPo);
    }

    @RequestMapping("/deleteUserByUserIds.json")
    public Map<String, Object> deleteUserByUserIds(@RequestParam("userIds[]") String[] userIds) {
        return this.userService.deleteUserByUserIds(userIds);
    }


    @RequestMapping("/getUserByPhone.json")
    public Map<String, Object> getUserByPhone(@RequestParam("phone") String phone) {
        List<UserPo> userByPhone = this.userService.getUserByPhone(phone);
        Map<String, Object> result = new HashMap<>();
        int row = userByPhone.size();
        result.put("row",row);
        return result;
    }
}
