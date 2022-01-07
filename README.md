<a name="back-to-top"></a>
# *WeatherPal*🌤️
> **UNIVPM** project (Object Oriented Programming)

![Logo WeatherPal](https://github.com/MatDor02/WeatherPal/blob/main/pic/Logo/LogoWeatherPal.png?raw=true)


## Table of Contents 📑
* [Description](#description)
* [How to use](#how-to-use)
* [Route](#route)
* [Examples of routes](#examples-of-routes)
* [How to write params](#how-to-write-params)
* [Notes for developers](#notes-for-developers)
* [UML](#uml)
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
2. Open WeatherPal in your IDE and run the program
3. Open Postman and create a new collection "WeatherPal"
4. Now select the GET request and write the desired route (*you can take the route in the next paragraph*👇🏻)
5. Put the request param in the appropriate fields
6. Now you can send the request and view the weather you have chosen.


<a name="route"></a>
## Route 🔗
> This application use only the `GET` request.

| **Request**| **Route**                         | **Description**                                  | **Parameters**                                          |   
|------------|-----------------------------------|--------------------------------------------------|---------------------------------------------------------|
| `GET`      | [`/stats/period`](#/stats/period) | Returns the available period for the stats.      |                                                         |
| `GET`      | [`/stats/hourly`](#/stats/hourly) | Returns the *hourly stats* of a city.            | `city`, `startDate`, `endDate`, `startTime`, `endTime`. | 
| `GET`      | [`/stats/daily`](#/stats/daily)   | Returns the *daily stats* of a city.             | `city`, `startDate`, `endDate`.                         |
| `GET`      | [`/5dForecast`](#/5dForecast)     | Returns the forecast for the *next 5 days*.      | `city`.                                                 |


<a name="how-to-write-params"></a>
## ⚠️ How to write `params` *correctly*: ⚠️

For the correct use of the program  you must follow these simple rules:
* In `city` you must enter only the city name, without province or postcode.
* For `startDate` and `endDate` you have to write the date using this pattern: `dd-MM-yyyy` (For example: `11-07-2021`).
* For `startTime` and `endTime` you have to write the time using this pattern: `HH:mm`      (For example: `21:00`).


<a name="examples-of-routes"></a>
## Examples of routes 📱
> ![Small Logo](https://github.com/MatDor02/WeatherPal/blob/main/pic/Logo/SmallLogo.png?raw=true)

<a name="/stats/period"></a>
### *localhost:8080/stats/period*
> This is the only route that does not need parameters.

  This route returns the available period for the stats.
  
  ![Route get Period](https://github.com/MatDor02/WeatherPal/blob/main/pic/Request/getPeriod.png?raw=true)
  
  <details><summary>CLICK ME to see the period 🔽</summary>
  <p>
    
  #### The period is:
  ```ruby
    For all the cities, stats are available from 31-12-2021 at 01:00 to 05-01-2022 at 23:36.
    For Ancona they are also available from 04-01-2022 at 12:00 to 04-01-2022 at 22:00
  ```
    
  </p>
  </details>


<a name="/stats/hourly"></a>
### *localhost:8080/stats/hourly*
> The params `city` & `endDate` are optional, if you ignore them they returns: `city = Ancona` and `endDate = startDate` (*the other params are required*)
  
  This route returns the stats for an hourly period.
  
  ![Route Hourly Stats](https://github.com/MatDor02/WeatherPal/blob/main/pic/Request/HourlyStats.png?raw=true)
  
  <details><summary>CLICK ME to see hourly stats 🔽</summary>
  <p>
    
  #### The hourly stats are:
  ```json
  {
    "Temperature": {
        "max": {
            "value": 14.41,
            "unit": "°C",
            "date": "05-01-2022",
            "time": "13:00"
        },
        "min": {
            "value": 10.46,
            "unit": "°C",
            "date": "03-01-2022",
            "time": "02:00"
        },
        "average": {
            "value": 11.8611
        },
        "variance": {
            "value": 1.0106
        },
        "standard deviation": {
            "value": 1.0053
        }
    },
    
    .
    . (Feels like, Humidity, Wind, Pressure & Clouds)
    .
    
    "Air Pollution": {
        "max": {
            "value": {
                "index": 1.0,
                "CO": 250.34,
                "NO": 0.45,
                "NO2": 4.24,
                "O3": 67.23,
                "PM10": 12.05
            },
            "date": "05-01-2022",
            "time": "13:00"
        },
        "min": {
            "value": {
                "index": 2.0,
                "CO": 494.0,
                "NO": 1.01,
                "NO2": 18.34,
                "O3": 8.32,
                "PM10": 14.66
            },
            "date": "04-01-2022",
            "time": "01:00"
        },
        "average": {
            "no2": 11.7727,
            "no": 3.7504,
            "o3": 42.2357,
            "pm10": 10.0964,
            "index": 1.275,
            "co": 411.2251
        },
        "variance": {
            "no2": 51.2337,
            "no": 173.7939,
            "o3": 373.4637,
            "pm10": 67.2729,
            "index": 0.5563,
            "co": 73893.7838
        },
        "standard deviation": {
            "no2": 7.1578,
            "no": 13.1831,
            "o3": 19.3252,
            "pm10": 8.202,
            "index": 0.7459,
            "co": 271.8341
        }
    },
    "Stats period": {
        "from": "02-01-2022 at 12:00",
        "to": "05-01-2022 at 19:00"
    }
 }
```
    
  </p>
  </details>
  
  
  <a name="/stats/daily"></a>
## *localhost:8080/stats/daily*
> The param `city` is optional, if you ignore it the route returns `city = Ancona` (*the other params are required*)
  
  This route returns the stats for a daily period.
  
  ![Route Daily Stats](https://github.com/MatDor02/WeatherPal/blob/main/pic/Request/DailyStats.png?raw=true)
  
  <details><summary>CLICK ME to see daily stats 🔽</summary>
  <p>
    
  #### The daily stats are:
  ```json
  {
    "Temperature": {
        "max": {
            "value": 10.5033,
            "unit": "°C",
            "date": "05-01-2022"
        },
        "min": {
            "value": 4.5442,
            "unit": "°C",
            "date": "03-01-2022"
        },
        "average": {
            "value": 6.5181
        },
        "variance": {
            "value": 7.5507
        },
        "standard deviation": {
            "value": 2.7479
        }
    },
    
    .
    . (Feels like, Humidity, Wind, Pressure & Clouds)
    .
    
    "Air pollution": {
        "max": {
            "value": {
                "index": 1.0,
                "CO": 295.4004,
                "NO": 0.0254,
                "NO2": 8.4171,
                "O3": 55.7167,
                "PM10": 6.7883
            },
            "date": "05-01-2022"
        },
        "min": {
            "value": {
                "index": 3.0,
                "CO": 691.0822,
                "NO": 0.35,
                "NO2": 21.287,
                "O3": 16.8235,
                "PM10": 27.4383
            },
            "date": "02-01-2022"
        },
        "average": {
            "no2": 14.5551,
            "no": 0.2576,
            "o3": 33.1566,
            "pm10": 19.6889,
            "index": 2.5,
            "co": 496.6125
        },
        "variance": {
            "no2": 28.4504,
            "no": 0.0541,
            "o3": 268.813,
            "pm10": 80.8068,
            "index": 1.0,
            "co": 26191.697
        },
        "standard deviation": {
            "no2": 5.3339,
            "no": 0.2326,
            "o3": 16.3955,
            "pm10": 8.9893,
            "index": 1.0,
            "co": 161.8385
        }
    },
    "Stats period": {
        "from": "02-01-2022",
        "to": "05-01-2022"
    }
 }
```
    
  </p>
  </details>
  
  
  <a name="/5dForecast"></a>
## *localhost:8080/5dForecast*
> Here the param `city` is required.
  
  This route returns the forecasts for five days every three hours.
  
  ![Route 5d Forecast](https://github.com/MatDor02/WeatherPal/blob/main/pic/Request/5dForecast.png?raw=true)
  
  <details><summary>CLICK ME to see 5d forecast 🔽</summary>
  <p>
  
  #### The 5d forecast are:
  ```json
  {
    "name": "venezia",
    "lat": 45.4371908,
    "lon": 12.3345898,
    "alt": 6,
    "forecast": [
        {
            "date": "06-01-2022",
            "time": "01:00",
            "weather": {
                "type": "Rain",
                "description": "heavy intensity rain"
            },
            "temperature": {
                "value": 3.38,
                "unit": "°C"
            },
            "feels like": {
                "value": -2.81,
                "unit": "°C"
            },
            "humidity": {
                "value": 94,
                "unit": "%"
            },
            "wind": {
                "value": 10,
                "unit": "m/s"
            },
            "pressure": {
                "value": 1004,
                "unit": "mbar"
            },
            "clouds": {
                "value": 75,
                "unit": "%"
            },
            "precipitation prob.": {
                "value": 100,
                "unit": "%"
            }
        },
        {
            "date": "06-01-2022",
            "time": "04:00",
            "weather": {
                "type": "Rain",
                "description": "light rain"
            },
            "temperature": {
                "value": 3.1,
                "unit": "°C"
            },
            "feels like": {
                "value": -1.43,
                "unit": "°C"
            },
            "humidity": {
                "value": 95,
                "unit": "%"
            },
            "wind": {
                "value": 5,
                "unit": "m/s"
            },
            "pressure": {
                "value": 1006,
                "unit": "mbar"
            },
            "clouds": {
                "value": 83,
                "unit": "%"
            },
            "precipitation prob.": {
                "value": 100,
                "unit": "%"
            }
        },
        .
        .   (continues until 10/01/2022 at 22:00)
        .
    ]
}
```

  </p>
  </details>


<a name="notes-for-developers"></a>
## Notes for developers 🌐

Here are some notes on modeling techniques and dynamics:
1. **GENERICS**: we used generic types in several classes and interfaces.
2. **WILDCARDS**: since we needed the freedom to use any class belonging to a certain 'family' (with a common superclass) as the generic type of a certain attribute, parameter      or entire class definition, we had to introduce also wildcards.
3. **HOW WE CALCULATE AVG, VAR, STD.DEV**: Distribution interface contains the static methods to calculate average, variance and standard deviaton from a vector of any class        that: (1) extends Number or (2) implements Distribution.
   Thus Distribution has a dual role: on one side, it acts like a green light to make average, variance and std. deviation calculable for the classes that implement it; on the      other side, it stores the methods to calculate those stats.
   
   **TYPE OF RETURN**: in case (1), average, variance and std. dev. are double values. In case (2) the idea is to have an object of the class of vector's elements; when this        object represents the average, each field of it will be the average of the corresponding field in the elements of the vector given. The same for variance and std. dev. . 
   BUT: 
   - [A] if the class in question has an int field, the average object cannot store an eventual double average in that field.
   - [B] what is the average of an eventual string attribute? And its variance?
   - [C] (other promblems)

   That's why we choosed an *hashmap* to represent average, var. and std. dev. of a vector with elements of a complex class (which not extends Number but implements                Distribution). This hashmap has the same 'tree node' structure of an object of that complex class: for each pair key-value, the key is the attribute's name, the value is the    attribute's value. Furthermore, it has the advantages of having all double values and not having the class attributes that do not extend Number nor implement Distribution.
   
   ⚠️NOTE:
   - if an attribute's class also extends Distribution , its average (or any other stat) is calculated recursively.
   - to be present in the hashmap, an attribute of the given class not only has to extend Number (this codition includes primitive types too) or implement Distribution, but it      must also have a camel case getter.
4. **REFLECTION**: to make these calculations on all the attiributes of any class (it only has to implement Distribution), we made use of reflection for retrieving a list of all    fields of the given class. To do this, we get the value of each field using a camel case getter: that's why fields that don't have it do not appear in the list of fields and    aren't contained in the hashmap. Methods that use reflection are contained in the ReflectionTools class.
5. **HOW WE CALCULATE MAX/MIN**: maximum and minimum values are maximum and minimum elments of the given vector of measures. Its elements must belong to a class that implements    Comparable interface, because we use Comparable's compareTo method.

   NOTE: air pollution measures have a specific class that stores the presence of multiple chemical elements. AirPollution class implements Comparable and its compareTo method      is based on european [CAQI](https://en.wikipedia.org/wiki/Air_quality_index#CAQI) (Common Air Quality Index), which considers O3 (Ozone), NO2 (Nitrogen dioxide) and PM10        (particulates): the greater the sum of these 3 elements, the greater the AirPollution object is considered.
   
<a name="uml"></a>
There you can find the UML of the project:

![UML](https://github.com/MatDor02/WeatherPal/blob/main/pic/WeatherPal.png?raw=true)


<a name="credits"></a>
## Credits 🧰

For the realization of this application we used [Spring Boot](https://spring.io/) and the [Open Weather API](https://openweathermap.org/api).


<a name="authors"></a>
## Authors 👨‍💻

| Name                | GitHub                                                    | Contacts 📧                  |
|---------------------|-----------------------------------------------------------|-------------------------------| 
| D'Orazio Matteo     | [MatDor02](https://github.com/MatDor02)                   | S1101759@studenti.univpm.it   |
| Fiorucci Lorenzo    | [Lorenzo-Fiorucci](https://github.com/Lorenzo-Fiorucci)   | S1099692@studenti.univpm.it   |

[Back to top](#back-to-top)
