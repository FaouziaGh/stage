Feature: Erudaxis Login Page
@login
Scenario Outline: Login with valid credentials

Given admin is on login page

When admin enter correct username "faouzia.gharbi111@gmail.com" and correct password "Faouzia@2026!"

Then admin click on role "<role>"

Then admin is directed to home page that containes MSG "<verificationMsg>"

Examples:
|role   |verificationMsg|
|college|college|
|lycee  |lycee  |
