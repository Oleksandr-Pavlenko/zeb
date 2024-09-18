# CO2 Balance API

This project implements a REST API to calculate CO2 emissions based on the consumption of different energy resources.
Customers can send energy consumption data to get CO2 emission calculation.

## Running the Application

- Make sure you have [Docker](https://docs.docker.com/get-docker/) installed.

- Run the following command to build and start the Docker image:

   ```bash
   docker build -f src/main/docker/Dockerfile -t zeb-app .
   docker run -d -p 8080:8080 zeb-app

## Functionality

Submitting energy consumption data.

`POST /api/v1/energy/consumption`

Example Request Body

`[
{
"description": "fake place",
"energySourceId": "2001",
"energyConsumption": 11,
"emissionFactor": 1,1
},
{
"description": "second fake place",
"energySourceId": "2001",
"energyConsumption": 22,
"emissionFactor": 2,2
}
]
`
## Technology stack

- Java 22
- Spring Boot
- Maven
- Docker

