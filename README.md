# 介绍

这是一个生成gitlab checkout脚本的工具。



# 背景

公司项目工程数有点多，一个一个的更新非常要命，另外还使用了flatten-maven-plugin插件，工程必须按指定目录放才行。

刚开始有人手写了一个CheckOut.bat，慢慢工程越来越多，手动维护已经非常不方便了，所以我写了个生成工具，一次把所有有权限的工程全部写入到脚本里去。



CheckOut.bat长这样

<code>

@echo off

if not exist java-components mkdir java-components
if not exist ip-java-components mkdir ip-java-components
if not exist ip-java-commons mkdir ip-java-commons
if not exist ip-java-ice-slice mkdir ip-java-ice-slice
if not exist ip-java-services mkdir ip-java-services
if not exist ip-java-web-servers mkdir ip-java-web-servers

SETLOCAL ENABLEDELAYEDEXPANSION

SET var=redis-cache ice-all dt-finally

cd java-components
FOR %%a IN (%var%) DO (
    SET dir=%%a
    SET url=http://192.168.10.44:800/java-components/%%a.git
    
    if exist !dir! (
      cd !dir! & git pull
      echo !cd! finished & cd ..
    ) else (
      git clone !url!
    )
)
cd ..

</code>



# 实现方法

就是用jsoup去爬project列表，然后根据这些信息写bat脚本。