FROM khipu/openjdk17-alpine

# work directory 설정
WORKDIR /app

# timezone
ENV TZ="Asia/Seoul"

# spring profile 설정
ARG SPRING_PROFILE
ENV ENV_SPRING_PROFILE=$SPRING_PROFILE

# local -> docker
ARG BUILD_JAR=build/libs/challang-0.0.1-SNAPSHOT.jar
COPY ${BUILD_JAR} ./challang.jar

# docker run
ENTRYPOINT ["java", "-Dspring.profiles.active=${ENV_SPRING_PROFILE}", "-Duser.timezone=Asia/Seoul", "-jar", "./challang.jar"]
