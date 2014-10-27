[![Built with Grunt](https://cdn.gruntjs.com/builtwith.png)](http://gruntjs.com/)  [![Build Status](https://travis-ci.org/Maciekek/ProjektZespolowy2015.svg?branch=master)](https://travis-ci.org/Maciekek/ProjektZespolowy2015)

**MoneyGiver**
===================
Aplikacja na zajęcia "Projekt Zespołowy 2014/2015"

----------

Wstępny opis:
----------

>Aplikacja polegająca na wspieraniu użytkownika w kontroli jego finansów. Użytkownik po jakimś zakupie dodaje dany wydatek przez aplikacje na telefonie.Wszystkie takie transakcje są odpowiednio zapisywane na serwerze. Użytkownik przez aplikacje mobilną jak i przez webową ma kontrolę nad swoim budżetem. Przez co użytkownik może wiedzieć na co wydał pieniądze, kiedy itp. Dodatkowo można zaplanować w aplikacji swoje fundusze. Np. wypłate mam 4tysiące. Z tego muszę opłacić mieszkanie, dojazdy itp. I jeśli okazało by się, że np. z wolnej gotówki zostało mi 50% to aplikacja mnie o tym poinformuje, albo wręcz zaalarmuje. Dodatkowo, w wersji webowej, użytkownik będzie mógł zaplanować swoje oszczędności. Np. zakłada, że przez rok oszczędzi 10tys. złotych. Po każdym miesiącu aplikacja informuje, czy użytkownikowi uda się to oszczędzić przy takim tempie wydatków. 



Opis elementów technicznych:
---------------
>**Tip** Akapit będzie rozwijany wraz z rozwijaniem aplikacji.


Branche i rodzaje tasków 
-----------
Generalnie każdy task powinien być realizowany poprzez taska w issueTrackerze gitowy.
W zależności od rodzaju zadania mamy różne rodzaje
*  Develop - rodzaj zadania, które polega na rozwijanu jakiejś części aplikacji. Np. dodanie nowej funkcjonalości
*  Bug - wszelkie sprawy związane ze zgłaszaniem bugów. 
*  Refacor - wszelkie sprawy związane z poprawianiem jakości aplikacji, bez dodatkowych funkcjonalności

**Branche**

Każda realizacja taska powinna być realizowana w osobnym branchu. 
Konwencja nazewnicza:

```sh
    develop/NumerTaska
    Przykład: dev/#3
    
    bug/NumerTaska
    Przykład: bug/#3
    
    refactor/NumerTaska
    Przykład: ref/#3
```

Pozwoli nam to na filtrowanie tasków i utrzymanie porządku pomiędzy mergami a taskami w issue trackerze.


------------------------


Informacje użytkowe w aplikacji webowej
--------------

Po ściągnieciu najnowszej wersji aplikacji, aby uruchomić projekt webowy lokalnie wystarczy wpisać w konsoli:
```sh
    npm start
```

To polecenie wykona poniższe czynności:
```sh
    npm install -g grunt-cli            -instaluje grunt-cli
    npm install -g nodemon              -nodemon, jest to programik, który restartuje serwer po każdej zmianie serwer aktualizując zmiany
    bower install                       -ściaga takie paczki jak angularJS itp.
    npm install                         -ściąga wszyskie zależności
    nodemon serwer.js                   -uruchamia serwer.
```
Taka sekwencja zawsze zajmuję chwilę czasu. Dlatego jak przynajmniej raz puści się komedę npm start, to potem szybciej robi się to za pomocą 
```sh
    nodemon serwer.js
```

--------------

**Grunt**
Do projektu dodałem narzędzie [Grunt](http://gruntjs.com/)
Jest to narzędzie do automatyzacji pewnych tasktów. Będzie to rozwijane wraz z potrzebami. 
Póki są dodane 2 rzeczy:
```sh
    grunt watch         obserwuje pliki *.js po każdej zmiane sprawdza poprawności semantyki (jshit)
    grunt htmlangular   obserwuje pliki *.html i działa jak watch
```

Nardzędzie Grunt odpala się z konsoli poleceniem:
```sh
    grunt
```
Od tego momenty pliki .js i .html będą po każdej zmianie sprawdzane.

------------------


Scripts
---------------------

W folderze 
```sh
    ./scripts
```
zdajduję się mały skrypcik o nazwie *droplet*.
Służy on do łączenia z wirtualną maszyną. Wystarczy go odpalić w ten sposób
```sh
    droplet.bat NazwaUżytkownika
```
i następnie swoje hasło. Ułatwia to podłączenie do VM bez konieczności pamiętania IP

Później dodam też skrypty na bazę danych.




Testy
---------------


**Protractor**
Testy te służą do testowania zachowań front-endu. 

```sh
    npm run protractor
```


**Unit serwer**

Serwer również powinien być testowany. 
Testy serwera znajdują się w 
```sh
    /WebApp/test/serwer-unit/*.spec.js
```

Odpalanie testów z konsoli: 
```sh
    grunt test
```


