package org.keyboardplaying.demo.runtime;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author Cyrille Chopelet
 */
@Component
public class RuntimeInformation {

    private String jvmName;
    private int pid;

    @PostConstruct
    public void init() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        this.jvmName = runtimeBean.getName();
        this.pid = Integer.valueOf(jvmName.split("@")[0]);
    }

    public String getJvmName() {
        return jvmName;
    }

    public int getPid() {
        return pid;
    }
}
