package org.keyboardplaying.demo.runtime;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Cyrille Chopelet
 */
@Component
public class OsInformation {

    private static final String OS_NAME = "os.name";

    private String os;

    @PostConstruct
    public void init() {
        this.os = System.getProperty(OS_NAME).toLowerCase();
    }

    public boolean isWindows() {
        return (os.contains("win"));
    }

    public boolean isMac() {
        return (os.contains("mac"));
    }

    public boolean isUnix() {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }

    public boolean isSolaris() {
        return (os.contains("sunos"));
    }
}
