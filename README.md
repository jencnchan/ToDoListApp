# Pre-work - Todo List App

This app is the prework submission for the Girls in Tech and CodePath Android bootcamp. 

**Todo List App** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting existing items.

Submitted by: **Jennifer Chan**

Time spent: 15 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persisted the todo items into SQLite instead of a text file
* [X] Improved style of the todo items in the list using a custom adapter
* [X] Added support for completion due dates for the todo items and displayed within the listview item
* [X] Added support for selecting the priority (importance) of each todo item and displayed in the listview item
* [X] Tweaked the style improving the UI / UX, played with colors, images and backgrounds

The following **additional** features are implemented:

* [X] Used DatePickerDialog for the user to select the due dates
* [X] Displayed importance of the items in the listview by color
* [X] Implemented "empty entry check" to ensure the entered fields are not empty when adding or editing items to prevent the todo items in the list from being blank
* [X] Implemented "existing item check" to ensure the item to be added or edited has not already existed in the current list

## How To Use This App
1. Enter todo items into the space at the bottom and hit "Add". The entered text will be shown in the list above and stored in the database.
2. Click on the items in the list to bring you to the edit page. In this page, you can edit the item, hit "Select" to select a due date for the item, and choose an importance level for the item. Hit "Save" to save the edited data. The todo item, it's due date, and it's level of importance will be stored and shown in the main page. The levels of importance are expressed by color. (Red = High importance, Orange = Medium importance, Green = Low importance)
3. In the main page, long click on the items in the listview to delete the items.
4. Previously saved data will be shown when restarting the app.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

![todo app gif walkthrough](https://cloud.githubusercontent.com/assets/24812963/22621752/d7bed06e-eb65-11e6-8bc2-ee02ba885501.gif)

## License

    Copyright 2017 Jennifer Chan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
