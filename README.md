# Game With Paging 3.0

<br />
<p align="center">
  <h3 align="center">Game App with Paging</h3>

  <p align="center">
    Game Paging Android App
  </p>
</p>
<br />
<br />
Used technologies
<br />
<br />
-MVVM (Modern Updated Architecture)
<br />
-Coroutines(Thread management)
<br />
-Hilt(Dependency Injection Tool)
<br />
-Room(Persistence)
<br />
-Coil
<br />
-Retrofit
<br />
-Kotlin Flow(merge two request)
<br />
-Paging 3.0 (rc01)
<br />
<br />
<br />

Meaning of this application

<b>There are three way for testing api and paging.</b>
<br />
<br />
-You can use only requested
<br />
-You can use only Database
<br />
-You can use Request and Database
<br />

<br />
Api call example
<br />


https://api.rawg.io/api/games?ordering=-released&metacritic=10,100&platforms=4&page_size=10&page=20&key=905bf28dea024135b163cb11b38ced30

<br />
This Api call used network request stage. For this used retrofit and moshi
<br />
You will see while you are paging whole page
<br />
Two apis were merged by Kotlin flow
<br />
Room Data base was used for persistence

