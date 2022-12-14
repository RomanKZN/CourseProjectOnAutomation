## Описание приложения
***
### Бизнес часть
***
Приложение представляет из себя веб-сервис.

[![11.png](https://i.postimg.cc/hPLryjQS/11.png)](https://postimg.cc/mtrChTSq)

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).
***
### Техническая часть
***
#### Шаги воспроизведения
1.  Клонировать репозиторий командой *git clone*
```
    https://github.com/RomanKZN/CourseProjectOnAutomation
```
2.  Открыть проект в IntelliJ IDEA
3.  Запустить контейнеры БД в терминале командой *docker-compose up* ([руководство по установке Docker](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md))
4.  Ввести в терминале команду  *java -jar artifacts/aqa-shop.jar*
5.  Открыть в браузере адрес http://localhost:8080/

#### Запуск теста
В IntelliJ IDEA нажать два раза *ctrl*  в появившемся окне ввести
```
gradlew clean test
```
или ввести в терминале  *./gradlew test*.
Так же можно открыть файл TestFormPayment и запустить тест с помощью горячих клавиш Ctrl+Shift+F10

#### Генерация отчета и автоматическое открытие в браузере 
В IntelliJ IDEA нажать два раза *ctrl*  в появившемся окне ввести
```
gradlew allureServe
```