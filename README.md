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
* Кнопка фильтра пользователей по следующим признакам:
    * участие в различных разделах
    * количество сообщений в заданном интервале времени
* Кнопка перехода на страницу создания пользователя, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретного пользователя
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
* Кнопка перехода на страницу конкретного раздела
* Кнопка создания раздела, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретного раздела
* Создание раздела, если владелец текущей сессии - модератор

#### Список тем в разделе
Данные:
* Список тем в разделе
* Кнопка перехода на страницу конкретной темы
* Кнопка создания темы, если владелец текущей сессии - обычный пользователь
* Кнопка удаления раздела, если владелец текущей сессии - модератор

Действия:
* Переход на страницу конкретной темы
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
Для каждого сценарий нужно войти в аккаунт.
* Вход в аккаунт
    1. Ввести логин и пароль на странице входа
    2. Нажать кнопку входа (автоматический переход на главную страницу)

* Получение списка пользователей
    0. Перейти на главную страницу
    0. Перейти на страницу списка пользователей
    0. При желании применить фильтр

* Просмотр своей персональной страницы
    0. Войти в аккаунт на странице входа
    0. Перейти на свою персональную страницу с помощью соответствующей кнопки с любой страницы

* Просмотр персональной страницы другого пользователя
    0. Войти в аккаунт на странице входа
    0. 

* Выход из аккаунта
    0. Войти в аккаунт на странице входа

* Получение списка разделов
    0. Войти в аккаунт на странице входа

* Получение списка тем в разделе
    0. Войти в аккаунт на странице входа

* Получение списка сообщений в теме раздела
    0. Войти в аккаунт на странице входа

_Только для модераторов:_
* Создание нового пользователя
    0. Войти в аккаунт на странице входа

* Блокирование пользователя
    0. Войти в аккаунт на странице входа

* Создание раздела
    0. Войти в аккаунт на странице входа

* Удаление раздела
    0. Войти в аккаунт на странице входа

* Удаление темы
    0. Войти в аккаунт на странице входа

* Удаление сообщения
    0. Войти в аккаунт на странице входа

_Только для обычных пользователей:_
* Создание темы
    0. Войти в аккаунт на странице входа

* Создание сообщения в теме
    0. Войти в аккаунт на странице входа
