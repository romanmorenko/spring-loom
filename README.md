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

