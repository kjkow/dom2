# dom2


Aplikacja desktopowa wykorzystywana w codziennych zadaniach domowych, obejmuje obszary:
- sprzątania - lista z datami (co i kiedy w najbliższym czasie sprzątać) + crud
- przepisy - prosta książka kucharska (crud), proces planowania obiadów na najbliższy tydzień wraz z generowaniem listy zakupów ze składników
- automat do aktualizacji excela z budżetem domowym - wykonuje powtarzalne czynności na arkuszu - zmiany zakresów komórek, generowanie czystego szablonu na nowy rok
- uroczystości - zapisz kto ma kiedy urodziny lub imieniny, a będziesz widział informacje na dole okna aplikacji


# Technologie:
- GUI - javafx
- Backend - Java
- Baza danych - MySql

# Wykorzystane biblioteki:
- org.apache.poi - do pracy z arkuszami excela
- mysql-connector-java - połączenie z bazą danych
- junit - testy jednostkowe

# Inne:
- dziennik aplikacji
- "bazówka" -
  - w wartwie dao (otwieranie/zamykanie połączeń), 
  - na kliencie (cała obsługa przechodzenia między formatkami w javafx, obsługa błędów)
- klasy narzędziowe -
  - do obsługi arkuszy excel
  - operacje na plikach
  - obsługa dziennika aplikacji
- wykorzystanie koncepcji kontekstu zwracanego, przechowującego informację o wystąpieniu błędu, log(dodatkowe info lub treść błędu) i dane biznesowe przenoszone między warstwami

# Instalacja:
- upewnij się że masz javę w wersji 8 oraz mavena
- załóż nową bazę myslq, a następnie puść skrypty przyrostowe z katalogu \dao\src\main\java\github\kjkow\skrypty
- ustaw login, hasło i url hosta w pliku \dao\src\main\resources\github\kjkow\implementacja\bazodanowe.properties
- zbuduj aplikację poleceniem mvn install w głównym katalogu
