# Тестовое задание

Прикрепленное image содержит приложение, реализующее самый простой диспетчер задач с CRUD-операциями. Образ можно загрузить с помощью `docker load` и запустить с помощью `docker run -p 8080:4242`.

Задача состоит:

Требуется написать несколько тестов для проверки функциональности приложения. Мы не предоставляем строгих спецификаций, потому что домен достаточно прост. Поэтому необходимо придумывать кейсы самостоятельно.

# Реализация задания

Для начала работы нужно убедиться, что установлен Docker.
команда в терминале: `docker version`.

Дальше нам нужно запустить нужный нам контейнер.
Для запуска используем команду в терминале `docker start [Name container]`.


Реализовал фреймворк, используя REST Assured, Java 11, Maven, JUnit5, AssertJ, Allure Report.

Существуют 2 группы тестов: позитивные и негативные.
Тесты проверяют методы: Get, Post, Put, Delite
Тесты проходят в многопоточном режиме.
После прогона тестов открывается HTML с результатом прогонов.

Настройки JUnit5 в junit-platform.properties :
```
    junit.jupiter.execution.parallel.enabled=true
    junit.jupiter.execution.parallel.mode.default=concurrent
    junit.jupiter.execution.parallel.mode.classes.default=concurrent
    junit.jupiter.execution.parallel.config.strategy=fixed
    junit.jupiter.execution.parallel.config.fixed.parallelism=2
``` 


# Запуск тестов

Пример запуска тестов:
```bash
  mvn clean test -Durl=... -Dlogin=... -Dpassword=...
```