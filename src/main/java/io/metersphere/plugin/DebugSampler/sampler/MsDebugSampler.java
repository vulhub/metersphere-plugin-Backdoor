package io.metersphere.plugin.DebugSampler.sampler;

import com.alibaba.fastjson.JSON;
import io.metersphere.plugin.core.MsParameter;
import io.metersphere.plugin.core.MsTestElement;
import io.metersphere.plugin.core.utils.LogUtil;
import io.metersphere.plugin.DebugSampler.utils.ElementUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.sampler.DebugSampler;
import org.apache.jorphan.collections.HashTree;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MsDebugSampler extends MsTestElement {
    public MsDebugSampler() {

    }

    private String clazzName = "io.metersphere.plugin.DebugSampler.sampler.MsDebugSampler";

    @Override
    public void toHashTree(HashTree tree, List<MsTestElement> hashTree, MsParameter config) {
        LogUtil.info("===========开始转换MsDebugSample ==================");
        if (!this.isEnable()) {
            return;
        }

        final HashTree pluginTree = tree.add(initDebugSample());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            for (MsTestElement el : hashTree) {
                el.toHashTree(pluginTree, el.getHashTree(), config);
            }
        }
    }

    public DebugSampler initDebugSample() {
        try {
            DebugSampler debugSampler = new DebugSampler();
            // base 执行时需要的参数
            debugSampler.setProperty("MS-ID", this.getId());
            String indexPath = this.getIndex();
            debugSampler.setProperty("MS-RESOURCE-ID", this.getResourceId() + "_" + ElementUtil.getFullIndexPath(this.getParent(), indexPath));
            List<String> id_names = new LinkedList<>();
            ElementUtil.getScenarioSet(this, id_names);
            debugSampler.setProperty("MS-SCENARIO", JSON.toJSONString(id_names));

            // 自定义插件参数转换
            debugSampler.setEnabled(this.isEnable());
            debugSampler.setName(this.getName());
            debugSampler.setProperty(TestElement.GUI_CLASS, "TestBeanGUI");
            debugSampler.setProperty(TestElement.TEST_CLASS, "DebugSampler");
            debugSampler.setProperty("displayJMeterProperties", false);
            debugSampler.setProperty("displayJMeterVariables", true);
            debugSampler.setProperty("displaySystemProperties", false);

            return debugSampler;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
