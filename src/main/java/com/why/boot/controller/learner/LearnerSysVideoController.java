package com.why.boot.controller.learner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.boot.bean.*;
import com.why.boot.bean.view.CourseVideoView;
import com.why.boot.controller.BaseController;
import com.why.boot.service.*;
import com.why.boot.service.ex.UpdateException;
import com.why.boot.utils.IdWorker;
import com.why.boot.utils.JsonResult;
import com.why.boot.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.eclipse.jetty.server.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysVideoController
 * @CreateTime: 2023/3/18 20:26
 */

@Slf4j
@Controller
public class LearnerSysVideoController extends BaseController {

    @Autowired
    HdfsService hdfsService;

    @Autowired
    SysVideoService sysVideoService;

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    CourseVideoRelationService courseVideoRelationService;

    @Autowired
    CourseVideoViewService courseVideoViewService;

    @Autowired
    CourseLearnerStudyService courseLearnerStudyService;

    @Autowired
    LearnerCourseVideoRelationService learnerCourseVideoRelationService;

    @Autowired
    SysUserHistoryService sysUserHistoryService;

    IdWorker idWorker=new IdWorker(1,1,1);


    @PostMapping("/learner/getCourseVideo")
    public String getCourseVideo(Model model, Integer pageNumber, Long courseId){
        QueryWrapper<CourseVideoView> courseVideoViewQueryWrapper=new QueryWrapper<>();
        courseVideoViewQueryWrapper.eq("course_id",courseId).orderByAsc("video_upload_time");
        IPage<CourseVideoView> iPage=courseVideoViewService.page(new Page<>(pageNumber,4),courseVideoViewQueryWrapper);
        model.addAttribute("pages",iPage.getPages());
        model.addAttribute("courseVideoViewList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent() - 1);
        model.addAttribute("next",iPage.getCurrent() + 1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("courseId",courseId);
        return "/learner/learner_course_video_data";
    }

    @GetMapping("/toVideoPlay")
    public String toVideoPlay(Model model, Long videoId, Long courseId,HttpSession session){

        // 向model中填充页面所需数据
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        model.addAttribute("videoId",videoId);
        LearnerCourseVideoRelation learnerCourseVideoRelationByVideoId = learnerCourseVideoRelationService.getLearnerCourseVideoRelationByVideoId(sysUser.getUserId(), courseId, videoId);
        if(learnerCourseVideoRelationByVideoId!=null){
            model.addAttribute("videoCurrent",learnerCourseVideoRelationByVideoId.getVideoCurrent());
            model.addAttribute("learnerCourseVideoRelationService",learnerCourseVideoRelationService);
        }
        SysCourse sysCourse = sysCourseService.getCourseByCourseId(courseId);
        model.addAttribute("sysCourse",sysCourse);
        CourseLearnerStudy courseLearnerStudyByCourseIdAndUserId = courseLearnerStudyService.getCourseLearnerStudyByCourseIdAndUserId(sysUser.getUserId(), courseId);
        if(courseLearnerStudyByCourseIdAndUserId!=null){
            model.addAttribute("courseProgress",courseLearnerStudyByCourseIdAndUserId.getCourseProgress());
        }
        List<CourseVideoView> courseVideoViewList=courseVideoViewService.getCourseVideoByCourseId(courseId);
        model.addAttribute("courseVideoViewList",courseVideoViewList);


        // 设置用户历史记录，点击课程视频即插入数据库
        String keyWords=sysCourse.getFirstLabel()+","+sysCourse.getSecondLabel()+","+sysCourse.getThirdLabel();
        if(sysUserHistoryService.save(new SysUserHistory(idWorker.nextId(),sysUser.getUserId(),sysCourse.getCourseId(),sysCourse.getCourseName(),
                sysCourse.getCourseDescription(),keyWords, WebUtil.dateToStrong(new Date(),WebUtil.DATETIME)))){
            log.info("用户历史记录更新成功！");
        }


        if(courseLearnerStudyService.updateLastWatchVideoByCourseIdAndUserId(courseId,sysUser.getUserId(),videoId)){
            log.info("最后观看视频更新成功！");
        }
        return "/learner/learner_course_video_play";
    }

    @PostMapping("/learner/getCourseVideoPlay")
    public String getCourseVideoPlay(Model model,Long videoId,Long courseId,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        model.addAttribute("videoId",videoId);


        LearnerCourseVideoRelation learnerCourseVideoRelationByVideoId = learnerCourseVideoRelationService.getLearnerCourseVideoRelationByVideoId(sysUser.getUserId(), courseId, videoId);
        if(learnerCourseVideoRelationByVideoId!=null){
            model.addAttribute("currentTime",learnerCourseVideoRelationByVideoId.getVideoCurrent());

        }
        SysVideo sysVideo = sysVideoService.getById(videoId);
        model.addAttribute("videoName",sysVideo.getVideoName());

        if(courseLearnerStudyService.updateLastWatchVideoByCourseIdAndUserId(courseId,sysUser.getUserId(),videoId)){
            log.info("最后观看视频更新成功！");
        }
        return "/learner/learner_course_video";
    }


    @GetMapping("/video/play/{videoId}")
    public void videoPlay(@PathVariable("videoId") Long videoId,
                          HttpServletResponse response) {

        InputStream is = null;
        // 通过视频ID获取视频信息
        SysVideo sysVideo = sysVideoService.getById(videoId);
        log.info("视频信息：{}",sysVideo);

        try {
            // 通过视频信息中的视频位置获取 HDFS 文件输入流
            is = hdfsService.getFileInputStream(sysVideo.getVideoLocation());

            // 设置响应内容类型为媒体类型
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // 设置响应头 Content-Disposition，指定文件名
            response.setHeader("Content-Disposition", "attachment; filename=" + sysVideo.getVideoName());

            // 利用工具类 IOUtils 将输入流写入响应输出流
            IOUtils.copy(is, response.getOutputStream());

            // 刷新缓冲区，将数据写入响应输出流中
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @ResponseBody
    @PostMapping("/learner/updateVideoCurrentAndIsDone")
    public JsonResult<Void> updateVideoCurrentAndIsDone(Long courseId,Long videoId,String videoDuration,String currentTime,boolean isDone,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        if(learnerCourseVideoRelationService.videoIsDone(sysUser.getUserId(),courseId,videoId)){
            //当该视频已看完，重复播放时，只更新视频播放位置
            if(learnerCourseVideoRelationService.updateVideoCurrent(sysUser.getUserId(),courseId,videoId,videoDuration,currentTime)){
                state=200;
                /*log.info("视频播放位置更新成功！");*/
            }else{
                state=handleException(new UpdateException()).getState();
            }
        }else{
            //若视频未看完，则二者均更新
            if(learnerCourseVideoRelationService.updateVideoCurrentAndIsDone(sysUser.getUserId(),courseId,videoId,currentTime,learnerCourseVideoRelationService.videoIsDone(sysUser.getUserId(),courseId,videoId))){
                state=200;
               /*log.info("视频播放位置与视频是否看完标识更新成功！");*/
            }else{
                state=handleException(new UpdateException()).getState();
            }
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/learner/updateIsDoneVideoCurrentToInitial")
    public JsonResult<Void> updateIsDoneVideoCurrentToInitial(Long courseId,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        if(learnerCourseVideoRelationService.upateIsDoneVideoCurrentToInitial(courseId,sysUser.getUserId())){
            state=200;
            log.info("设置自动连播，已看完视频进度设置为0");
        }else{
            state=handleException(new UpdateException()).getState();
        }
        return new JsonResult<>(state);
    }

}