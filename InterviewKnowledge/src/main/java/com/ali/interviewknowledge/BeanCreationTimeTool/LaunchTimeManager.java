package com.ali.interviewknowledge.BeanCreationTimeTool;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.util.calendar.CalendarUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wfhstart
 * @create 2025-02-17
 */
@Component
public class LaunchTimeManager implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<String, LaunchTime> LAUNCHTIMEMAP = new ConcurrentHashMap<>();

    public void beanStart(String beanName, long startTime) {
        LaunchTime launchTime = new LaunchTime();
        launchTime.setBeanName(beanName);
        launchTime.setCreateStart(startTime);
        LAUNCHTIMEMAP.put(beanName, launchTime);
    }

    @Transactional
    public void beanEnd(String beanName, long endTime) {
        if (LAUNCHTIMEMAP.containsKey(beanName)) {
            LaunchTime launchTime = LAUNCHTIMEMAP.get(beanName);
            launchTime.setCreateEnd(endTime);
            launchTime.setCost(endTime - launchTime.getCreateStart());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("spring ioc started");

        // top20
        LAUNCHTIMEMAP.values()
                .stream()
                .sorted((o1, o2) -> Long.valueOf(o2.getCost() - o1.getCost()).intValue())
                .limit(20)
                .forEach(e -> {
                    System.out.println(e.getBeanName() + ", cost: " + e.getCost() + "ms.");
                });

        System.out.println();
        System.out.println();

        LAUNCHTIMEMAP.values()
                .stream()
                .sorted((e1, e2) -> Long.valueOf(e1.getCreateStart() - e2.getCreateStart()).intValue())
                .forEach(e -> {
                    System.out.println(
                            "create: [ " + dateFormat(e.getCreateStart()) + " -> " + dateFormat(e.getCreateEnd()) + " ]" +
                            ", cost: " + e.getCost() + "ms \t\t" + e.getBeanName()
                    );
                });
    }

    private String dateFormat(long time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return localDateTime.format(formatter);
    }

}
