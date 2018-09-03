# boot-validation-n-security [With Help contribution framework]

### Best Practices Using Spring BOOT ###

Basic member RESTfulCRUD app using spring boot showing the right approach to input validation and OAuth/token based implementaton.

This is a fully RESTful application and there is no any front-end app, web or mobile, hooked to it.

### Thanks To ###

I have either used sample codes, inspirations, how tos, notes and tutorials from the following:


http://intellitech.pro/tutorial-1-spring-security-authentication-using-token/
https://www.boraji.com/spring-boot-using-servlet-filter-and-listener-example-1
https://github.com/nydiarra/springboot-jwt
https://swagger.io/docs/specification/authentication/bearer-authentication/
https://projects.spring.io/spring-security/

### How to run it locally ###

It is a java project, with gradle package manager. The engine is build on top of spring-boot framework. For the data storage, it is using relational postgres database.

* Install gradle if you don't have it
* Install postgres database or use docker for it.
* checkout the project
* Update hte src/main/resource/application.yml with your postgres credentials
* issue gradle buildrun on the command line. The port it is using by default is 8080
* use postman or curl to access the app.
