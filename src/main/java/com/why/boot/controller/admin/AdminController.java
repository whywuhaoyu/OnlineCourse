package com.why.boot.controller.admin;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.boot.bean.SysCourse;
import com.why.boot.bean.SysQuestion;
import com.why.boot.bean.SysUser;
import com.why.boot.bean.SysVideo;
import com.why.boot.controller.BaseController;
import com.why.boot.mapper.SysUserMapper;
import com.why.boot.service.*;
import com.why.boot.service.ex.*;
import com.why.boot.utils.CountUtil;
import com.why.boot.utils.IdWorker;
import com.why.boot.utils.JsonResult;
import com.why.boot.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: AdminController
 * @CreateTime: 2023/3/20 14:39
 */

@Slf4j
@Controller
public class AdminController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    SysVideoService sysVideoService;

    @Autowired
    SysQuestionService sysQuestionService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private HdfsService hdfsService;

    @Autowired
    CountUtil countUtil;

    IdWorker idWorker=new IdWorker(1,1,1);

    @GetMapping("/toAdminMainView")
    public String toAdminMainView(){
        return "admin/admin_main_view";
    }

    @GetMapping("/toUserManage")
    public String toUserManage(){
        return "admin/admin_user_manage";
    }

    @GetMapping("/toCourseManage")
    public String toCourseManage(){
        return "admin/admin_course_manage";
    }

    @GetMapping("/toVideoManage")
    public String toVideoManage(){
        return "admin/admin_video_manage";
    }

    @GetMapping("/toQuestionManage")
    public String toQuestionManage(){
        return "admin/admin_question_manage";
    }

    @PostMapping("/admin/getDataStatistics")
    public String getDataStatistics(Model model){
        //统计用户数量
        int sysUserCount = sysUserService.count();
        model.addAttribute("sysUserCount",sysUserCount);
        log.info("用户数量为：{}人",sysUserCount);

        //统计课程数量
        int sysCourseCount = sysCourseService.count();
        model.addAttribute("sysCourseCount",sysCourseCount);
        log.info("课程数量为：{}个",sysCourseCount);

        //统计视频数量
        int sysVideoCount = sysVideoService.count();
        model.addAttribute("sysVideoCount",sysVideoCount);
        log.info("视频数量为：{}个",sysVideoCount);

        //统计题目数量
        int sysQuestionCount=sysQuestionService.count();
        model.addAttribute("sysQuestionCount",sysQuestionCount);
        log.info("题目数量为：{}个",sysQuestionCount);
        return "admin/data_statistics_show";
    }

    @PostMapping("/admin/getUserDataTable")
    public String getUserDataTable(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber ,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,String isNotEnabled, String isEnabled,
                              @RequestParam(value = "userType",defaultValue = "all") String userType, String userCondition, Model model, HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        if(isNotEnabled!=null && !Objects.equals(isNotEnabled, "") && Objects.equals(isEnabled, "")){
            sysUserQueryWrapper.eq("user_enabled",0);
        }else if(isEnabled!=null && !Objects.equals(isEnabled, "") && Objects.equals(isNotEnabled, "")){
            sysUserQueryWrapper.eq("user_enabled",1);
        }
        if(userType.equals("all")){
            sysUserQueryWrapper.ne("user_id",sysUser.getUserId());
        }else{
            sysUserQueryWrapper.eq("user_type",userType);
        }
        if(userCondition!=null&& !userCondition.equals("")){
            sysUserQueryWrapper.lambda()
                    .and(
                            StringUtils.isNotBlank(userCondition),   //当courseCondition非空时，进行下面的条件组装
                            q -> q.like(SysUser::getUserName, userCondition)
                                    .or()
                                    .like(SysUser::getAccount, userCondition)
                                    .or()
                                    .like(SysUser::getUserMajor, userCondition)
                    );

        }
        Page<SysUser> userPage = new Page<>(pageNumber,pageSize);
        IPage<SysUser> page = sysUserService.page(userPage, sysUserQueryWrapper);

        //封装工具类参数
        model.addAttribute("countUtil",countUtil);

        //封装查询参数
        model.addAttribute("isNotEnabled",isNotEnabled);
        model.addAttribute("isEnabled",isEnabled);
        model.addAttribute("userType",userType);
        model.addAttribute("userCondition",userCondition);


        //封装分页参数
        model.addAttribute("sysUserList",page.getRecords());
        model.addAttribute("total",page.getTotal());
        model.addAttribute("pages",page.getPages());
        model.addAttribute("pre",page.getCurrent()-1);
        model.addAttribute("next",page.getCurrent()+1);
        model.addAttribute("cur",page.getCurrent());
        model.addAttribute("last",page.getPages());
        model.addAttribute("pageSize",pageSize);
        return "admin/user_data_table";
    }


    @ResponseBody
    @PostMapping("/admin/deleteUserDataByUserId")
    public JsonResult<Void> deleteUserDataByUserId(Long userId){
        if(sysUserService.removeById(userId)){
            state=200;
            log.info("删除用户信息成功！");
        }else{
            log.info("删除用户信息失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }


    @ResponseBody
    @PostMapping("/admin/uploadUserHeadImg")
    public JsonResult<Map> uploadUserHeadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            state =handleException(new FileIsEmptyException()).getState();
            log.info("上传文件不能为空！");
            return new JsonResult<>(state);
        }else{
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String fileName = UUID.randomUUID() + extension;
            try {
                Resource resource = resourceLoader.getResource("classpath:static/images/user/");
                String path=resource.getFile().getAbsolutePath();

                // 检查路径是否合法
                if (path.contains("..") || path.contains("*")) {
                    state= handleException(new InvalidPathException()).getState();
                    log.error("上传路径非法: {}", path);
                    return new JsonResult<>(state);
                }else if(!isImageFile(file)){
                    state= handleException(new FileTypeNotImageException()).getState();
                    log.error("文件类型不是图片！");
                    return new JsonResult<>(state);
                }else{
                    Path fileSavePath = Paths.get(path +"/"+fileName);
                    Files.write(fileSavePath, file.getBytes());
                    log.info("新图片路径：{}",fileSavePath);
                    String imgPath="/images/user/"+fileName;

                    Map<String,Object> objectMap=new HashMap<>();
                    objectMap.put("imgPath",imgPath);
                    return new JsonResult<>(state,objectMap);
                }
            }catch (FileNotFoundException e) {
                state=handleException(new FileNotFoundIOException()).getState();
                log.error("文件不存在！");
                return new JsonResult<>(state);
            }catch (IOException e) {
                state=handleException(new FileSaveFailedException()).getState();
                log.error("保存上传文件失败!");
                return new JsonResult<>(state);
            }
        }
    }


    @ResponseBody
    @PostMapping("/admin/addUser")
    public JsonResult<Void> addUser(String userAccount,String userName,String userMajor,String userPassword,String headImgPath){
        headImgPath=headImgPath.substring(2);
        if(sysUserService.sysUserIsExistedByAccount(userAccount)){
            log.info("用户账号已存在！");
            state=handleException(new SysUserAccountDuplicatedException()).getState();
        }else if(sysUserService.sysUserIsExistedByUserName(userName)){
            log.info("用户名已被占用！");
            state=handleException(new SysUserNameDuplicatedException()).getState();
        }
        else{
            //1.随机生成一个盐值(大写的随机字符串)
            String salt = UUID.randomUUID().toString().toUpperCase();
            //2.将密码和盐值作为一个整体进行加密处理
            String md5Password = sysUserService.getMD5Password(userPassword, salt);
            SysUser sysUser = new SysUser(idWorker.nextId(),userAccount,userName,md5Password,"learner",userMajor,null,null,
                    true, WebUtil.dateToStrong(new Date(),WebUtil.DATETIME),headImgPath,salt);
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

    @ResponseBody
    @PostMapping("/admin/editUser")
    public JsonResult<Void> editUser(Long userId,String account,String userName,String userMajor,String userType,boolean userEnabled){
        SysUser sysUser = sysUserService.getById(userId);
        UpdateWrapper<SysUser> sysUserUpdateWrapper=new UpdateWrapper<>();
        log.info("用户id：{},用户名：{}，用户专业：{}，用户类型：{}，是否可用：{}",userId,userName,userMajor,userType,userEnabled);
        if(Objects.equals(userMajor,"")||userMajor==null){
           userMajor=sysUser.getUserMajor();
        }
        if(Objects.equals(userType,"")||userType==null){
            userType= sysUser.getUserType();
        }
        if(sysUserService.updateUserById(userId,account,userName,userMajor,userType,userEnabled)){
            state=200;
            log.info("编辑成功！");
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("编辑失败！");

        }
        return new JsonResult<>(state);
    }









    @PostMapping("/admin/getCourseDataTable")
    public String getCourseDataTable(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber ,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                     @RequestParam(value = "firstLabel",defaultValue = "全部") String firstLabel,@RequestParam(value = "secondLabel",defaultValue = "全部") String secondLabel,
                                     String courseCondition, Model model, HttpSession session){
        QueryWrapper<SysCourse> sysCourseQueryWrapper=new QueryWrapper<SysCourse>();
        if(!firstLabel.equals("全部")){
            sysCourseQueryWrapper.eq("first_label",firstLabel);
        }
        if(!secondLabel.equals("全部")){
            sysCourseQueryWrapper.eq("second_label",secondLabel);
        }
        if(courseCondition!=null&& !courseCondition.equals("")){
            sysCourseQueryWrapper.lambda()
                    .and(
                            StringUtils.isNotBlank(courseCondition),   //当courseCondition非空时，进行下面的条件组装
                            q -> q.like(SysCourse::getCourseName, courseCondition)
                                    .or()
                                    .like(SysCourse::getCourseLevel, courseCondition)
                                    .or()
                                    .like(SysCourse::getCourseMajor, courseCondition)
                                    .or()
                                    .like(SysCourse::getThirdLabel, courseCondition)
                    );

        }

        Page<SysCourse> coursePage=new Page<>(pageNumber,pageSize);
        IPage<SysCourse> page=sysCourseService.page(coursePage,sysCourseQueryWrapper);

        //封装工具类参数
        model.addAttribute("countUtil",countUtil);


        //封装查询参数
        model.addAttribute("firstLabel",firstLabel);
        model.addAttribute("secondLabel",secondLabel);
        model.addAttribute("courseCondition",courseCondition);

        //封装分页参数
        model.addAttribute("sysCourseList",page.getRecords());
        model.addAttribute("total",page.getTotal());
        model.addAttribute("pages",page.getPages());
        model.addAttribute("pre",page.getCurrent()-1);
        model.addAttribute("next",page.getCurrent()+1);
        model.addAttribute("cur",page.getCurrent());
        model.addAttribute("last",page.getPages());
        model.addAttribute("pageSize",pageSize);
        return "admin/course_data_table";

    }



    @ResponseBody
    @PostMapping("/admin/deleteCourseDataByCourseId")
    public JsonResult<Void> deleteCourseDataByCourseId(Long courseId){
        if(sysCourseService.removeById(courseId)){
            state=200;
            log.info("删除课程信息成功！");
        }else{
            log.info("删除课程信息失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/admin/uploadCourseImg")
    public JsonResult<Map> uploadCourseImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            state =handleException(new FileIsEmptyException()).getState();
            log.info("上传文件不能为空！");
            return new JsonResult<>(state);
        }else{
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String fileName = UUID.randomUUID() + extension;
            try {
                Resource resource = resourceLoader.getResource("classpath:static/images/course/");
                String path=resource.getFile().getAbsolutePath();

                // 检查路径是否合法
                if (path.contains("..") || path.contains("*")) {
                    state= handleException(new InvalidPathException()).getState();
                    log.error("上传路径非法: {}", path);
                    return new JsonResult<>(state);
                }else if(!isImageFile(file)){
                    state= handleException(new FileTypeNotImageException()).getState();
                    log.error("文件类型不是图片！");
                    return new JsonResult<>(state);
                }else{
                    Path fileSavePath = Paths.get(path +"/"+fileName);
                    Files.write(fileSavePath, file.getBytes());
                    log.info("新图片路径：{}",fileSavePath);
                    String imgPath="/"+fileName;

                    Map<String,Object> objectMap=new HashMap<>();
                    objectMap.put("imgPath",imgPath);
                    return new JsonResult<>(state,objectMap);
                }
            }catch (FileNotFoundException e) {
                state=handleException(new FileNotFoundIOException()).getState();
                log.error("文件不存在！");
                return new JsonResult<>(state);
            }catch (IOException e) {
                state=handleException(new FileSaveFailedException()).getState();
                log.error("保存上传文件失败!");
                return new JsonResult<>(state);
            }
        }
    }


    @ResponseBody
    @PostMapping("/admin/addCourse")
    public JsonResult<Void> addCourse(String courseName,String courseDescription,String courseLevel,String courseBase,String courseMajor,
                                      String firstLabel,String secondLabel,String thirdLabel,String imgPath,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        int pos = imgPath.indexOf("course/"); // 查找 "course/" 的位置
        String newImgPath=null;
        if (pos >= 0) {
            newImgPath = "/"+imgPath.substring(pos + 7); // 获取 "course/" 后面的字符串
        }
        SysCourse sysCourse=new SysCourse(idWorker.nextId(),courseName,null,courseDescription,sysUser.getUserId(),WebUtil.dateToStrong(new Date(),WebUtil.DATETIME),
                courseLevel, courseBase, null,newImgPath, firstLabel, secondLabel, thirdLabel, true, courseMajor);
        if(sysCourseService.save(sysCourse)){
            state=200;
            log.info("添加成功！");
        }else{
            log.info("添加失败！");
            state=handleException(new InsertException()).getState();
        }
        return new JsonResult<>(state);
    }


    @ResponseBody
    @PostMapping("/admin/editCourse")
    public JsonResult<Void> editCourse(Long courseId,String courseName,String viewCount,String courseMajor,String secondLabel,String thirdLabel,boolean courseState){
        UpdateWrapper<SysCourse> sysCourseUpdateWrapper=new UpdateWrapper<>();
        sysCourseUpdateWrapper.eq("course_id",courseId).set("course_name",courseName).set("view_count",viewCount)
                        .set("course_major",courseMajor).set("second_label",secondLabel).set("third_label",thirdLabel)
                        .set("course_state",courseState);
        if( sysCourseService.update(sysCourseUpdateWrapper)){
            state=200;
            log.info("编辑成功！");
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("编辑失败！");

        }
        return new JsonResult<>(state);
    }











    @PostMapping("/admin/getVideoDataTable")
    public String getVideoDataTable(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber ,
                                    @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                    String videoCondition,Model model,HttpSession session){

        QueryWrapper<SysVideo> sysVideoQueryWrapper=new QueryWrapper<>();
        if(videoCondition!=null&& !videoCondition.equals("")){
            sysVideoQueryWrapper.lambda()
                    .and(
                            StringUtils.isNotBlank(videoCondition),   //当courseCondition非空时，进行下面的条件组装
                            q -> q.like(SysVideo::getVideoNum, videoCondition)
                                    .or()
                                    .like(SysVideo::getVideoName, videoCondition)
                    );

        }

        Page<SysVideo> videoPage=new Page<>(pageNumber, pageSize);
        IPage<SysVideo> page=sysVideoService.page(videoPage,sysVideoQueryWrapper);

        //封装工具类参数
        model.addAttribute("countUtil",countUtil);
        model.addAttribute("sysUserService",sysUserService);


        //封装查询参数

        model.addAttribute("videoCondition",videoCondition);

        //封装分页参数
        model.addAttribute("sysVideoList",page.getRecords());
        model.addAttribute("total",page.getTotal());
        model.addAttribute("pages",page.getPages());
        model.addAttribute("pre",page.getCurrent()-1);
        model.addAttribute("next",page.getCurrent()+1);
        model.addAttribute("cur",page.getCurrent());
        model.addAttribute("last",page.getPages());
        model.addAttribute("pageSize",pageSize);
        return "admin/video_data_table";

    }



    @GetMapping("/admin/video/play/{videoId}")
    public String videoPlay(@PathVariable("videoId") Long videoId,
                            Model model) {
        model.addAttribute("videoId", videoId);
        return "/admin/admin_video_play";
    }


    @ResponseBody
    @PostMapping("/admin/deleteVideoDataByVideoId")
    public JsonResult<Void> deleteVideoDataByVideoId(Long videoId){
        if(sysVideoService.removeById(videoId)){
            state=200;
            log.info("删除视频信息成功！");
        }else{
            log.info("删除视频信息失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }



    @ResponseBody
    @PostMapping("/admin/addVideo/{videoNum}/{videoName}")
    public JsonResult<Void> addVideo(@PathVariable("videoNum") String videoNum, @PathVariable("videoName") String videoName, @RequestParam("fileupload") MultipartFile fileupload, HttpSession session) throws IOException {
        SysUser sysUser=(SysUser)session.getAttribute("sysUser");
        String originalFileName = fileupload.getOriginalFilename();
        String seq = UUID.randomUUID().toString();
        assert originalFileName != null;
        String[] items = originalFileName.split("\\.");
        String location = "/onlineCourseVideo/" + seq + "." + items[1];
        SysVideo sysVideo=new SysVideo(idWorker.nextId(),videoNum,videoName,location,null, sysUser.getUserId(),WebUtil.dateToStrong(new Date(),WebUtil.DATETIME));
        hdfsService.fileUpload(fileupload.getInputStream(),location);
        if(sysVideoService.save(sysVideo)){
            state=200;
            log.info("添加成功！");
        }else{
            log.info("添加失败！");
            state=handleException(new InsertException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/admin/editVideo")
    public JsonResult<Void> editVideo(Long videoId,String videoNum,String videoName,String videoDuration){
        UpdateWrapper<SysVideo> sysVideoUpdateWrapper=new UpdateWrapper<>();
        sysVideoUpdateWrapper.eq("video_id",videoId).set("video_num",videoNum)
                .set("video_name",videoName).set("video_duration",videoDuration);
        if( sysVideoService.update(sysVideoUpdateWrapper)){
            state=200;
            log.info("编辑成功！");
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("编辑失败！");

        }
        return new JsonResult<>(state);
    }









    @PostMapping("/admin/getQuestionDataTable")
    public String getQuestionDataTable(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber ,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                     @RequestParam(value = "questionType",defaultValue = "全部") String questionType,@RequestParam(value = "contentType",defaultValue = "全部") String contentType,
                                     Model model, HttpSession session){

        QueryWrapper<SysQuestion> sysQuestionQueryWrapper=new QueryWrapper<>();
        if(!questionType.equals("全部")){
            sysQuestionQueryWrapper.eq("question_type",questionType);
        }
        if(!contentType.equals("全部")){
            sysQuestionQueryWrapper.eq("content_type",contentType);
        }


        Page<SysQuestion> questionPage = new Page<>(pageNumber,pageSize);
        IPage<SysQuestion> page=sysQuestionService.page(questionPage,sysQuestionQueryWrapper);


        //封装工具类参数
        model.addAttribute("countUtil",countUtil);


        //封装查询参数
        model.addAttribute("questionType",questionType);
        model.addAttribute("contentType",contentType);


        //封装分页参数
        model.addAttribute("sysQuestionList",page.getRecords());
        model.addAttribute("total",page.getTotal());
        model.addAttribute("pages",page.getPages());
        model.addAttribute("pre",page.getCurrent()-1);
        model.addAttribute("next",page.getCurrent()+1);
        model.addAttribute("cur",page.getCurrent());
        model.addAttribute("last",page.getPages());
        model.addAttribute("pageSize",pageSize);
        return "admin/question_data_table";

    }



    @ResponseBody
    @PostMapping("/admin/deleteQuestionDataByQuestionId")
    public JsonResult<Void> deleteQuestionDataByQuestionId(Long questionId){
        if(sysQuestionService.removeById(questionId)){
            state=200;
            log.info("删除试题信息成功！");
        }else{
            log.info("删除试题信息失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }



    @ResponseBody
    @PostMapping("/admin/addQuestion")
    public JsonResult<Void> addQuestion(String questionDescription,String A,String B,String C,String D,String E,String F,
                                      String questionType,String contentType,String questionAnswer){

        SysQuestion sysQuestion=new SysQuestion(idWorker.nextId(),questionDescription, A, B, C, D, E, F,questionType,contentType,questionAnswer,10);
        if(sysQuestionService.save(sysQuestion)){
            state=200;
            log.info("添加成功！");
        }else{
            log.info("添加失败！");
            state=handleException(new InsertException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/admin/editQuestion")
    public JsonResult<Void> editQuestion(Long questionId,String A,String B,String C,String D,String E,String F,
                                         String questionType,String contentType,String questionAnswer){
        UpdateWrapper<SysQuestion> sysQuestionUpdateWrapper=new UpdateWrapper<>();
        sysQuestionUpdateWrapper.eq("question_id",questionId).set("A",A).set("B",B).set("C",C).set("D",D).set("E",E)
                        .set("F",F).set("question_type",questionType).set("content_type",contentType).set("question_answer",questionAnswer);
        if( sysQuestionService.update(sysQuestionUpdateWrapper)){
            state=200;
            log.info("编辑成功！");
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("编辑失败！");

        }
        return new JsonResult<>(state);
    }




    /*判断是否为图片类型*/
    public boolean isImageFile(MultipartFile file) {
        List<String> allowedExtensions = Arrays.asList("jpe","jpg","jpeg","gif","png","bmp","ico","svg","svgz",
                "tif","tiff","ai","drw","pct","psp","xcf","psd","raw","webp"); // 支持的文件类型
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(); // 获取文件后缀名并转为小写
        return allowedExtensions.contains(extension);
    }

}