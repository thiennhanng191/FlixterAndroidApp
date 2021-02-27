# CodePath Android University Assignment 2 - *Flixter*

**Flixter** is an Android application that lets users view a list of movies sourced from The Movie Database API.
Submitted by: **Nhan Nguyen**

The following **required** functionality is completed:

* [x] Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.
* [x] Allow video posts to be played in full-screen using the YouTubePlayerView.

The following **optional** features are implemented:
* [x] Trailers for popular movies are played automatically when the movie is selected.
  * [x] When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
  * [x] Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
* [x] Added ScrollView to the Movie Details Activity. 
* [x] Improved the UI in the Movie Details Activity. 

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='app-walkthrough-assignment2.gif' title='Video Walkthrough Assignment 2' width='' alt='Video Walkthrough Assignment 2' />

GIF created with [GIPHY Capture](https://apps.apple.com/us/app/giphy-capture-the-gif-maker/id668208984?mt=12).

### Notes:
- Since my MovieDetailsActivity has to extend YouTubeBaseActivity instead of AppCompatActivity, I could not call setSupportActionBar() to implement the toolbar. For that same reason, I could not call supportFinishAfterTransition() function to implemenet a shared element transition like the way the instruction did. 

## License

    Copyright [2021] [MIT]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
