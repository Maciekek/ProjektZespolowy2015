[![Built with Grunt](https://cdn.gruntjs.com/builtwith.png)](http://gruntjs.com/)

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


