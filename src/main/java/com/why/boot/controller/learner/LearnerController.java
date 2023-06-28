package com.why.boot.controller.learner;

import com.why.boot.bean.CourseLearnerComment;
import com.why.boot.bean.CourseLearnerStudy;
import com.why.boot.bean.SysUser;
import com.why.boot.controller.BaseController;
import com.why.boot.service.CourseLearnerCommentService;
import com.why.boot.service.CourseLearnerStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: LearnerController
 * @CreateTime: 2023/3/19 22:36
 */

@Controller
public class LearnerController extends BaseController {

    @Autowired
    CourseLearnerStudyService courseLearnerStudyService;

    @Autowired
    CourseLearnerCommentService courseLearnerCommentService;

    @GetMapping("learner/learner_personal_center")
    public String toPersonalCenter(HttpSession session, Model model){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        List<CourseLearnerStudy> courseLearnerStudyList=courseLearnerStudyService.getCourseLearnerStudyListByUserId(sysUser.getUserId());
        model.addAttribute("myCourseCount",courseLearnerStudyList.size());
        List<CourseLearnerComment> courseLearnerCommentList=courseLearnerCommentService.getCourseLearnerCommentByUserId(sysUser.getUserId());
        model.addAttribute("myCommentCount",courseLearnerCommentList.size());
        return "learner/learner_personal_center";
    }


    @GetMapping("learner/learner_course_study_path")
    public String toStudyPath(HttpSession session, Model model){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        model.addAttribute("learnerMajor",sysUser.getUserMajor());
        return "learner/learner_course_study_path";
    }



}