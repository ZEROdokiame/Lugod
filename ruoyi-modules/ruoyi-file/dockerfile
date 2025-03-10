From adoptopenjdk/openjdk8:x86_64-ubuntu-jdk8u322-b06-slim
MAINTAINER 254837522@qq.com

ARG projectName
ARG port1

ENV projectName=${projectName}
EXPOSE ${port1}
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Dserver.port=${port1} -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -XX:+HeapDumpOnOutOfMemoryError -Duser.timezone=Asia/Shanghai"
RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /${projectName}
WORKDIR /${projectName}


ADD ./${projectName}.jar ./
CMD java -jar $JAVA_OPTS ${projectName}.jar