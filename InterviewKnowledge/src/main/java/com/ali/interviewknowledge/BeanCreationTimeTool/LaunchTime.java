package com.ali.interviewknowledge.BeanCreationTimeTool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfhstart
 * @create 2025-02-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaunchTime {

    private String beanName;

    private long createStart;

    private long createEnd;

    private long cost;

}
