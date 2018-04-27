<img alt="Logo" src="http://coderslab.pl/svg/logo-coderslab.svg" width="400">

# Java EE  - Hibernate Relacje

### Zadania.

#### Zadanie 1 - rozwiązywane z wykładowcą

1. Połącz klasę wydawcy i książkę relacją jeden do wielu.
2. Pamiętaj o zmianie geterów i seterów dla zmienionych pól.
3. Zmodyfikuj kontroler do obsługi książek tak, aby poprawnie wykonać wszystkie operacje.

-----------------------------------------------------------------------------

#### Zadanie 2

1. Połącz klasę autora i książkę relacją wiele do wielu.
2. Pamiętaj o zmianie geterów i seterów dla zmienionych pól.
3. Zmodyfikuj kontroler do obsługi książek tak, aby poprawnie wykonać wszystkie operacje.

#### Zadanie 3

1. Utwórz klasę `Person` zawierającą pola 
2. Encja ma zawierać następujące pola:
- id 
- login
- password
- email

3. Utwórz klasę `PersonDetails` zawierającą pola 
4. Encja ma zawierać następujące pola:
- firstName
- lastName
- streetNumber
- street
- city

5. Połącz encje za pomocą relacji `@OneToOne`.
6. Utwórz kontroler, realizujący operacje CRUD (create, read, update, delete).


#### Zadanie 4
1. Uzupełnij utworzone klasy Dao o metodę do pobierania listy wszystkich obiektów.
2. Wykorzystaj w tym celu JPQL.
3. W klasie `BookDao` dodaj metodę `getRatingList(int rating)`, która na podstawie 
otrzymanego parametru pobierze książki o zadanym parametrze.
