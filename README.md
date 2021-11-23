# MeterSphere 接口测试调试请求插件

[MeterSphere](https://github.com/metersphere/metersphere) 是一站式开源持续测试平台，涵盖测试跟踪、接口测试、性能测试、团队协作等功能，兼容JMeter 等开源标准，有效助力开发和测试团队充分利用云弹性进行高度可扩展的自动化测试，加速高质量软件的交付。

该项目为 MeterSphere 配套的调试请求插件，在 MeterSphere 中安装该插件后可在接口自动化场景中添加调试请求步骤，通过该步骤可以打印出执行过程中的变量及变量值。

## 安装使用

1. 在仓库的 Releases 页面下载最新的插件 Jar 包
2. 在 MeterSphere `系统设置`-`插件管理` 页面上传下载的插件
3. 在接口自动化场景中添加 `调试请求` 步骤

## 问题反馈

如果您在使用过程中遇到什么问题，或有进一步的需求需要反馈，请提交 GitHub Issue 到 [MeterSphere 项目的主仓库](https://github.com/metersphere/metersphere/issues)

## 开发说明

Ui生成器：
    http://www.form-create.com/designer/?fr=home

UI组件说明：
    https://form-create.com/v2/element-ui/global.html

插件入口：
    和jar包同名配置，内容格式：如果多个入口，按照每行一个入口

Upload组件特殊用法：用UI生成器生成后 修改 type=msUpload 即可

动态数据初始化：
  组件增加事件 change，click 等事件 
  emit参数：name = 事件
      inject为传递参数：paramsName 为当前组件的field 名称 ，第二个参数是自定义的JSON串，可任意格式 可在 UiScriptApi.customMethod 的
      入参接收；如需获取表单中其他属性值当参数传递 可用 ${field 名称} 这种方式提取
  emitPrefix参数： prefix 为固定前缀，平台会监听 prefixVisibleChange ,prefixClick 等事件
  
  完整示例：
  "emit": [{
         "name": "visible-change",
         "inject": [
           "paramsName",
           "{\"method\":\"initInterface\",\"表单参数\":\"${filed}\"}"
         ]
       }],
      "emitPrefix": "prefix",
