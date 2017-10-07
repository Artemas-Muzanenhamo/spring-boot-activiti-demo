# spring-boot-activiti-demo

[![CircleCI](https://circleci.com/gh/Artemas-Muzanenhamo/spring-boot-activiti-demo/tree/develop.svg?style=svg)](https://circleci.com/gh/Artemas-Muzanenhamo/spring-boot-activiti-demo/tree/develop)
[![Build Status](https://travis-ci.org/Artemas-Muzanenhamo/spring-boot-activiti-demo.svg?branch=develop)](https://travis-ci.org/Artemas-Muzanenhamo/spring-boot-activiti-demo)

## About

This is a simple project showing examples using Spring-Boot and Activiti BPMN. It will be using Activiti Community not 
enterprise so you will need some form of plugin to be able to play with the Activiti BPMN workflow.

## Pre-requisites

* You will need to have a database (relational or non relational. I used a relational MySQL Database for this example called activiti-demo)
and create a database called `activiti-demo`. Before you start up the application.
* There are two profiles you can run depending on your choice of database. You can run `mvn spring-boot:run -Dspring.profiles.active=development` if you wanted to run on an embedded H2 database. Or you can run `mvn spring-boot:run -Dspring.profiles.active=playground` if you wanted to run the relational MySQL database.
* You are completely not limited to these two databases. If you want to create your profile with your own configuration then go right ahead.
* To start up the application you will just run `mvn spring-boot:run`.

## API Usage

Url | Request | Req. Body | Description
:---: | :---: | :---: | :---:
 `localhost:8080/deploy` | **POST** | Deployment Name : **String** | `Creates a Deployment of the BPMN process.`
 `localhost:8080/start-task` | **POST** | Process Instance Key : **String** | `Starts a Task given a Process Instance Key.`
 `localhost:8080/find-task` | **POST** | Task Assignee **String** | `Returns a List of Tasks given an Assignee.`
 `localhost:8080/complete-task` | **POST** | Task Id **String** | `Completes a task passing a Task Id.`
