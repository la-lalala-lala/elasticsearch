## Spring boot集成ElasticSearch项目
* JDK:1.8
* MySQL:8.0
* ElasticSearch:5.2.2
* Spring boot:2.0.1
## 注意说明：
 * 务必注意spring boot和elasticsearch之间版本的关系，否则项目无法启动
## 开发中遇到的问题
 * 项目集成了Jpa，由于在开发的过程中，没有注意到依赖，在未添加javassist依赖时，无法启动项目
 * 页面无法返回json给前端页面，原因：项目打包后的class没有实体类的get和set方法
 * 项目在运行时无法获取实体类的get&set方法，但项目编译后有对应的get&set方法。解决办法：升级lombok的版本，并修改scope作用域：provided，当前的lombok版本：1.18.10