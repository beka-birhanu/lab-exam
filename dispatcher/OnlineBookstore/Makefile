TARGET_DIR := target
WAR_NAME := OnlineBookstore-1.0-SNAPSHOT.war

.PHONY: build run test clean deploy

build:
	@mvn clean package

run:
	@mvn tomcat7:run

test:
	@mvn test

clean:
	@mvn clean
