package com.sun.vuebackground.controller;

import com.sun.vuebackground.dao.TB_user_dao;
import com.sun.vuebackground.entity.DataResult;
import com.sun.vuebackground.entity.TB_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Optional;

@RestController
@RequestMapping(value = "vue/user")
public class TB_user_controller {

    @Autowired
    private TB_user_dao tb_user_dao;

    //登陆
    @RequestMapping(value = "login")
    public DataResult login(@ModelAttribute TB_user tb_user, HttpSession session) {
        DataResult dataResult = new DataResult();
        if (tb_user.getUsername() == null || tb_user.getPassword() == null) {
            dataResult.setCode(1);
            dataResult.setMsg("缺少参数！");
            return dataResult;
        }
        TB_user user = tb_user_dao.findByUsername(tb_user.getUsername());
        //MD5是不可以逆的，将传来的password进行MD5加密，与数据中的用户信息比对
        String md5Password = DigestUtils.md5DigestAsHex(tb_user.getPassword().getBytes());
        if (!user.getPassword().equals(md5Password)) {
            dataResult.setCode(1);
            dataResult.setMsg("账户或密码错误！");
        } else if (user.isStatus()) {
            dataResult.setCode(1);
            dataResult.setMsg("用户未激活！");
        } else {
            dataResult.setMsg("登陆成功");
        }
        session.setAttribute("user", user);
        return dataResult;
    }

    //使用id查询用户
    @GetMapping(value = "/{id}")
    public DataResult getUser(@PathVariable String id) {
        DataResult dataResult = new DataResult();
        if (tb_user_dao.countById(id) != 0) {
            dataResult.setData(tb_user_dao.findById(id));
            dataResult.setMsg("查询成功");
        } else {
            dataResult.setCode(1);
            dataResult.setMsg("未查询到数据");
        }
        return dataResult;
    }

    //新增用户
    @PostMapping(value = "")
    public DataResult postUser(@ModelAttribute TB_user tb_user) {
        DataResult dataResult = new DataResult();
        if (tb_user_dao.countByUsername(tb_user.getUsername()) != 0) {
            dataResult.setMsg("邮箱已经注册！");
            dataResult.setCode(1);
            return dataResult;
        }
        //MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(tb_user.getPassword().getBytes());
        tb_user.setPassword(md5Password);
        tb_user.setRole(3);
        tb_user.setStatus(false);
        if (tb_user_dao.save(tb_user) != null) {
            dataResult.setMsg("注册成功！");
        }
        return dataResult;
    }


    //修改用户
    @PutMapping(value = "")
    public DataResult putUser(@ModelAttribute TB_user tb_user) {
        DataResult dataResult = new DataResult();
        if (tb_user.getId() == null) {
            dataResult.setCode(1);
            dataResult.setMsg("缺少参数！");
            return dataResult;
        }
        if (tb_user_dao.save(tb_user) != null) {
            dataResult.setMsg("修改成功！");
        } else {
            dataResult.setCode(1);
            dataResult.setMsg("修改失败！");
        }
        return dataResult;
    }

    //删除用户
    @DeleteMapping(value = "/{id}")
    public DataResult deleteUser(@PathVariable("id") String id) {
        DataResult dataResult = new DataResult();
        tb_user_dao.deleteById(id);
        dataResult.setMsg("删除成功！");
        return dataResult;
    }
}
