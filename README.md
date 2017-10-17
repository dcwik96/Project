# Ile dasz? - Aplikacja webowa.
## 1.Użytkownicy
### 1.Administrator
* Blokowanie kont użytkowników
* Możliwość usuwania/edycji wszystkich ofert
* Możliwość usuwania/edycji przedmiotów wystawionych //dublet powyzej?
* Dodawanie/edycja kategorii przedmiotów  
* Przeglądanie oczekujących na akceptację przedmiotów //aby dodac ogloszenie musi byc zaakceptowane ?
* Wgląd do danych wszystkich użytkowników, możliwość zmiany hasła, zmiany wszystkich danych, brak wglądu do hasła //mozliwosc zamiany hasla a potem brak wgladu do hasla ?!
* Edycja/usuwanie opinii o użytkowniku

### 2.Użytkownik zalogowany    
* Możliwość wystawienia przedmiotu NIE PODAJAC ceny
* Kontaktowanie się z zainteresowanym który podał satysfakcjonującą cenę za przedmiot
* Możliwość ustawienia ceny minimalnej za przedmiot, wtedy oferty poniżej są pokazywane jako “Pozostałe”, taka strefa czerwona, strefa zielona oferty powyżej
* Możliwość usuniecia strefy czerwonej, jezeli ktos nie jest zainteresowany cenami ponizej ceny minimalnej. “Wyłącz oferty pozostałe”
* Możliwość wyboru czasu ‘aukcji’ bezterminowego  lub terminowego, 1, 3, 7 dni. W bezterminowym możliwość zakończenia aukcji w dowolnym czasie po minięciu minimalnego czasu trwania aukcji.
* Możliwość wyboru dowolnej oferty, to nie jest tak, że dobiera nam tylko najwyższą ofertę, sprzedający może wybrać nawet tą najniższą, po wciśnięciu opcji “Wybierz”, jest takie jakby “Dopasowanie”, wtedy kupujący dostaje powiadomienie “Sprzedający wybrał Twoją ofertę, czy akceptujesz?” Po wciśnięciu Tak, następuje wymiana danymi pomiędzy sprzedającym a kupującym, po wciśnięciu Nie sprzedający otrzymuje powiadomienie, a ta oferta zostaje podkreślona na czerwono jako odrzucona. Czas oczekiwania na odpowiedź wstępnie 24h?
* Możliwość zapytania o przedmiot w przeglądaniu ofert

### Odwiedzający
* Przegladanie ofert
* Ukazuje mu się menu dodaj przedmiot oraz losuj, po wejściu adnotacja na czerwono, że aby móc składać oferty potrzeba być zalogowanym, następnie przekierowanie do panelu logowania/rejestracji

## 2. Funkcjonalności, co się dzieje

### 1. MENU
* Strona główna
	* Tutaj będą najnowsze przedmioty, kafelki ze zdjęciem, po najechaniu pokazywałaby się nazwa przedmiotu i skrócony opis( tytuł w razie braku)

	* Dodaj przedmiot
	* Wybór kategorii, opis, zdjęcia, cena minimalna - Tak, ile? Lub nie. Czas bezterminowy, czy ustalony 1, 3, 7 dni.

		* Losuj przedmiot
		* Po wejściu w to ukazuje nam się menu, gdzie wybieramy konkretne filtry, kategorie, co potrzebujemy, jeżeli nie chcemy żadnego filtru, to nic nie wybieramy i wciskamy losuj, wtedy pokazuje nam się przedmiot z opisem i mamy na dole TAK i NIE, jeżeli wciśniemy TAK to wyświetla się formułka “Podaj cenę za przedmiot”, wprowadzamy kwotę i sprzedający dostaje nową ofertę, a nam ukazują się kolejne przedmioty

		* Przeglądaj
		* Jeżeli nie chcemy aby losowało nam przedmioty wchodzimy tutaj i ukazują nam się kafelki z miniaturkami zdjęć kategorii, np rower, jakaś lodówka oznaczająca sprzęt RTV. U góry mamy wyszukiwarkę, po wciśnięciu w kategorię, pole wyszukiwania się ogranicza, po lewej ukazują się filtry do wyboru, po wciśnięciu w dowolny filtr, dodajemy kolejne ograniczenia do tego co wyszukaliśmy


