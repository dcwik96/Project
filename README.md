# Ile dasz? - Aplikacja webowa.
## Użytkownicy
### 1.Administrator
* Blokowanie kont użytkowników
* Możliwość usuwania/edycji wszystkich ofert
* Możliwość usuwania/edycji przedmiotów wystawionych
* Dodawanie/edycja kategorii przedmiotów  
* Przeglądanie oczekujących na akceptację przedmiotów
* Wgląd do danych wszystkich użytkowników, możliwość zmiany hasła, zmiany wszystkich danych, brak wglądu do hasła
* Edycja/usuwanie opinii o użytkowniku

### Użytkownik zalogowany    
* Możliwość wystawienia przedmiotu NIE PODAJAC ceny
* Kontaktowanie się z zainteresowanym który podał satysfakcjonującą cenę za przedmiot
* Możliwość ustawienia ceny minimalnej za przedmiot, wtedy oferty poniżej są pokazywane jako “Pozostałe”, taka strefa czerwona, strefa zielona oferty powyżej
* Możliwość usuniecia strefy czerwonej, jezeli ktos nie jest zainteresowany cenami ponizej ceny minimalnej. “Wyłącz oferty pozostałe”
* Możliwość wyboru czasu ‘aukcji’ bezterminowego  lub terminowego, 1, 3, 7 dni. W bezterminowym możliwość zakończenia aukcji w dowolnym czasie po minięciu minimalnego czasu trwania aukcji.
* Możliwość wyboru dowolnej oferty, to nie jest tak, że dobiera nam tylko najwyższą ofertę, sprzedający może wybrać nawet tą najniższą, po wciśnięciu opcji “Wybierz”, jest takie jakby “Dopasowanie”, wtedy kupujący dostaje powiadomienie “Sprzedający wybrał Twoją ofertę, czy akceptujesz?” Po wciśnięciu Tak, następuje wymiana danymi pomiędzy sprzedającym a kupującym, po wciśnięciu Nie sprzedający otrzymuje powiadomienie, a ta oferta zostaje podkreślona na czerwono jako odrzucona. Czas oczekiwania na odpowiedź wstępnie 24h?

### Odwiedzający
* Przegladanie ofert

## 2. Funkcjonalności, co się dzieje

### 1.  MENU
* Strona główna
	* Tutaj będą najnowsze przedmioty, kafelki ze zdjęciem, po najechaniu pokazywałaby się nazwa przedmiotu i skrócony opis( tytuł w razie braku)
		* Losuj przedmiot 
		* Po wejściu w to ukazuje nam się menu, gdzie wybieramy konkretne filtry, kategorie, co potrzebujemy, jeżeli nie chcemy żadnego filtru, to nic nie wybieramy i wciskamy losuj, wtedy pokazuje nam się przedmiot z opisem i mamy na dole TAK i NIE, jeżeli wciśniemy TAK to wyświetla się formułka “Podaj cenę za przedmiot”, wprowadzamy kwotę i sprzedający dostaje nową ofertę, a nam ukazują się kolejne przedmioty

		* Przeglądaj
		* Jeżeli nie chcemy aby losowało nam przedmioty wchodzimy tutaj i ukazują nam się kafelki z miniaturkami zdjęć kategorii, np rower, jakaś lodówka oznaczająca sprzęt RTV. U góry mamy wyszukiwarkę, po wciśnięciu w kategorię, pole wyszukiwania się ogranicza, po lewej ukazują się filtry do wyboru, po wciśnięciu w dowolny filtr, dodajemy kolejne ograniczenia do tego co wyszukaliśmy
