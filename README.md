# memory-game
Memory Matching Game created on Android (Kotlin + Java)

## Screenshots
<table>
  <tr>
    <td>
      <img width="200" alt="portfolio_view" src="https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/screenshot_start_screen.jpeg">
    </td>
    <td>
      <img width="200" alt="portfolio_view" src="https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/screenshot_settings.jpeg">
    </td>
  </tr>
  <tr>
    <td>
      <img width="200" alt="portfolio_view" src="https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/screenshot_start.jpeg">
    </td>
    <td>
      <img width="200" alt="portfolio_view" src="https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/screenshot_game.jpeg">
    </td>
    <td>
      <img width="200" alt="portfolio_view" src="https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/screenshot_game_finished.jpeg">
    </td>
  </tr>
</table>


## Project architecture
### Adapter Design Pattern
Initially, the adapter design pattern was used to create an intermediate abstraction between view and data. More information about this and other Android design patterns can be found [here](https://www.raywenderlich.com/470-common-design-patterns-for-android-with-kotlin)

The project will use two main adapters: one for cards while playing and another one for statistics. The idea of this pattern is 
to isolate the view from the data, so in case of the adapter used for statistics:
1. A [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView) is used to hold the view
2. [StatisticsRecordAdapter](https://github.com/lascenify/memory-game/blob/master/app/src/main/java/com/example/androidmemorygame/adapters/StatisticsRecordAdapter.kt) is the class that implements the adapter for the RecyclerView. 
3. [GameRecord](https://github.com/lascenify/memory-game/blob/master/app/src/main/java/com/example/androidmemorygame/data/GameRecord.kt) is the class that holds the information that the adapter will get and pass to the view in order to be displayed on the screen.

This, way, the view knows nothing about the GameRecord class and viceversa. The adapter gets the data as a list of GameRecord and fills the view.

## Unit testing
Two main tests have been deployed for these project
1. [MemoryGameLogicTest](https://github.com/lascenify/memory-game/blob/master/app/src/test/java/com/example/androidmemorygame/logic/MemoryGameLogicTest.kt): tests the logic of the game. Asserts that all the cases of clicking cards make the game respond in the correct way.
2. [JSONReaderTest](https://github.com/lascenify/memory-game/blob/master/app/src/test/java/com/example/androidmemorygame/util/JSONReaderTest.kt): [JSONReader](https://github.com/lascenify/memory-game/blob/master/app/src/main/java/com/example/androidmemorygame/util/JSONReader.kt) is a util class used to read the [json file](https://github.com/lascenify/memory-game/blob/master/app/src/main/assets/products.json) containing all the information about the cards. This test guarantees that the json file is read properly.

The [Robolectric](http://robolectric.org/) framework has been used for this purpose. 

## Continuous integration
[GithubActions](https://help.github.com/en/actions/automating-your-workflow-with-github-actions) helps creating custom CI workflows that automate building and testing code. In this project, some simple actions have been integrated to automatically test the pull-requests in every branch.

## Build Instructions
1. Clone or download the repository
```
git clone https://github.com/lascenify/memory-game.git
```
2. Import the project into Android Studio or IntelliJ IDEA

3. Set the configuration:

![Output sample](https://github.com/lascenify/memory-game/blob/master/app/src/main/res/raw/configuration.gif)

## Future work
### Features
1- Include users. The game can ask you a username before starting the game. When a game is finished, the game record would include the username to make it visible in the stats screen.

2- Count down mode. This mode would be a category to be chosen before starting the game, so that there would be two main modes: normal (actual implemented) and count down. Depending on the difficulty chosen, the timer would be shorter or longer.

### Architecture 
1- Include [DataBinding](https://developer.android.com/topic/libraries/data-binding/) and [LiveData](https://developer.android.com/topic/libraries/architecture/livedata). Databinding allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically. Furthermore, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. This awareness ensures LiveData only updates app component observers that are in an active lifecycle state. More info [here](https://android.jlelse.eu/android-architecture-components-livedata-with-data-binding-7bf85871bbd8)

2- Introduce [Room](https://developer.android.com/topic/libraries/architecture/room). The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

3- Include [Dagger](https://dagger.dev/). Dagger aims to address many of the development and performance issues that have plagued reflection-based solutions

Including Room + Dagger reference [here](https://android.jlelse.eu/repository-layer-using-room-and-dagger-2-android-12d311830fd9)

## Libraries

- [Picasso](https://square.github.io/picasso/) for image loading 
- [Google gson](https://github.com/google/gson) used to convert Java Objects into their JSON representation. 
- [Google Guava](https://github.com/google/guava) to include bidirectional hashmaps. 
