# group-project-team-hypervisor
group-project-team-hypervisor created by GitHub Classroom

# MealPickr

Authors:
1. Farnam Keshavarzian (farnam@csu.fullerton.edu)
2. Ramon Amini (raaamonnn@csu.fullerton.edu)
3. Vincent Lee (lee.v3798@csu.fullerton.edu)

## Overview
### Summary
MealPickr is an mobile android application that helps those who cannot find a place to eat. 
It will take the user’s current location and pick a random restaurant within a range (mi) the user chooses. 
Ex. If the user lives 1 mile away from a McDonalds and the user chooses 2 miles as the range, 
McDonalds will have a possible chance of being selected. There will be different filters you can 
choose whether you feel it’d be a meal, dessert, what restaurant is open, or even a type of meal.

### Platforms
For now this application is meant to be supported on Android

## Background and motivation
It's always hard choosing a place to eat around the area. 
There are times where it takes over 30 minutes just thinking of what to eat. 
For those indecisive folks, it would be nice to have a generator to select random restaurants to eat. 
It can also help give ideas by having the application choose random restaurants for you. 
I feel like it is universal because everyone has to eat. Time is important so don’t waste time 
thinking when you should be eating.


## Features
Pick a random restaurant location based on a given range and current location. 
- Functions 
- Random generator
- Current Location
- Map Display/Navigation
- Filter

## Progress
### Check-in 1
#### Developer 1 Farnam:
Simply added a MenuItem class, created a branch and pushed my code. 

#### Todo list:
1. Get started on searching different API's to see the different types of data that we need to deal with
to then hard code the data. 

#### Developer 2 Vincent:
Added information to the design document and pushed a RandomGenerator branch.

#### Todo list:
1. Figure out how to capture data from the API and think of any more classes we need.

#### Developer 3 Ramon:
Created the userObject class

#### Todo list:
1. Reimplement the project to use the map template
2. Let the map start at the current user location


## Progress
### Check-in 2:
#### Developer 1 Farnam:
Added FloatingActionButton for the settings menu and started
working on the settings page under the Settings branch.
#### Todo list:
1. Start working on the backend for handling "API" data. 

#### Developer 2 Vincent:
Remote push the map template to integrate the map activity.
Authorize Google Maps API key so team can use it.
Added navigation menu

#### Todo list:
1. Figure out how to integrate API

#### Developer 3 Ramon:
1. Reimplement the project to use the map template
2. Let the map start at the current user location
3. Looked into the yelp api
4. Added a location fragment to fulfill the check in 2 requirement

#### Todo list:
1. Figure out how to do an api request in kotlin
2. Try getting the yelp api integrated

*Design document template is based on [Chromium’s Design document template.](https://docs.google.com/document/d/14YBYKgk-uSfjfwpKFlp_omgUq5hwMVazy_M965s_1KA/edit)*
