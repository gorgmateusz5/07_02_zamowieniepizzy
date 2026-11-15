# Zadanie 3 – czyszczenie formularza

## Jak rozwiązałem problem cofania czyszczenia?

Przed wyczyszczeniem formularza zapisuję wszystkie wartości kontrolek do obiektu `FormState`.

Obiekt przechowuje:
- adres dostawy,
- wybrany rozmiar pizzy,
- rodzaj ciasta,
- ilość pizz,
- stan wszystkich CheckBoxów,
- stan przełącznika dostawy.

Po zapisaniu danych czyszczę wszystkie kontrolki.

Następnie wyświetlam Snackbar z przyciskiem „Cofnij”. Po jego kliknięciu wywołuję metodę `restoreFormState()`, która odczytuje zapisany wcześniej obiekt i przywraca wszystkie wartości.

Dzięki temu użytkownik może cofnąć czyszczenie formularza bez konieczności ponownego wpisywania danych.
