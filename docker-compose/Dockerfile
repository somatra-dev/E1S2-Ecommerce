# Understand about the instruction

# -----------------------
# Stage 1: Download jdk
# -----------------------
FROM ghcr.io/graalvm/graalvm-community:25

WORKDIR /workspace
COPY build/libs/a01-e1-ecommerce-api-1.0.0.jar /workspace/api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/workspace/api.jar"]