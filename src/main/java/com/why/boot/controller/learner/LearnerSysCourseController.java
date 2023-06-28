package com.why.boot.controller.learner;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.boot.bean.*;

import com.why.boot.bean.view.UserCourseCommentView;
import com.why.boot.bean.view.UserCourseStudyView;
import com.why.boot.controller.BaseController;
import com.why.boot.service.*;
import com.why.boot.service.ex.*;
import com.why.boot.utils.CountUtil;
import com.why.boot.utils.IdWorker;
import com.why.boot.utils.JsonResult;
import com.why.boot.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.util.concurrent.HadoopThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;


/**
 * @Description: TODO
 * @author: why
 * @ClassName: SysCourseController
 * @CreateTime: 2023/3/13 15:13
 */

@Slf4j
@Controller
public class LearnerSysCourseController extends BaseController {

    @Autowired
    SysUserService sysUsersService;

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    SysVideoService sysVideoService;


    @Autowired
    SysQuestionService sysQuestionService;

    @Autowired
    UserCourseCommentViewService userCourseCommentViewService;

    @Autowired
    UserCourseStudyViewService userCourseStudyViewService;

    @Autowired
    CourseLearnerStudyService courseLearnerStudyService;

    @Autowired
    CourseVideoRelationService courseVideoRelationService;

    @Autowired
    CourseQuestionRelationService courseQuestionRelationService;

    @Autowired
    CourseLearnerCommentService courseLearnerCommentService;

    @Autowired
    LearnerCourseVideoRelationService learnerCourseVideoRelationService;

    @Autowired
    CoursePrerequisiteService coursePrerequisiteService;

    @Autowired
    TopologySortService topologySortService;

    @Autowired
    TFIDFService tfidfService;

    @Autowired
    CountUtil countUtil;

    IdWorker idWorker=new IdWorker(1,1,1);


    @PostMapping("/learner/getCourseData")
    public String getCourseData(Model model,Integer pageNumber){
        List<Object> firstLabelList=sysCourseService.getCourseFirstLabelList();    //获取全部一级标签
        Map<String,Map> sysCourseInfoMap=new HashMap<>();
        for(Object object:firstLabelList){
            String firstLabel=object.toString();
            QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("first_label",firstLabel);
            IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
            Map<String,Object> sysCourseListMap=new HashMap<>();
            sysCourseListMap.put("sysCourseListByFirstLabel",iPage.getRecords());
            sysCourseListMap.put("pre",iPage.getCurrent()-1);
            sysCourseListMap.put("next",iPage.getCurrent()+1);
            sysCourseListMap.put("cur",iPage.getCurrent());
            sysCourseListMap.put("last",iPage.getPages());
            sysCourseListMap.put("firstLabel",firstLabel);
            sysCourseInfoMap.put(firstLabel,sysCourseListMap);
        }
        model.addAttribute("sysCourseInfoMap",sysCourseInfoMap);
        return "/learner/learner_course_data";
    }


    @PostMapping("/learner/getCourseDataByFirstLabel")
    public String getCourseData(Model model,Integer pageNumber,String firstLabel){
        Map<String,Map> sysCourseInfoMap=new HashMap<>();
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("first_label",firstLabel);
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
        Map<String,Object> sysCourseListMap=new HashMap<>();
        sysCourseListMap.put("sysCourseListByFirstLabel",iPage.getRecords());
        sysCourseListMap.put("pre",iPage.getCurrent()-1);
        sysCourseListMap.put("next",iPage.getCurrent()+1);
        sysCourseListMap.put("cur",iPage.getCurrent());
        sysCourseListMap.put("last",iPage.getPages());
        sysCourseListMap.put("firstLabel",firstLabel);
        sysCourseInfoMap.put(firstLabel,sysCourseListMap);
        model.addAttribute("sysCourseInfoMap",sysCourseInfoMap);
        return "/learner/learner_course_data";
    }


