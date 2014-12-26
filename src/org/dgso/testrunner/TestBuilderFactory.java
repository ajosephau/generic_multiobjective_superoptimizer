package org.dgso.testrunner;

import java.util.ArrayList;

public class TestBuilderFactory {
    public static ArrayList<TestBuilder> testBuilders;

    public static void createTestBuilders(String templateFolder, String templateFile, int instanceCount) {
        testBuilders = new ArrayList<TestBuilder>(instanceCount);

        for (int i = 1; i <= instanceCount; i++) {
            TestBuilder tb = new TestBuilder(templateFolder, templateFile, i);
            testBuilders.add(tb);
        }
    }

    public static TestBuilder getTestBuilder(int instanceID) {
        if (instanceID < testBuilders.size() && instanceID > 0) {
            return testBuilders.get(instanceID);
        } else {
            return null;
        }
    }
}
