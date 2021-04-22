FROM r.cn/pub/jdk:1.8
ARG  env
ARG  idc
ENV  LANG en_US.UTF-8
ENV  env=${env} idc=${idc}
RUN  mkdir -p /opt/settings/ && echo -e "env=${env}\nidc=${idc}" > /opt/settings/server.properties
ADD  build/libs/choice-credit-0.0.1-SNAPSHOT.jar /opt/choice-credit.jar
ENTRYPOINT /usr/java/jdk1.8.0_151/bin/java ${JAVA_OPTS} -jar /opt/choice-credit.jar
EXPOSE 8080