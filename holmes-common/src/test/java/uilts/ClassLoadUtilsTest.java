package uilts;

import com.shuzheng.holmes.common.utils.ClassloadUtils;

public class ClassLoadUtilsTest {

    public static void main(String[] args) {
        ClassloadUtils classloadUtils = ClassloadUtils.getClassloadUtils();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Class<?> aClass = classloadUtils.classLoadFromClassFile("com.beyond.run.Application", "C:\\Users\\WIN10\\Desktop\\Application.class");
            }).start();
        }
        classloadUtils.tryGetClass("com.beyond.run.Application");
    }

}
