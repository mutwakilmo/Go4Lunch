# Go4Lunch app
The Go4Lunch will be a collaborative application used by all employees. It will allow you to find restaurants near you and to share your restaurant choice with coworkers. You'll also be able to check out where your coworkers are headed for lunch and decide if you want to go with them. Just before the lunch break, the app will notify different employees and invite them to join their coworkers.

<a href='https://play.google.com/store/apps/dev?id=8103420999836613602&hl=en'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="30%" height="30%"/></a>

<a href='https://trello.com/b/cGDs5LVK/go4lunch-app'><img alt='Get it on Google Play' src='https://www.idalko.com/wp-content/uploads/2018/03/Trello-Guide.png' width="30%" height="30%"/></a>





<img src="./art/01.jpg" width="25%"> &ensp;<img src="./art/02.jpg" width="25%"> &ensp;<img src="./art/03.jpg" width="25%">
&ensp;<img src="./art/04.jpg" width="25%">&ensp;<img src="./art/05.jpg" width="25%">

<img src="./art/06.jpg" width="25%"> &ensp;<img src="./art/07.jpg" width="25%"> &ensp;<img src="./art/08.jpg" width="25%">
&ensp;<img src="./art/09.jpg" width="25%">&ensp;<img src="./art/10.jpg" width="25%">

<img src="./art/11.jpg" width="25%"> &ensp;<img src="./art/12.jpg" width="25%"> &ensp;<img src="./art/13.jpg" width="25%">
&ensp;<img src="./art/14.jpg" width="25%">&ensp;<img src="./art/15.jpg" width="25%">

## Developed By

![enter image description here](https://avatars1.githubusercontent.com/u/41000818?s=460&v=4)

[**Mutwakil Mo**](https://www.linkedin.com/in/mutwakil-mo/)

## License

    Copyright 2020 Mutwakil Mo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


    |### Go4Lunch app|  |
    |----------------|--|
    |                |  |


#### Backend

In order to work correctly, the app needs to communicate with a server (more commonly called a  [backend](https://en.wikipedia.org/wiki/Front_and_back_ends)). In order to simplify the implementation, Go4Lunch is based on a backend called [Firebase](https://firebase.google.com/), offered by Google. This service lets you easily manage:

-   User accounts;
-   User authentification via third-party services (including Facebook and Google, obviously);
-   Saving of data;
-   Sending of push notifications.


#### Login

Access to the application is restricted: it should be necessary to login with a Google or Facebook account.



#### Home screen

The application will be composed of three main views, accessible via three buttons accessible at the bottom of the screen:

-   Restaurant view (as a map);
-   Restaurant view (as a list);
-   Coworker view (to see who's going where).

Once a user is logged in, the application will show the restaurant map view by default.


#### Restaurant map view

Once the user is logged in, they get geolocalized by the application which then displays the area in which they are. All the nearby restaurants are shown on the map using custom pins. If at least one coworker has already been to a certain restaurant, the pin will be a different color (green). The user can click on each restaurant pin to show the restaurant page A geolocalization button should allow automatic recentering of the user's map.


#### Restaurant list view

This view will show details of the restaurants displayed on the map. For each restaurant, the following information should be shown:

-   Name of the restaurant;
-   Distance from the user to the restaurant;
-   Photo of the restaurant (if available);
-   Restaurant type;
-   Restaurant address;
-   Number of colleagues who have indicated interest in going there;
-   Restaurant opening hours;
-   Restaurant reviews (between 0 and 3 stars).


#### Restaurant page

Once the user clicks on a restaurant (whether from the map or list view), a new screen should show the restaurant detail page. This page displays the information from the list view, as well as:

-   A button to indicate you've chosen this restaurant;
-   A button to call the restaurant (assuming there's a phone number available);
-   A button to "like" the restaurant;
-   A button to visit the restaurant's website (assuming there's a website available);
-   The list of coworkers interested in lunching at this restaurant. If no coworkers are interested, no list should be shown.


#### Coworker list

This screen allows you to see all your coworkers and their restaurant choice. If a coworker chooses a restaurant, you can press on their area of the list and show the restaurant detail page.


#### Search

There should be a magnifying class displayed in the top right of each view that, when pressed, pulls up a search area. The search is contextual and should automatically update the data shown in the corresponding view. For example, if you search for "Japanese" while on the map view, only Japanese restaurants should be shown on the map. If no Japanese restaurants are found in the area, try something else!

#### Menu

There should be a menu button in the top left of the screen. No, this isn't a lunch menu! 😂

By clicking on it, a lateral menu should be shown with the following information:

-   Your profile picture;
-   Your first and last name
-   A button to show the restaurant where you've planned to eat;
-   A button to access your settings page (where you'll configure notification settings below, for example);
-   A button allowing you to sign out and return to the sign in page.

#### Notifications

Thanks to Firebase,  [a notification](https://firebase.google.com/docs/cloud-messaging/android/first-message) will be automatically sent to every user who's selected a restaurant in the application. The notification message should be sent at noon. It will remind the user of the name of the restaurant that they've chosen, the address of the restaurant, as well as the list of co-workers coming with them.

#### Translations

Since your colleagues come from many different places, you should offer both English and Spanish as languages in the application (feel free to use Google Translate; the translations don't need to be perfect).

### Elective functionality

You can probably think of some great ways to make this application better, such as:

-   Adding a chat functionality in the app, allowing discussion between coworkers;
-   Other ways to sign in, like via Twitter, GitHub, or username and password;
-   Ability to filter restaurants via different criteria (distance, number of stars, type, etc).

Choose one of these three functionalities and add it to your application. It's up to you!

### Skills

-   Modify an existing Android project
    
-   Create, manage, and show interface fragments
    
-   Define and apply a global theme
    
-   Show a data list with RecyclerView and an Adapter
    
-   Show content in multiple languages
    
-   Configure settings of an Android project
    
-   Create and show a menu



