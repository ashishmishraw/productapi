# MyRetail product Api 
Contributors: [mishra.ashish@icloud.com](https://github.com/ashishmishraw)

### Description
RESTful service that can retrieve product and price details by ID.
* Performs retrieval of the given product name from redsky.target.com PDP service
* Performs retrieval of given product price from hosted Pricing service
* Returns the product details

* For API details, check swagger documentation as mentioned below: 
http://{host}:3000/swagger-ui.html
http://{host}:3000/api-docs


### Deployment and Running the service
Docker image can be built using maven and run as a containerized service on any host

* For docker details see [Dockerfile](Dockerfile)
* For hosting on AWS Elastic Beanstalk as a docker environment, see [Dockerrun.aws.json](Dockerrun.aws.json)
* Docker image could be found on Dockerhub at [ashishmishraw](https://hub.docker.com/r/ashishmishraw/productapi/tags)

###Tech stack details
* The service is developed using [spring-boot](https://start.spring.io/) framework
* Underlying JVM is java 8. Docker container runs alpine JDK 8 image 
