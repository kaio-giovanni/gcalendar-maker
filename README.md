# GCalendar Maker

### This project makes user calendars based on Google calendar

#### Project Status

🟡 In progress ️🛠️

#### Project setup

1. Execute the commands bellow:

```bash
git clone https://github.com/kaio-giovanni/gcalendar-maker 
cd gcalendar-maker
gradle clean build
```

2. Create or configure an google cloud project

3. Active the Google Calendar API and get access credentials

4. Create an `.env` file in the project root and enter your credentials based on the `.env.example` file.

#### Run project

1. Execute the command bellow:

```bash
gradle bootRun
```

2. Access the endpoint `/google-register` and allow the application to access your google calendar.

3. Access the endpoint `/user/calendar` passing your userId, startDate and endDate (format: yyyy-mm-ddThh:mm:ss).

Ex:
<div align="center">
    <p>
        <i>Monthly calendar</i>
    </p>
    <p>
        <img width="500" height="500" alt="Monthly calendar" src="./screenshots/MonthlyCalendar.png">
    </p>
</div>

#### Run tests

- To run all tests, execute the command bellow:

```bash
gradle test
```

- To run a specific test, execute the command bellow:

```bash
gradle test --tests SomeSpecificTest
```

- To run tests with debug mode, execute the command bellow:

```bash
DEBUG=true gradle test
```

#### Author

| ![user](https://avatars1.githubusercontent.com/u/64810260?v=4&s=150) |
| ----------------------------- |
| <p align="center"> <a href="https://github.com/kaio-giovanni"> @kaio-giovanni </a> </p>|


