package io.metersphere.plugin.DebugSampler.utils;

import io.metersphere.plugin.core.MsTestElement;
public class ElementUtil {

    public static String getFullIndexPath(MsTestElement element, String path) {
        if (element == null || element.getParent() == null) {
            return path;
        }
        path = element.getIndex() + "_" + path;
        return getFullIndexPath(element.getParent(), path);
    }
}
