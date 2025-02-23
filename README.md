# Практикум по Web-технологиям
## Вариант №13: Web-форум
### Описание страниц
Схема навигации между страницами

![Схема навигации между страницами](https://github.com/user-attachments/assets/5b27a01c-93d0-4b7f-913f-f11162753df2)

С любой страницы можно перейти на главную страницу с помощью соответствующей кнопки.

С любой страницы можно перейти на свою персональную страницу пользователя с помощью соответствующей кнопки.
#### Страница входа
Данные:
* Поле для ввода логина
* Поле для ввода пароля
* Кнопка для входа

Действия:
* Вход в аккаунт пользователя/модератора с помощью логина и пароля и автоматический переход на главную страницу

#### Главная страница
Данные:
* Кнопка для перехода на страницу со списком пользователей
* Кнопка для перехода на страницу со списком разделов

Действия:
* Переход на страницу со списком пользователей
* Переход на страницу со списком разделов

#### Список пользователей
Данные:
* Список логинов пользователей
* Поле поиска пользователя по логину
* Кнопка фильтра пользователей по следующим признакам:
    * участие в различных разделах
    * количество сообщений в заданном интервале времени
* Кнопка перехода на страницу создания пользователя, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретного пользователя
* Поиск пользователя по логину
* Возможность отфильтровать пользователей по перечисленным выше признакам
* Переход на страницу создания пользователя, если владелец текущей сессии - модератор

#### Страница создания пользователя
Данные:
* Поле для ввода логина
* Поле для ввода пароля
* Поле для выбора прав из двух вариантов:
    * пользователь
    * модератор
* Кнопка подтверждения создания пользователя/модератора

Действия:
* Создание нового пользователя/модератора

#### Персональная страница пользователя
Данные:
* Логин
* Дата регистрации
* Права
* Кнопка блокирования пользователя, если владелец текущей сессии - модератор
* Кнопка выхода из аккаунта и автоматического перехода на страницу входа, если это персональная страница владельца текущей сессии

Действия:
* Блокировка пользователя, если владелец текущей сессии - модератор
* Выход из аккаунта и автоматический переход на страницу входа, если это персональная страница владельца текущей сессии.

#### Список разделов
Данные:
* Список разделов
* Поле поиска раздела по названию
* Кнопка перехода на страницу конкретного раздела
* Кнопка создания раздела, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретного раздела
* Поиск раздела по названию
* Создание раздела, если владелец текущей сессии - модератор

#### Список тем в разделе
Данные:
* Список тем в разделе
* Поле поиска темы по названию
* Кнопка перехода на страницу конкретной темы
* Кнопка создания темы, если владелец текущей сессии - обычный пользователь
* Кнопка удаления раздела, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретной темы
* Поиск темы по названию
* Создание темы, если владелец текущей сессии - обычный пользователь
* Удаление раздела, если владелец текущей сессии - модератор

#### Список сообщений в теме
Данные:
* Список сообщений в теме
* Кнопка перехода на страницу создания сообщения в этой теме, если владелец текущей сессии - обычный пользователь
* Кнопка удаления для каждого сообщения в теме, если владелец текущей сессии - модератор
* Кнопка удаления темы, если владелец текущей сессии - модератор

Действия:
* Переход на страницу создания сообщения в этой теме, если владелец текущей сессии - обычный пользователь
* Удаление любого сообщения в теме, если владелец текущей сессии - модератор
* Удаление темы, если владелец текущей сессии - модератор

#### Страница создания сообщения в теме
Данные:
* Поле для ввода текста сообщения
* Поле для прикрепления файлов к сообщению
* Кнопка подтверждения отправки сообщения в конкретную тему

Действия:
* Ввод текста сообщения
* Прикрепление файлов к сообщению
* Отправка сообщения в конкретную тему


### Схема базы данных приложения
![Схема базы данных](https://github.com/user-attachments/assets/4eea07e0-1e01-440a-b4d0-b87ac1e85eca)

### Описание use cases/сценариев использования
Для каждого сценария нужно войти в аккаунт.
* Вход в аккаунт
    1. Ввести логин и пароль на странице входа
    2. Нажать кнопку входа (автоматический переход на главную страницу)

* Получение списка пользователей
    1. Перейти на главную страницу
    2. Перейти на страницу списка пользователей
    3. При желании применить фильтр

* Просмотр своей персональной страницы
    1. Перейти на свою персональную страницу с помощью соответствующей кнопки с любой страницы

* Просмотр персональной страницы другого пользователя
    1. Получить список пользователей
    2. Воспользоваться поиском по логину/Вручную найти в списке нужного пользователя
    3. Нажать на его логин

* Выход из аккаунта
    1. Перейти на свою персональную страницу
    2. Нажать кнопку выхода из аккаунта

* Получение списка разделов
    1. Перейти на главную страницу
    2. Перейти на страницу списка разделов

* Получение списка тем в разделе
    1. Получить список разделов
    2. Воспользоваться поиском раздела по названию/Вручную найти в списке нужный раздел
    3. Нажать на название нужного раздела

* Получение списка сообщений в теме раздела
    1. Получить список тем в разделе
    2. Воспользоваться поиском темы по названию/Вручную найти в списке нужную тему раздела
    3. Нажать на название нужной темы

_Только для модераторов:_
* Создание нового пользователя
    1. Перейти на страницу списка пользователей
    2. Ввести логин, пароль, выбрать вариант прав из двух: пользователь, модератор
    3. Нажать кнопку подтверждения создания пользователя/модератора

* Блокирование пользователя
    1. Перейти на персональную страницу пользователя
    2. Нажать на кнопку блокировки пользователя

* Создание раздела
    1. Перейти в список разделов
    2. Нажать на кнопку создания раздела
    3. Ввести название раздела в соответствующее поле

* Удаление раздела
    1. Перейти на список тем раздела
    2. Нажать на кнопку удаления раздела

* Удаление темы
    1. Перейти на список сообщений по теме раздела
    2. Нажать на кнопку удаления темы

* Удаление сообщения
    1. Перейти на список сообщений по теме раздела
    2. Нажать на кнопку удаления сообщения

_Только для обычных пользователей:_
* Создание темы
    1. Перейти на список тем раздела
    2. Нажать на кнопку создания темы
    3. Ввести название темы в соответствующее поле

* Создание сообщения в теме
    1. Перейти на список сообщений по теме раздела
    2. Перейти на страницу создания сообщения по теме
    3. Ввести текст сообщения, при желании прикрепить файлы
    4. Нажать на кнопку подтверждения отправки сообщения в конкретную тему
