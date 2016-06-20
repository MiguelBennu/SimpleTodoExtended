# SimpleTodoExtended
# Pre-work - Simple Todo Extended  (Gotta Do)

Gotta Do SimpleTodo is an android app that allows building an ephimeral todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Luis Miguel Arango

Time spent: 16 hours spent in total

User Stories:

-Paul has issues remembering his daily responsabilities. With "Gotta Do" Paul logs a task and sets its priority when he knows he has to remember a task for later. Paul also assigns a date to the task to make sure task is completely timely. When Paul completes the task he erases the listing and gets a feeling of success. 
-Antony always forgets what to buy when he goes grocery shopping. With Gotta do Antony can log his shopping task and comment to it the items he has to remember when he is at the store. Once Antony has done the shopping, he deletes the task goes home with a succesful shopping

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Splah Screen
  
  Give I can see this being very useful with the following added features
  - Update entry can be refined to allow duplicate entries
  - Picture functionality - Tak a picture and attach to the task
  - Alarm/notification if completion date is past due
  

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/YvCxth4.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

- The custom "Fragment" implementation is vague with respect to a fragment calling a fragment 

- When returning date from the date fragment to the edit fragment sometime the DUT crashes
- When DUT is flipped horizontally the app crashes.

## License

    Copyright 2016

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
