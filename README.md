# *WeatherPal*🌤️
> **UNIVPM** project (Object Oriented Programming)

![Logo WeatherPal](https://github.com/MatDor02/WeatherPal/blob/parallel/pic/theme/LogoWeatherPal2.png?raw=true)

## Table of Contents 📑
* [Description](#description)
* [How to use](#how-to-use)
* [Root](#root)
* [Example of root](#example-of-root)
* [Credits](#credits)
* [Authors](#authors)

<a name="description"></a>
## Description 📖
With WeatherPal you can have your own weather with just one click!

This [Spring Boot](https://spring.io/) application allows you to verify the weather of any city that you want. Using Postman you can find the forecasts of 5 days after, the hourly and daily forecast or you can check the stats of the previous days.

<a name="how-to-use"></a>
## How to use 🛠️
To use the application follow the instructions below:
1. Install [Postman](https://www.postman.com/)
2. Open WeatherPal in your IDE and start the debugger
3. Open Postman and create a new collection
4. Now select the GET request and write the desired root (*you can take the root in the next paragraph*👇🏻)
5. Put the request param in the appropriate fields
6. Now you can send the request and view the weather you have chosen.

<a name="root"></a>
## Root 🔗
This application use only the GET request.

| Root                                     | Description                                                              | Parameters                                |
|------------------------------------------|--------------------------------------------------------------------------|-------------------------------------------|
| [`/stats/period`](#/stats/period)        | Return the available period of the *hourly stats* for Ancona             |                                           |
| [`/stats/period`](#/stats/period)        | Return the available period of the *historical stats* for a city         |                                           |
| [`/stats/hourly`](#/stats/hourly)        | Return the *hourly stats* of a city                                      | `?city`, `?startDate`, `?endDate`,        |
|                                          |                                                                          | `?startTime`, `?endTime`.                 |
| [`/stats/daily`](#/stats/daily)          | Return the *daily stats* of a city                                       | `?city`, `?startDate`, `?endDate`.        |
| [`/5dForecast`](#/5dForecast)            | Returns the forecast for the *next 5 days*.                              | `?city`.                                  |

<a name="example-of-root"></a>
## Example of root 📱
> *localhost:8080/stats/period*
  ![Root Period]( )

> *localhost:8080/stats/hourly*
  ![Root Hourly](https://github.com/MatDor02/WeatherPal/blob/parallel/pic/theme/Hourly.png?raw=true)
  
> *localhost:8080/stats/daily*
  ![Root Daily](https://github.com/MatDor02/WeatherPal/blob/parallel/pic/theme/Daily.png?raw=true)
  
> *localhost:8080/5dForecast*
  ![Root 5dForecast]( )

<a name="credits"></a>
## Credits 🧰
For the realization of this application we use [Spring Boot](https://spring.io/) and the [Open Weather API](https://openweathermap.org/api).

<a name="authors"></a>
## Authors 👨‍💻
| Name                   | GitHub                                                    | Contact                          |
|------------------------|-----------------------------------------------------------|----------------------------------| 
| D'Orazio Matteo        | [MatDor02](https://github.com/MatDor02)                   | S1101759@studenti.univpm.it      |
| Fiorucci Lorenzo       | [Lorenzo-Fiorucci](https://github.com/Lorenzo-Fiorucci)   | S1099692@studenti.univpm.it      |
