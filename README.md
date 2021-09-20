# spring-loom
Простой проект для тестирования виртуальных потоков на Project Loom Early-Access Builds

# Как запустить
Качаем [Project Loom Early-Access Builds](https://jdk.java.net/loom/)
Импортируем проект в Idea
1. Добавляем JDK18: File -> Project Structure -> Platform Settings -> SDK -> + -> Add JDK -> Путь до скаченной JDK18 ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK.png)
2. File -> Project Structure -> Project -> Project SDK: -> Выбираем 18 version 18 
3. File -> Project Structure -> Project -> Project language level: -> Выбираем SDK default (X - Experimental features) ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK2.png)
4. File -> Settings -> Build, Execution, Deployment -> Java Compiler -> Per-module bytecode version -> В строке таблицы, во второй колонке руками ставим 18 ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK3.png)
5. Добавляем конфигурацию для maven  (clean install) и собираем проект ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK_m.png)
7. Добавляем конфигурацию для запуска SpringBoot проекта: ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK4.png)
8. Добавляем конфиграцию для запуска теста ![](https://github.com/romanmorenko/spring-loom/blob/d109c783a8c297dc5793fb62540d4cf68d667947/JDK5.png)
9. Запускаем  SpringBoot проект, после старта запускаем LoadTesting

