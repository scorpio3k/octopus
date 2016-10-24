package org.scorpio.octopus.debug;


public class DefaultDebugLogger implements DebugLogger {
    @Override
    public void log(String info) {
        System.out.println(info);
    }
}
