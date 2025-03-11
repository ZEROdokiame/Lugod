## cat Dcokerfile

FROM   docker.io/library/busybox:latest AS base

# 使用 ADD 下载 java-agent,你也可以使用其他下载命令，或者下载到本地，再 使用 COPY或者ADD 指令添加到 镜像里
ADD    https://dlcdn.apache.org/skywalking/java-agent/9.0.0/apache-skywalking-java-agent-9.0.0.tgz /tmp/
RUN    mkdir -p /opt \
    && tar -xzf /tmp/apache-skywalking-java-agent-9.0.0.tgz -C /opt/ \
    && rm -rf /tmp/*
#ADD skywalking-agent.tar.gz /opt/
RUN    mkdir -p /javaagent

## java-agent 使用 方法
## https://skywalking.apache.org/docs/skywalking-java/next/en/setup/service-agent/java-agent/readme/
##  java -javaagent:/opt/skywalking-agent/skywalking-agent.jar -jar yourApp.jar
##  -javaagent:/opt/skywalking-agent/skywalking-agent.jar=agent.service_name=yourAppName,collector.backend_service=127.0.0.1:11800
##  -javaagent:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=yourAppName -Dskywalking.collector.backend_service=127.0.0.1:11800