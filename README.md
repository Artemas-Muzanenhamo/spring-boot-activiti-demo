# spring-boot-activiti-demo

[![CircleCI](https://circleci.com/gh/Artemas-Muzanenhamo/spring-boot-activiti-demo/tree/develop.svg?style=svg)](https://circleci.com/gh/Artemas-Muzanenhamo/spring-boot-activiti-demo/tree/develop)
[![Build Status](https://travis-ci.org/Artemas-Muzanenhamo/spring-boot-activiti-demo.svg?branch=develop)](https://travis-ci.org/Artemas-Muzanenhamo/spring-boot-activiti-demo)
![Docker Pulls](https://img.shields.io/docker/pulls/artemas/activiti-demo?style=for-the-badge)

## About

This is a simple project showing examples using Spring-Boot and Activiti BPMN. It will be using Activiti Community not 
enterprise so you will need some form of plugin to be able to play with the Activiti BPMN workflow.

## Pre-requisites

* Java 11+
* You will need to have a database (relational or non relational. I used a relational MySQL Database for this example called activiti-demo)
and create a database called `activiti-demo`. Before you start up the application.
* There are two profiles you can run depending on your choice of database. You can run `mvn spring-boot:run -Dspring.profiles.active=development` if you wanted to run on an embedded H2 database. Or you can run `mvn spring-boot:run -Dspring.profiles.active=playground` if you wanted to run the relational MySQL database.
* You are completely not limited to these two databases. If you want to create your profile with your own configuration then go right ahead.
* To start up the application you will just run `mvn spring-boot:run`.

## Understanding the API

This application has [swagger](https://swagger.io/tools/swagger-ui/) installed so you can view the API at the following URL:

- http://localhost:8080/swagger-ui.html#/workflow-controller (UI)
- http://localhost:8080/v2/api-docs (JSON)