    @PostMapping("/learner/getCourseDataByUserHistory")
    public String getCourseDataByUserHistory(Model model,Integer pageNumber,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        List<SysCourse> sysCourseList=tfidfService.TFIDFRecommend(sysUser.getUserId());
        List<Long> sysCourseIdsList=new ArrayList<>();
        if(sysCourseList!=null){
            for(SysCourse sysCourse:sysCourseList){
                sysCourseIdsList.add(sysCourse.getCourseId());
            }
            QueryWrapper<SysCourse> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("course_id",sysCourseIdsList);
            IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
            model.addAttribute("sysCourseListByUserHistory",iPage.getRecords());
            model.addAttribute("pre",iPage.getCurrent()-1);
            model.addAttribute("next",iPage.getCurrent()+1);
            model.addAttribute("cur",iPage.getCurrent());
            model.addAttribute("last",iPage.getPages());
        }
        return "/learner/learner_course_personalization_data";
    }


    @PostMapping("/learner/getCourseDataByStudyPath")
    public String getCourseDataByStudyPath(Model model,Integer pageNumber,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        List<SysCourse> sysCourseList=topologySortService.topologySort(sysUser.getUserMajor());
        List<Long> sysCourseIdsList=new ArrayList<>();
        if(sysCourseList!=null){
            for(SysCourse sysCourse:sysCourseList){
                sysCourseIdsList.add(sysCourse.getCourseId());
            }
        }
        QueryWrapper<SysCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("course_id",sysCourseIdsList);
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
        model.addAttribute("sysCourseListByStudyPath",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        return "/learner/learner_course_studyPath_data";
    }



    @PostMapping("/learner/getCourseDataByUserInterest")
    public String getCourseDataByUserInterest(Model model,Integer pageNumber,String userInterest){
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("second_label",userInterest);
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
        model.addAttribute("sysCourseListByUserInterest",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("second_label",userInterest);
        return "/learner/learner_course_recommend_data";
    }


    @PostMapping("/learner/getCourseDataByViewCount")
    public String getCourseDataByViewCount(Model model,Integer pageNumber){
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("view_count",10).orderByDesc("view_count");
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
        model.addAttribute("sysCourseListByViewCount",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        return "/learner/learner_course_hot_data";
    }



    @PostMapping("/learner/getCourseDataByCourseScore")
    public String getCourseDataByCourseScore(Model model,Integer pageNumber){
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("course_score",4).orderByDesc("course_score");
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,4),queryWrapper);
        model.addAttribute("sysCourseListByCourseScore",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        return "/learner/learner_course_high_data";
    }



    @PostMapping("/learner/getCourseDataByCourseScoreForPersonalCenter")
    public String getCourseDataByCourseScoreForPersonalCenter(Model model,Integer pageNumber){
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("course_score",4).orderByDesc("course_score");
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,2),queryWrapper);
        model.addAttribute("sysCourseListByCourseScoreForPersonalCenter",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        return "/learner/learner_course_highRecommend_data";
    }




    @GetMapping("/learner/learner_course_list")
    public String toLearnerCourseList(String firstLabel,Model model){
        model.addAttribute("firstLabel",firstLabel);
        return "/learner/learner_course_list";
    }



    @PostMapping("/learner/getCourseListDataByFirstLabel")
    public String getCourseListDataByFirstLabel(String firstLabel,Integer pageNumber,Integer pageSize,Model model){
        QueryWrapper<SysCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("first_label",firstLabel);
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber, pageSize),queryWrapper);
        model.addAttribute("sysCourseListByFirstLabel",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("pages",iPage.getPages());
        model.addAttribute("firstLabel",firstLabel);
        model.addAttribute("pageSize",pageSize);
        return "/learner/learner_course_list_data";

    }



    @GetMapping("/learner/learner_course_search")
    public String toLearnerCourseSearch(String search,Model model){
        model.addAttribute("search",search);
        return "/learner/learner_course_search";
    }



