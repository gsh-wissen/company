FROM openjdk:8
ADD target/company-management-system.jar company-management-system.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","company-management-system.jar"]