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