    @PostMapping("/learner/getCourseListDataByCondition")
    public String getCourseListDataByCondition(String search,Integer pageNumber,Integer pageSize,Model model){
        QueryWrapper<SysCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .and(
                        StringUtils.isNotBlank(search),   //当search非空时，进行下面的条件组装
                        q -> q.like(SysCourse::getCourseName, search)
                                .or()
                                .like(SysCourse::getCourseLevel, search)
                                .or()
                                .like(SysCourse::getCourseMajor, search)
                                .or()
                                .like(SysCourse::getFirstLabel, search)
                                .or()
                                .like(SysCourse::getSecondLabel, search)
                                .or()
                                .like(SysCourse::getThirdLabel, search)
                );

        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber, pageSize),queryWrapper);
        model.addAttribute("sysCourseListByCondition",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("pages",iPage.getPages());
        model.addAttribute("search",search);
        model.addAttribute("pageSize",pageSize);
        return "/learner/learner_course_search_data";

    }


    @GetMapping("/learner/getCourseDetailData")
    public String getCourseDetailData(HttpSession session,Long courseId, Model model){
        SysUser sysUser= (SysUser) session.getAttribute("sysUser");
        model.addAttribute("courseId",courseId);
        SysCourse courseByCourseId = sysCourseService.getCourseByCourseId(courseId);
        model.addAttribute("sysCourse",courseByCourseId);


        //判断该课程是否收藏
        if(courseLearnerStudyService.isExistedCollectCourse(courseId,sysUser.getUserId())){
            model.addAttribute("msg","已收藏");
        }

        //获取该课程评论数量
        List<CourseLearnerComment> courseLearnerCommentList=courseLearnerCommentService.getCourseLearnerCommentByCourseId(courseId);
        model.addAttribute("courseCommentCount",courseLearnerCommentList.size());
        log.info("课程 {} 共有{}条评论记录",courseByCourseId.getCourseName(),courseLearnerCommentList.size());

        //获取该视频视频数量
        List<CourseVideoRelation> courseVideoRelationList=courseVideoRelationService.getCourseVideoByCourseId(courseId);
        model.addAttribute("courseVideoCount",courseVideoRelationList.size());
        log.info("课程 {} 共有{}条视频",courseByCourseId.getCourseName(),courseVideoRelationList.size());


        //获取该课程试题数量
        List<Long> sysQuestionIdsList=courseQuestionRelationService.getQuestionIdsListByCourseId(courseId);
        model.addAttribute("courseQuestionCount",sysQuestionIdsList.size());
        log.info("课程 {} 共有{}道试题",courseByCourseId.getCourseName(),sysQuestionIdsList.size());

        return "/learner/learner_course_details";
    }



    @PostMapping("/learner/getCourseDataBySecondLabel")
    public String getCourseDataBySecondLabel(Model model,Integer pageNumber,String secondLabel,Long courseId){
        QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("second_label",secondLabel).ne("course_id",courseId);
        IPage<SysCourse> iPage=sysCourseService.page(new Page<>(pageNumber,3),queryWrapper);
        model.addAttribute("sysCourseListBySecondLabel",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent() - 1);
        model.addAttribute("next",iPage.getCurrent() + 1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("secondLabel",secondLabel);
        model.addAttribute("courseId",courseId);
        return "/learner/learner_course_secondLabel_data";
    }


    @PostMapping("/learner/getUserCourseComment")
    public String getUserCourseComment(Model model, Integer pageNumber, Long courseId){
        QueryWrapper<UserCourseCommentView> userCourseCommentViewQueryWrapper=new QueryWrapper<>();
        userCourseCommentViewQueryWrapper.eq("course_id",courseId).orderByDesc("comment_time");
        IPage<UserCourseCommentView> iPage=userCourseCommentViewService.page(new Page<>(pageNumber,4),userCourseCommentViewQueryWrapper);
        model.addAttribute("userCourseCommentViewList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent() - 1);
        model.addAttribute("next",iPage.getCurrent() + 1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("courseId",courseId);
        return "/learner/learner_course_comment_data";
    }


    @PostMapping("/learner/getCourseQuestion")
    public String getCourseQuestion(Model model,Long courseId,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        CourseLearnerStudy courseLearnerStudy=courseLearnerStudyService.getCourseLearnerStudyByCourseIdAndUserId(sysUser.getUserId(), courseId);
        if(courseLearnerStudy!=null){
            String userCourseProgress=courseLearnerStudy.getCourseProgress();
            int userGrade=courseLearnerStudy.getGrade();
            if(userCourseProgress.equals("100%")){
                List<SysQuestion> sysQuestionList=courseQuestionRelationService.selectQuestionsByCourseId(courseId);
                model.addAttribute("sysQuestionListByCourseId",sysQuestionList);
                model.addAttribute("courseId",courseId);
                model.addAttribute("userGrade",userGrade);
                System.out.println("课程id："+courseId);
                log.info("用户成绩：{}",userGrade);
            }
        }

        return "/learner/learner_course_question_data";
    }
    @ResponseBody
    @PostMapping("/learner/getExamResult/{courseId}")
    public JsonResult<Map> getExamResult(@PathVariable("courseId") Long courseId, @RequestBody QuestionAnswer[] questionAnswers,HttpSession session){
        int score=0;
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        Map<String,Integer> scoreMap=new HashMap<>();
        for (QuestionAnswer questionAnswer : questionAnswers) {
            if(questionAnswer!=null){
                SysQuestion sysQuestion=sysQuestionService.getById(questionAnswer.getQuestionId());
                if(sysQuestion.getQuestionType().equals("多选题")){
                    String[] correctAnswer=sysQuestion.getQuestionAnswer().split(",");
                    String[] userAnswer=questionAnswer.getUserAnswer();
                    if(Arrays.equals(correctAnswer,userAnswer)){
                        score+=sysQuestion.getQuestionAward();
                    }
                }else{
                    if(sysQuestion.getQuestionAnswer().equals(questionAnswer.getUserAnswer()[0])){
                        score += sysQuestion.getQuestionAward();
                    }
                }
            }
        }

        if(courseLearnerStudyService.updateUserGradeByCourseIdAndUserId(sysUser.getUserId(),courseId,score)){
            log.info("用户成绩更新成功");
        }
        System.out.println("课程id"+courseId+"   用户id："+sysUser.getUserId());
        scoreMap.put("score",score);

        return new JsonResult<>(state,scoreMap);
    }



    @ResponseBody
    @PostMapping("/updateUserGradeByCourseId")
    public JsonResult<Void> updateUserGradeByCourseId(Long courseId, HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        if(courseLearnerStudyService.updateUserGradeByCourseIdAndUserId(sysUser.getUserId(),courseId,0)){
            log.info("用户成绩置0成功!");
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("用户成绩新失败");
        }
        return new JsonResult<>(state);
    }


    @PostMapping("/learner/getMyCourseComment")
    public String getMyCourseComment(Model model, Integer pageNumber, Long userId){
        QueryWrapper<UserCourseCommentView> userCourseCommentViewQueryWrapper=new QueryWrapper<>();
        userCourseCommentViewQueryWrapper.eq("user_id",userId).orderByDesc("comment_time");
        IPage<UserCourseCommentView> iPage=userCourseCommentViewService.page(new Page<>(pageNumber,4),userCourseCommentViewQueryWrapper);
        model.addAttribute("myCourseCommentViewList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent() - 1);
        model.addAttribute("next",iPage.getCurrent() + 1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("userId",userId);
        return "/learner/learner_course_myComment_data";
    }



    @PostMapping("/learner/getUserCourseStudy")
    public String getUserCourseStudy(Model model,Integer pageNumber,Long userId){
        QueryWrapper<UserCourseStudyView> userCourseStudyViewQueryWrapper=new QueryWrapper<>();
        userCourseStudyViewQueryWrapper.eq("user_id",userId).orderByDesc("last_watch_time").orderByDesc("course_choose_time");
        IPage<UserCourseStudyView> iPage=userCourseStudyViewService.page(new Page<>(pageNumber,3),userCourseStudyViewQueryWrapper);
        model.addAttribute("countUtil",countUtil);
        model.addAttribute("userCourseStudyViewList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent() - 1);
        model.addAttribute("next",iPage.getCurrent() + 1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("userId",userId);
        return "/learner/learner_course_study_data";
    }



    @ResponseBody
    @PostMapping("/collectCourse")
    public JsonResult<Void> collectCourse(Long courseId,Long userId){
       if(courseLearnerStudyService.isExistedCollectCourse(courseId,userId)){
           log.info("该课程已收藏，请勿重复收藏");
           state=handleException(new CollectSysCourseIsExistedException()).getState();
       }else{
           CourseLearnerStudy CourseLearnerStudy=new CourseLearnerStudy(idWorker.nextId(),courseId,userId,WebUtil.dateToStrong(new Date(),WebUtil.DATETIME),null,null,null,null,null);
           if(courseLearnerStudyService.save(CourseLearnerStudy)){
               List<CourseVideoRelation> courseVideoByCourseId = courseVideoRelationService.getCourseVideoByCourseId(courseId);
               if(!courseVideoByCourseId.isEmpty()){
                   //将用户课程视频三者关联
                   for(CourseVideoRelation courseVideoRelation:courseVideoByCourseId){
                       LearnerCourseVideoRelation learnerCourseVideoRelation=new LearnerCourseVideoRelation(idWorker.nextId(),userId,courseId,
                               courseVideoRelation.getVideoId(),sysVideoService.getVideoDurationByVideoId(courseVideoRelation.getVideoId()),null,false );
                       learnerCourseVideoRelationService.save(learnerCourseVideoRelation);
                   }
               }else{
                   log.info("该课程暂无视频");
               }

               state=200;
               log.info("收藏成功！");

           }else{
               log.info("收藏失败！");
               state=handleException(new InsertException()).getState();
           }
       }
       return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/judgeCourse")
    public JsonResult<Void> judgeCourse(Long courseId,Long userId,String judgeText,BigDecimal courseScore){
        CourseLearnerComment courseLearnerComment=new CourseLearnerComment(idWorker.nextId(),courseId,userId,WebUtil.dateToStrong(new Date(),WebUtil.DATETIME),judgeText, courseScore);
        if(courseLearnerCommentService.save(courseLearnerComment)){
            state=200;
            log.info("课程评论成功！");

            //评论成功，更新课程评分
            List<Object> courseScoreListByCourseId = courseLearnerCommentService.getCourseScoreListByCourseId(courseId);
            double totalScore=0.00;
            int count=0;
            for(Object score:courseScoreListByCourseId){
                if(score!=null){
                    totalScore+=Double.parseDouble(score.toString());
                    count++;
                }
            }
            log.info("总评论次数：{}，总评分和：{}",count,totalScore);
            UpdateWrapper<SysCourse> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("course_id",courseId).set("course_score",totalScore/count);
            if(sysCourseService.update(null,updateWrapper)){
                log.info("课程评分更新成功！");
            }

        }else{
            log.info("课程评论失败！");
            state=handleException(new InsertException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/deleteMyComment")
    public JsonResult<Void> deleteMyComment(Long courseId,Long userId,String commentTime){
        if(courseLearnerCommentService.deleteMyComment(courseId,userId,commentTime)){
            state=200;
            log.info("删除评论成功！");
        }else{
            log.info("删除评论失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/deleteMyStudy")
    public JsonResult<Void> deleteMyStudy(Long courseId,Long userId,HttpSession session){
        SysUser sysUser= (SysUser) session.getAttribute("sysUser");
        if(courseLearnerStudyService.deleteMyStudy(courseId,userId)){
            state=200;
            log.info("删除收藏课程成功！");
            if(learnerCourseVideoRelationService.deleteLearnerCourseVideoRelationByUserId(sysUser.getUserId(),courseId)){
                log.info("删除用户、课程与视频关联关系成功！");
            }
        }else{
            log.info("删除收藏课程失败！");
            state=handleException(new DeleteException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/updateViewCountByCourseId")
    public JsonResult<Void> updateViewCountByCourseId(Long courseId){
        //点击课程，更新课程点击量
        SysCourse courseByCourseId = sysCourseService.getCourseByCourseId(courseId);
        int count=courseByCourseId.getViewCount();
        UpdateWrapper<SysCourse> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("course_id",courseId).set("view_count",count+1);
        if(sysCourseService.update(null,updateWrapper)){
            state=200;
            courseByCourseId.setViewCount(count+1);
            log.info("课程点击量更新成功！");
        }else{
            log.info("课程点击量更新失败！");
            state=handleException(new UpdateException()).getState();
        }
        return new JsonResult<>(state);
    }

    @ResponseBody
    @PostMapping("/judgeCourseIsCollected")
    public JsonResult<Void> judgeCourseIsCollected(Long courseId,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        if(courseLearnerStudyService.isExistedCollectCourse(courseId,sysUser.getUserId())){
            state=200;
            /*log.info("该课程已收藏！");*/
        }else{
            state=handleException(new CollectSysCourseIsNotExistedException()).getState();
            /*log.info("该课程未收藏！");*/
        }
        return new JsonResult<>(state);
    }



    @ResponseBody
    @PostMapping("/learner/updateCourseProgress")
    public JsonResult<Void> updateCourseProgress(Long courseId,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        Long userId=sysUser.getUserId();
        List<Object> allVideoDurationByCourseIdAndUserId = learnerCourseVideoRelationService.getAllVideoDurationByCourseIdAndUserId(courseId, userId);
        List<Object> videoCurrentByIsNotDone = learnerCourseVideoRelationService.getVideoCurrentByIsNotDone(courseId, userId);
        List<Object> videoDurationByIsDone = learnerCourseVideoRelationService.getVideoDurationByIsDone(courseId, userId);
        int totalVideoDuration= CountUtil.totalSecond(allVideoDurationByCourseIdAndUserId);
        int isNotDoneVideoCurrent=CountUtil.totalSecond(videoCurrentByIsNotDone);
        int isDoneVideoDuration=CountUtil.totalSecond(videoDurationByIsDone);
        String courseProgress=CountUtil.toPercent(isNotDoneVideoCurrent+isDoneVideoDuration,totalVideoDuration);
        if(courseLearnerStudyService.updateCourseProgressByCourseIdAndUserId(courseId,userId,courseProgress)){
            state=200;
            /*log.info("课程进度更新成功");*/
        }else{
            state=handleException(new UpdateException()).getState();
            log.info("课程进度更新失败");
        }
        return new JsonResult<>(state);
    }



    @ResponseBody
    @PostMapping("/learner/saveNewHeadPath")
    public JsonResult<Void> addUser(String headImgPath,HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        headImgPath=headImgPath.substring(2);
        UpdateWrapper<SysUser> sysUserUpdateWrapper=new UpdateWrapper<>();
        sysUserUpdateWrapper.eq("user_id",sysUser.getUserId()).set("head_img_path",headImgPath);
        if(sysUsersService.update(sysUserUpdateWrapper)){
            state = 200;
            log.info("头像更新成功");
        }else {
            state = handleException(new UpdateException()).getState();
            log.info("头像更新失败");
        }
        return new JsonResult<>(state);
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(CountUtil.percentToNum("0%"));
    }
}