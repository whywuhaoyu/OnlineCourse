package com.why.boot.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.boot.bean.SysCarousel;
import com.why.boot.bean.SysCourse;
import com.why.boot.bean.SysUser;
import com.why.boot.bean.view.UserCourseCommentView;
import com.why.boot.mapper.SysCarouselMapper;
import com.why.boot.mapper.SysUserMapper;
import com.why.boot.mapper.UserCourseCommentViewtMapper;
import com.why.boot.service.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: OnlineCourseApplicationTest
 * @CreateTime: 2023/3/14 11:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineCourseApplicationTest {

    @Autowired
    SysCourseService sysCourseService;


    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysCarouselService sysCarouselService;


    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysCarouselMapper sysCarouselMapper;

    @Autowired
    UserCourseCommentViewtMapper userCourseCommentViewtMapper;

    @Autowired
    TopologySortService topologySortService;

    @Autowired
    TFIDFService tfidfService;


    @Test
    public void getCourseFirstLabelList(){
        List<Object> courseFirstLabelList = sysCourseService.getCourseFirstLabelList();
        log.info("一级标签：{}",courseFirstLabelList);
    }

    @Test
    public void test1(){
        Model model=new ConcurrentModel();
        List<Object> firstLabelList=sysCourseService.getCourseFirstLabelList();
        Map<String, Map> sysCourseInfoMap=new HashMap<>();
        for(Object object:firstLabelList){
            String firstLabel=object.toString();
            QueryWrapper<SysCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("first_label",firstLabel);
            IPage<SysCourse> iPage=sysCourseService.page(new Page<>(1,3),queryWrapper);
            Map<String,Object> sysCourseListMap=new HashMap<>();
            sysCourseListMap.put("sysCourseListByFirstLabel",iPage.getRecords());
            sysCourseListMap.put("pre",iPage.getCurrent()-1);
            sysCourseListMap.put("next",iPage.getCurrent()+1);
            sysCourseListMap.put("cur",iPage.getCurrent());
            sysCourseListMap.put("last",iPage.getPages());
            sysCourseInfoMap.put(firstLabel,sysCourseListMap);
        }
        model.addAttribute("sysCourseInfoMap",sysCourseInfoMap);
        log.info("{}",model.getAttribute("sysCourseInfoMap"));
    }

    @Test
    public void test2(){

        int count = sysCarouselService.count();
        log.info("轮播图数量为：{}个",count);
        int random=(int)(Math.random()*count);
        System.out.println("随机数"+random);
        if(random>count-3){
            random=count-3;
        }
        QueryWrapper<SysCarousel> sysCarouselQueryWrapper=new QueryWrapper<>();
        sysCarouselQueryWrapper.orderByAsc("id");
        sysCarouselQueryWrapper.last("limit "+ random +",3");
        List<SysCarousel> sysCarousels = sysCarouselMapper.selectList(sysCarouselQueryWrapper);
        log.info("随机结果：：{}",sysCarousels);


    }

    @Test
    public void test3(){
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.eq("user_enabled",1).eq("user_type","learner");
        log.info("{}",sysUserMapper.selectList(sysUserQueryWrapper));
    }


    @Test
    public void test4(){
        List<SysCourse> result=topologySortService.topologySort("平面设计");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.size(); i++) {
            SysCourse course = result.get(i);
            sb.append(course.getCourseName());
            if (i < result.size() - 1) {
                sb.append(" --> ");
            }
        }
        String output = sb.toString();
        System.out.println(output);
    }

    @Test
    public void testList() {
        List<SysCourse> sysCourseList=sysCourseService.list();
        System.out.println(sysCourseList.size());
        System.out.println(sysCourseList);
    }

    @Test
    public void testTIDIF(){
//        System.out.println("用户391094159114637312：");
//        List<SysCourse> sysCourseList=tfidfService.TFIDFRecommend(391094159114637312L);
//        for(SysCourse sysCourse:sysCourseList){
//            System.out.println(sysCourse.getCourseName());
//        }
//
//        System.out.println();

        System.out.println("用户391094159114637312：");
        List<SysCourse> sysCourseList1=tfidfService.TFIDFRecommend(391094159114637312L);
        for(SysCourse sysCourse:sysCourseList1){
            System.out.println(sysCourse.getCourseName());
        }

//        System.out.println();
//        System.out.println();
//        System.out.println("用户402603868531331072：");
//        List<SysCourse> sysCourseList2=tfidfService.TFIDFRecommend(402603868531331072L);
//        System.out.println(sysCourseList2);
    }

}