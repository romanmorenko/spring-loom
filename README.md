# spring-loom
Простой проект для тестирования виртуальных потоков на Project Loom Early-Access Builds

# Как запустить
Качаем [Project Loom Early-Access Builds](https://jdk.java.net/loom/)
Импортируем проект в Idea
1. Добавляем JDK18: File -> Project Structure -> Platform Settings -> SDK -> + -> Add JDK -> Путь до скаченной JDK18
2. File -> Project Structure -> Project -> Project SDK: -> Выбираем 18 version 18
3. File -> Project Structure -> Project -> Project language level: -> Выбираем SDK default (X - Experimental features)
4. File -> Settings -> Build, Execution, Deployment -> Java Compiler -> Per-module bytecode version -> В строке таблицы, во второй колонке руками ставим 18
5. Собираем проект maven  (clean install)
6. Добавляем конфигурацию для запуска SpringBoot проекта:
7. Добавляем конфиграцию для запуска теста
8. Запускаем  SpringBoot проект затем LoadTesting

# [Описание API](http://localhost:8083/swagger-ui/#)


# Параметры нагрузочного теста
В классе LoadTesting в константах задаются параметры нагрузки:
1. BOOKS_COUNT - колличество добавляемых книг
2. SLEEPS_COUNT - количество задач
3. IS_VIRTUAL - использовать виртуальные потоки
4. LOG_RESPONSES - логировать ответы
5. LOG_ERRORS - логировать запросы
