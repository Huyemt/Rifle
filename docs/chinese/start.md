# 前言
<kbd>Rifle</kbd>十分欢迎各位开发者参与主项目和模块的开发。
<br>
本项目属于免费开源项目，将由<kbd>[Huyemt](http://github.com/Huyemt)</kbd>提供长期维护的技术。
<br>
您对本项目的支持就是对<kbd>Rifle</kbd>开发者们的最大资助。
<br><br>
<strong style="color:red">请勿将此用于违法犯罪的行为，否则后果自负。</strong>
# 须知
1. 在使用之前，请确保您JDK环境在`JDK 11`或以上。推荐`adopt-openj9-11.0.11`
2. 本项目采用 <kbd>Java</kbd> 的项目管理平台 <kbd>Maven</kbd> 进行开发。若您想在原有<kbd>Rifle</kbd>之上再次开发属于自己的<kbd>Rifle</kbd>，请先[安装Maven的运行环境](https://www.runoob.com/maven/maven-setup.html)。
3. 在您编辑模块时，请先了解<kbd>Rifle</kbd>的[内部概念](rifle_flow.md)。
***
# 模块开发
* [模块基础](develop/Module.md)
* [自定义命令](develop/Command.md)
* [自定义任务](develop/Task.md)
* [Bullet语言](bullet)
# 内置支持库
* [调用JavaScript](lib/JavaScript4J.md)
* [网络请求](https://github.com/Huyemt/Http4J/blob/main/docs/chinese/develop.md)
* [密码加密](https://github.com/Huyemt/Crypto4J)
* [Json解析](../../src/main/java/org/huyemt/json4j/Json4J.java)