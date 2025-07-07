package com.wasteland.blogsourcecode.jconsole;

/**
 * @author wfhstart
 * @create 2025-03-15
 */
public interface MethodMonitorMBean {
    int getInvocationCount();
    void resetInvocationCount();
}
