package com.why.boot.controller.user;

import com.why.boot.bean.SysCarousel;
import com.why.boot.bean.SysUser;
import com.why.boot.controller.BaseController;
import com.why.boot.service.SysCarouselService;
import com.why.boot.service.SysCourseService;
import com.why.boot.service.SysUserService;
import com.why.boot.service.SysVideoService;
import com.why.boot.service.ex.*;
import com.why.boot.utils.IdWorker;
import com.why.boot.utils.JsonResult;
import com.why.boot.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: IndexController
 * @CreateTime: 2023/3/3 10:14
 */

@Slf4j
@Controller
public class IndexController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    SysVideoService sysVideoService;

    @Autowired
    SysCarouselService sysCarouselService;

    IdWorker idWorker=new IdWorker(1,1,1);

    /**
     * 进入登录页面
     * @return
     */
    @GetMapping(value={"/","/toLogin"})
    public String toLogin(){
        return "user/login";
    }

    /**
     * 进入注册页面
     */
    @GetMapping("/toRegister")
    public String toRegister(){
        return "user/register";
    }


    /**
     * 进入修改密码界面
     */
    @GetMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "user/update_password";
    }


    @ResponseBody
    @PostMapping("/logout")
    public JsonResult<Void> logout(HttpSession session, Model model){
        log.info("注销登录，移除session域中的用户信息，sysUser：{}",session.getAttribute("sysUser"));
        session.removeAttribute("sysUser");
        if(session.getAttribute("sysUser") != null){
            log.info("注销登录失败");
            state=handleException(new LogoutFailedException()).getState();
        }else{
            log.info("注销登录成功！");
            state=200;
        }
        return new JsonResult<>(state);
    }


    /**
     *进行登录
     */
    @ResponseBody
    @PostMapping("/login")
    public JsonResult<Map>  login(String account, String password, HttpSession session) {
        Map<String,Object> objectMap=new HashMap<>();
        SysUser sysUser = sysUserService.getSysUserByAccount(account);
        if(sysUser==null){
            log.info("用户账号不存在！");
            state=handleException(new SysUserAccountNotFoundException()).getState();
        }else{
            if(sysUser.getUserType().equals("learner")){
                //获取系统课程一级标签集合
                List<Object> firstLabelList=sysCourseService.getCourseFirstLabelList();
                session.setAttribute("firstLabelList",firstLabelList);
                //随机获取固定数量系统轮播图
                List<SysCarousel> sysCarouselRandom = sysCarouselService.getSysCarouselRandom(3);
                session.setAttribute("myCarousel",sysCarouselRandom);
            }
            if(!sysUser.isUserEnabled()){
                log.info("用户账号不可用！");
                state=handleException(new SysUserAccountDisabledException()).getState();
            }else{
                String salt = sysUser.getSalt();
                if(salt!=null){
                    String oldPassword = sysUser.getPassword();
                    String newMd5Password = sysUserService.getMD5Password(password, salt);
                    if(newMd5Password.equals(oldPassword)){
                        state=200;
                        log.info("登录成功！");
                        session.setAttribute("sysUser",sysUser);
                        session.setAttribute("md5Password",password);
                        sysUserService.updateLastLoginTimeByAccount(account,WebUtil.dateToStrong(new Date(),WebUtil.DATETIME));
                        log.info("用户({}) 登录时间为：{}",sysUser.getUserName(),WebUtil.dateToStrong(new Date(),WebUtil.DATETIME));
                        objectMap.put("view",judgeType(sysUser.getUserType()));
                    }else{
                        log.info("用户密码错误！");
                        state=handleException(new PasswordNotMatchException()).getState();
                    }
                }
            }
        }
        objectMap.put("sysUser",sysUser);
        log.info("当前用户信息：{}",sysUser);
        log.info("当前操作状态码是：{}",state);
        return new JsonResult<>(state, objectMap);
    }


    @ResponseBody
    @PostMapping("/updatePassword")
    public JsonResult<Void> updatePassword(String account,String password){
        SysUser sysUser = sysUserService.getSysUserByAccount(account);
        log.info("密码修改前用户信息：{}",sysUser);
        if(sysUser==null){
            log.info("用户账号不存在！");
            state=handleException(new SysUserAccountNotFoundException()).getState();
        }else{
            String salt=sysUser.getSalt();
            if(sysUserService.updatePasswordByAccount(account,password,salt)){
                SysUser sysUserByAccount = sysUserService.getSysUserByAccount(account);
                String md5Password=sysUserService.getMD5Password(sysUserByAccount.getPassword(),salt);
                if(md5Password.equals(password)){
                    state=200;
                    log.info("密码修改成功！");
                    log.info("密码修改后用户信息：{}",sysUserByAccount);
                }
            }else{
                log.info("密码修改失败！");
                state=handleException(new UpdateException()).getState();
            }
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/updateUserName")
    public JsonResult<Void> updateUserName(String account,String newUserName){
        SysUser sysUser = sysUserService.getSysUserByAccount(account);
        log.info("用户改名修改前用户信息：{}",sysUser);
        if(sysUser==null){
            log.info("用户账号不存在！");
            state=handleException(new SysUserAccountNotFoundException()).getState();
        }else{
            if(sysUserService.updateUserNameByAccount(account,newUserName)){
                SysUser sysUserByAccount = sysUserService.getSysUserByAccount(account);
                if(newUserName.equals(sysUserByAccount.getUserName())){
                    state=200;
                    log.info("用户名修改成功！");
                    log.info("用户名修改后用户信息：{}",sysUserByAccount);
                }
            }else{
                log.info("用户名修改失败！");
                state=handleException(new UpdateException()).getState();
            }
        }
        return new JsonResult<>(state);
    }



    @ResponseBody
    @PostMapping("/register")
    public JsonResult<Void> register(String account,String username,String type,String major,String password){
        if(sysUserService.sysUserIsExistedByAccount(account)){
            log.info("用户账号已存在！");
            state=handleException(new SysUserAccountDuplicatedException()).getState();
        }else if(sysUserService.sysUserIsExistedByUserName(username)){
            log.info("用户名已被占用！");
            state=handleException(new SysUserNameDuplicatedException()).getState();
        }
        else{
            //1.随机生成一个盐值(大写的随机字符串)
            String salt = UUID.randomUUID().toString().toUpperCase();
            //2.将密码和盐值作为一个整体进行加密处理
            String md5Password = sysUserService.getMD5Password(password, salt);
            SysUser sysUser = new SysUser(idWorker.nextId(),account,username,md5Password,type,major,null,null,
                    true, WebUtil.dateToStrong(new Date(),WebUtil.DATETIME),null,salt);
            if(sysUserService.save(sysUser)){
                state=200;
                log.info("注册成功！");
            }else{
                log.info("注册失败！");
                state=handleException(new InsertException()).getState();
            }
        }
        return new JsonResult<>(state);
    }




    public String judgeType(String userType){
        switch (userType){
            case "learner":
                return "learner/learner_main_view";
            case "admin":
            case "super_admin":
                return "admin/admin_main_view";
        }
        return "login";
    }


    /**进行页面跳转*/
    @GetMapping("admin/admin_main_view")
    public String admin_main_view(){
        return "admin/admin_main_view";
    }


    @GetMapping("learner/learner_main_view")
    public String learner_main_view(){
        return "learner/learner_main_view";
    }




}