# Weather Forecast

**Introduction

This application is developed for weather forecast of current location. To forecast weather information the project is using “api” from http://openweathermap.org/  website.
 So, the main scope of the application is to fetch data from weather api and then display in the application. Also data need to be stored locally. And api will be called in the background after every 2 hours. Further when app will be open this local stored data will be shown on the main screen.

 To achieve above task, the application is using WorkManager to perform the task after periodic time.

**App Architecture
This project is following MVVM architecture pattern. Here I have separated all classes in separate packages. The main packages of this application are as follows:
Model: It contains all objects consist of data that is used to interacts with ViewModels which further interact with Views with the help of observables. In this model data is created for Weather Forecast response and for location coordinates.

View: There is only single activity in this application ie MainActivity which is the main UI of weather forecast response.

ViewModel: As this application firstly fetch current location coordinates. So, “LocationCoordinateViewModel” is created in this package. It interacts with the “LocationResponseModel” and exposes the observable that can be observed by the “MainActivity” class.

Util: In this all common classes that is needed in whole project is created under this package.

Note: Right now application is not using “OpenWeatherViewModel” as application is calling api in “MyWorker” class. All response is managed in this class. In future I will use this unused class to manage the call.

The above images Cleary shows how app architecture is developed through MVVM.


**Implementation

Step1: Add dependencies in the project. Refer build.gradle under app folder.

Step2: Create different folders that relate to MVVM.

Step3: Design MainActivity view in main_activity.xml.

Step4: Hit api in web and fetch response. Further create model based on response data.

Step 5: Implement location permission method at runtime. And then get location coordinates by FusedLocation client and then save the location coordinates.

Step 6: Declare internet, location and other permissions in manifest file.

Step 7: For periodic request of api after every 2 hours create WorkManager class and then schedule request.

Step 8: Check internet connection. Here application is validating all active network.Not only the wifi. Here I have created separate method is CommonMethods.class under util package.

Step 9: Call weather forecast api to get response. This project is using Retrofit to make a call.

Step 10: Create SharedPrefrence class to store response locally. Here I am not using Room or SQl db as we need last response which we receive from api call.

Step 11: Create broadcast receiver in MainActivity and sendBroacast data from WorkManger ie.  class (MyWork.class).

Step 12: Display data in MainActivity.class that is received from Broadcast receiver.



**Future Release Thoughts

I would like to use material design in this application instead of normal relative layout to make view look better.

Apart from that I want to run few unit test cases to check the code logics.

**Github Repository

https://github.com/aakriti1/Weather-Forecast.git