## 3. Działanie aplikacji, czyli co się dzieje, jak to działa?
## Opis
* Aplikacja działa na zasadzie OLX i Allegro tylko, że nie znamy ceny jaką chcielibyśmy uzyskać za przedmiot,
a więc wystawiamy go i otrzymujemy przeróżne oferty, możemy ustalić cenę minimalną, aby nie przeglądać tych "śmieciowych"
ofert.
## Jak działa to wszystko, czyli co się dzieje?
1. Wystawienie przedmiotu
Wystawiamy przedmiot, ukazują się nam oferty, wszystkie dane są zamazane, sprzedającemu ukazują się tylko informacje
jak długo użytkownik jest już w serwisie, ile uzbierał punktów (Zrobimy system punktów za każdą pomyślnie zakończoną transakcję, np + 5 pkt) i cenę jaką zaoferował za przedmiot oraz forma odbioru (Osobisty/Wysyłka). Po prawej stronie przy każdej ofercie właściciel przedmiotu będzie mógł wcisnąć Wybierz. Nikt inny nie widzi najwyższej kwoty oferowanej za przedmiot.
2. Czas bezterminowy, a ustalony.
Mamy możliwość ustawienia czasu bezterminowego oraz 24h, 3 dni i 7 dni. Jaka jest różnica? Otóż w czasie bezterminowym
sprzedający będzie miał możliwość wybrania oferty odpowiadającej mu, może nawet wybrać najniższą, przy każdej ofercie ma tą opcje, w czasie bezterminowym może to zrobić w dowolnym momencie, natomiast w czasie ustalonym opcje wybierz będą dostępne dopiero po zakończeniu.
3. Opcja wybierz, co dzieje się dalej?
Po wybraniu danej oferty, kupujący zostaje powiadomiony, że jego oferta została zaakceptowana, a on ma możliwość potwierdzenia,
że dalej jest zainteresowany i chce wymienić się danymi. No właśnie, a co w przypadku gdy ofertę odrzuci? Wtedy nie następuje
wymiana danymi, a pozycja z jego ofertą zostaje przeniesiona na dół do pola "Odrzucone", lub "Pozostałe", lub po prostu zostaje
podkreślona na czerwono (Według uznania co będzie bardziej przejrzyste). Aukcja cały czas trwa, zakończenie następuje dopiero po wymianie danymi, gdy kupujący zaakceptuje jeszcze raz ofertę, którą złożył. Co w przypadku, gdy wszyscy kupujący odrzucą ofertę, którą złożyli, a aukcja jest zakończona, bo minęły np. 24h - wtedy nie pozostaje nic innego jak ponowne wystawienie przedmiotu, ale mimo tego w historii użytkownika aukcja wcześniejsza pojawia się w historii "Niesprzedane".
4. Kontaktowanie się ze sprzedającym w trakcie trwania aukcji jest możliwe. Odbywa się to mailowo.
5. W przeglądaniu ofert przy każdym przedmiocie jest opcja "Chcę to!", następnie pokazuje się formułka z zapytaniem "Ile dasz?", wprowadzamy kwotę i to wszystko :). Oferty można wycofać
6. W losowaniu przedmiotów mamy fotki, opis, oraz na samym dole Czerwony kwadracik Nie chcę i zielony Chcę. Po wciśnięciu Nie chcę ukazuje nam się kolejny przedmiot, po wciśnięciu Chcę ukazuje się okienko Ile dasz? No i to wszystko.
7. Każdy użytkownik w swoim profilu może zobaczyć swoje oferty, wystawione przedmioty, historię aukcji w których uczestniczył, ile aukcji wygrał (Doszło do transakcji), ile dostał punktów, może edytować swoje wszystkie dane, itp., itd.
8. Po zakończonej aukcji i po wciśnięciu przez sprzedającego, że doszło do transakcji, kupujący gdy to zatwierdzi oboje mogą wystawić sobie ocenę, czyli Łapka w górę/W dół lapkiwgore++, lapkiwdol++




# [ENG]
## 1.Users
### 1.Administrator
* Blocking users account
* Deleting/editing all adverts
* Managing items categories
* Reviewing items counting on publication
* Managing users accounts
* Deleting/editing users opinion (received/given)

### 2.Registered user   
* Put things up on sale WITHOUT QUOTING A PRICE
* Getting in touch with interested person who is able to pay satisfying price
* Ability to price a product, bids under this price are avaible as "Other", on main page are only bids over and equal given floor price
* User can hide bids under floor price, "Do not show other offer"
* Choosing duration of advert, termless or 1, 3, 7, 14 days. Choosing termless you can close advert anytime
* Ability to choose any bid. Seller can choose even the lowest one. Pressing this bid gives us ability to communicate with interested person who also gets notification about it. If he accept it they can exchange of personal data. If he decline, this bid is cross out. Initially, waiting time on answer is few hours

### Guest
* Browsing adverts

## 2. Functionality,
### 1. Menu
* Main page
	* The newest adverts, photo tiles, after hover the mouse over there will show name and shortened description of thing.
		* Drawing adverts
		* After choosing drawing, we can filter what we want. If we do not choose filter, we just draw everything and after that appear advert with description and we decide if we want to make bid. We write how many we can pay for it and after that seller gets notification. This action repeats and new advert shows up.

		* Browse
		* If we do not want to draw a thing, we can choose this and browse all adverts in table/list, just like eg. eBay. Of course we can search for thing using search option, choosing categories. We can narrow search down by adding more and more categories.
