package com.why.boot.controller;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: BaseController
 * @CreateTime: 2023/3/10 20:26
 */
import  com.why.boot.controller.ex.*;
import  com.why.boot.service.ex.*;
import  com.why.boot.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制层里面类的基类
 */
public class BaseController {

    //操作成功的状态码
    public static int state = 200;

    /**
     * 1.@ExceptionHandler表示该方法用于处理捕获抛出的异常
     * 2.什么样的异常才会被这个方法处理呢?所以需要ServiceException.class,这样
     * 的话只要是抛出ServiceException异常就会被拦截到handleException方法,此
     * 时handleException方法就是请求处理方法,返回值就是需要传递给前端的数据
     * 3.被ExceptionHandler修饰后如果项目发生异常,那么异常对象就会被自动传递
     * 给此方法的参数列表上,所以形参就需要写Throwable e用来接收异常对象
     */

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof SysUserNameDuplicatedException) {
            result.setState(3009);
            result.setMessage("用户名已经被占用的异常");
        }else if (e instanceof SysUserAccountDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户账号已经被占用的异常");
        } else if (e instanceof SysUserAccountNotFoundException) {
            result.setState(4001);
            result.setMessage("用户账号不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户名密码错误的异常");
        } else if (e instanceof SysUserAccountDisabledException) {
            result.setState(4003);
            result.setMessage("用户名账号不可用");
        }else if (e instanceof LogoutFailedException) {
            result.setState(4005);
            result.setMessage("注销登录失败异常");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常");
        }else if(e instanceof CollectSysCourseIsExistedException){
            result.setState(5003);
            result.setMessage("收藏的课程已存在的异常");
        } else if(e instanceof CollectSysCourseIsNotExistedException){
            result.setState(5004);
            result.setMessage("课程未收藏的异常");
        } else if (e instanceof InvalidPathException) {
            result.setState(6000);
            result.setMessage("文件路径非法的异常");
        } else if (e instanceof FileNotFoundIOException) {
            result.setState(6001);
            result.setMessage("文件不存在的异常");
        } else if (e instanceof FileTypeNotImageException) {
            result.setState(6002);
            result.setMessage("文件类型不是图片的异常");
        } else if (e instanceof FileSaveFailedException) {
            result.setState(6003);
            result.setMessage("文件保存失败的异常");
        }else if (e instanceof FileIsEmptyException) {
            result.setState(6004);
            result.setMessage("上传文件为空的异常");
        }
        return result;
    }

}