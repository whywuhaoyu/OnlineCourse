package com.why.boot.service.impl;

import com.why.boot.bean.SysCourse;
import com.why.boot.service.CoursePrerequisiteService;
import com.why.boot.service.SysCourseService;
import com.why.boot.service.TopologySortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: TODO
 * @author: why
 * @ClassName: TopologySortServiceImpl
 * @CreateTime: 2023/4/4 15:41
 */

@Service
public class TopologySortServiceImpl implements TopologySortService {

    @Autowired
    SysCourseService sysCourseService;

    @Autowired
    CoursePrerequisiteService coursePrerequisiteService;

    @Override
    public List<SysCourse> topologySort(String courseMajor) {
        List<SysCourse> sysCourseList = sysCourseService.getSysCourseListByCourseMajor(courseMajor);
        Map<Long, List<Long>> outDegreeMap = new HashMap<>();
        Map<Long, Integer> inDegreeMap = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();
        List<SysCourse> result = new ArrayList<>();

        // 遍历所有节点，初始化出度和入度信息
        for (SysCourse sysCourse : sysCourseList) {
            Long courseId = sysCourse.getCourseId();
            int inDegree = coursePrerequisiteService.getInDegree(courseId);
           inDegreeMap.put(courseId, inDegree);
            if (inDegree == 0) {
                queue.offer(courseId);
            }


            List<Long> outDegreeList = coursePrerequisiteService.getOutDegreeList(courseId);
            outDegreeMap.put(courseId, outDegreeList);
        }

        // 拓扑排序
        while (!queue.isEmpty()) {
            Long courseId = queue.poll();
            result.add(sysCourseService.getById(courseId));
            List<Long> outDegreeList = outDegreeMap.get(courseId);
            for (Long outDegree : outDegreeList) {
                int inDegree = inDegreeMap.get(outDegree) - 1;
                inDegreeMap.put(outDegree, inDegree);
                if (inDegree == 0) {
                    queue.offer(outDegree);
                }
            }
        }

        return result;
    }


}