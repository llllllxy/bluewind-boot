#!/bin/sh
#
# 启动 jar 运行

# 项目部署目录
projectDir=/opt/springboot/
# 项目运行 jar 名称
jarName="bluewind-boot-0.0.1-SNAPSHOT.jar"

#JVM参数
jvmOpts="-Xms512M -Xmx512M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=256M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"

# 项目部署环境
profileActive="dev"
jasypt_encryptor_password="ymp8R3Vg7Kv5$y5fM3*xl&ins7SZcTEY"

# 判断项目SpringBoot程序是否运行
count=$(ps -ef | grep ${jarName} | grep -v "grep" | wc -l)
if [ ${count} -lt 1 ]; then
  cd ${projectDir}
  nohup java -jar ${jvmOpts} ${jarName} --spring.profiles.active=${profileActive} --jasypt.encryptor.password=${jasypt_encryptor_password} >/dev/null 2>&1 &
  echo "$(date '+%Y-%m-%d %H:%M:%S') Start ${jarName} success..."
else
  echo "$(date '+%Y-%m-%d %H:%M:%S') ${jarName} is running..."
fi
