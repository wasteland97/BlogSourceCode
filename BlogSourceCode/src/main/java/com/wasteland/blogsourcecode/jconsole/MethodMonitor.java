package com.wasteland.blogsourcecode.jconsole;

/**
 * @author wfhstart
 * @create 2025-03-15
 */
public class MethodMonitor implements MethodMonitorMBean {
    private int invocationCount = 0;

    @Override
    public int getInvocationCount() {
        return invocationCount;
    }

    @Override
    public void resetInvocationCount() {
        invocationCount = 0;
    }

    public void monitoredMethod() {
        invocationCount++;
        System.out.println("Method invoked. Current count: " + invocationCount);
    }
}
