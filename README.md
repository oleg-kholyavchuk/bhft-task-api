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
Тесты проверяют методы: Get, Post, Put, Delete
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


### Запуск тестов без нагрузочных

Пример запуска тестов:
```bash
  mvn clean test -Dlogin=... -Dpassword=...
```
# Нагрузочное тестирование

### Запуск тестов с тестами JMeter

Пример запуска тестов:
```bash
  mvn clean verify -Dlogin=... -Dpassword=...
```

Тестирование производилось при помощи инструмента JMeter.
Показатели нагрузки измерялись средствами отчетов JMeter.
Во время тестирования приложение было развернуто на одной машине с JMeter.
Цели тестирования выявить "оптимальные" параметры работы приложения. 

Тест-кейс:
Тестируем метод POST.
Создание todo с одинаковыми по длине значениями полей "text", равными 5 символам (примем за среднюю длину описания заметки). И одинаковыми значениями признака "completed" равным false.


Производимые измерения и результаты:
1.	Нагрузка на сервер (Number of Threads – 5, Ramp-up period – 1s).
      Минимальная время ответа от сервера - 7 ms.
      Максимальное время ответа от сервера - 40 ms.
      Отклонение (Std. Dev.) – 13,18.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке - 0%.
      ![img.png](https://user-images.githubusercontent.com/82676512/246671471-9465f512-206f-4dec-9e14-758dc203791e.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_1.png](https://user-images.githubusercontent.com/82676512/246671485-5a133f9d-36b0-461c-974f-5b3daad9acce.png)
      График показывает время ответа на запросы к приложению.
      ![img_2.png](https://user-images.githubusercontent.com/82676512/246671493-fd872682-3e22-4d29-b8cb-b1ea619c3fdd.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_3.png](https://user-images.githubusercontent.com/82676512/246671494-3a46baa9-0348-4060-9e3e-34cef043c466.png)
2.	Нагрузка на сервер (Number of Threads – 20, Ramp-up period – 2s).
      Минимальная время ответа от сервера - 8 ms.
      Максимальное время ответа от сервера - 13 ms.
      Отклонение (Std. Dev.) – 0.82.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке - 0%.
      ![img_4.png](https://user-images.githubusercontent.com/82676512/246671502-6517b7e7-0538-4518-ba56-87e74be19f34.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_5.png](https://user-images.githubusercontent.com/82676512/246671508-4121da7a-b032-47b2-99c5-dfbed65ef203.png)
      График показывает время ответа на запросы к приложению.
      ![img_6.png](https://user-images.githubusercontent.com/82676512/246671512-07faa48c-e46f-48fc-bc46-95fc80473563.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_7.png](https://user-images.githubusercontent.com/82676512/246671525-9c85dee7-fd05-447b-bddb-35b8a209ca84.png)
3.	Нагрузка на сервер (Number of Threads – 100, Ramp-up period – 5s).
      Минимальная время ответа от сервера - 4 ms.
      Максимальное время ответа от сервера - 41 ms.
      Отклонение (Std. Dev.) – 3.69.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке – 0%.
      ![img_8.png](https://user-images.githubusercontent.com/82676512/246671533-6219e401-5d14-47ea-8898-034a68678933.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_9.png](https://user-images.githubusercontent.com/82676512/246671542-c09bef51-45d9-4434-aade-c7dd6561bb88.png)
      График показывает время ответа на запросы к приложению.
      ![img_10.png](https://user-images.githubusercontent.com/82676512/246671548-2fba1e9c-fb4a-48e8-a234-d24794f0b53d.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_11.png](https://user-images.githubusercontent.com/82676512/246671559-63410eca-7f0a-4fbe-b2df-733bcb6d498f.png)
4.	Нагрузка на сервер (Number of Threads – 400, Ramp-up period – 10s).
      Минимальная время ответо от сервера - 3 ms.
      Максимальное время ответа от сервера – 84 ms.
      Отклонение (Std. Dev.) – 5.06.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке – 0%.
      ![img_12.png](https://user-images.githubusercontent.com/82676512/246671562-6ebab998-b04f-4b0e-9af0-e5b88d6fd36c.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_13.png](https://user-images.githubusercontent.com/82676512/246671580-893e1b5b-2673-4969-830e-25440e985b26.png)
      График показывает время ответа на запросы к приложению.
      ![img_14.png](https://user-images.githubusercontent.com/82676512/246671583-13ebe623-b607-4e00-8fcb-a1f1d81768b9.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_15.png](https://user-images.githubusercontent.com/82676512/246671588-66b6f002-5e20-47ce-bc9e-02a5f7832a6d.png)
5.	Нагрузка на сервер (Number of Threads – 2000, Ramp-up period – 20s).
      Минимальная время ответа от сервера - 2 ms.
      Максимальное время ответа от сервера – 1350 ms.
      Отклонение (Std. Dev.) – 48.76.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке – 0%.
      ![img_16.png](https://user-images.githubusercontent.com/82676512/246671592-27d73fb9-527c-4ea9-9b1c-ff8bcd4c5da2.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_17.png](https://user-images.githubusercontent.com/82676512/246671597-097ac248-bdd2-4d09-a5f8-a61200ae921d.png)
      График показывает время ответа на запросы к приложению.
      ![img_18.png](https://user-images.githubusercontent.com/82676512/246671600-52ccd374-cf82-4f56-a84e-135889978571.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_19.png](https://user-images.githubusercontent.com/82676512/246671604-8ec4e6bd-4ac1-4e47-bf2b-ad0d6e11baf5.png)
6.	Нагрузка на сервер (Number of Threads – 3000, Ramp-up period – 20s).
      Минимальная время ответа от сервера - 2 ms.
      Максимальное время ответа от сервера – 1381 ms.
      Отклонение (Std. Dev.) – 128.09.
      Количество ответов от сервера с кодом ответа не 201 при такой нагрузке – 0%.
      ![img_20.png](https://user-images.githubusercontent.com/82676512/246671609-47018e90-a789-4231-8f76-1f3227786443.png)
      График показывает контроля правильности подачи нагрузки. Количество Thread.
      ![img_21.png](https://user-images.githubusercontent.com/82676512/246671615-dee773e3-a429-4e44-8f62-7466c24cea61.png)
      График показывает время ответа на запросы к приложению.
      ![img_22.png](https://user-images.githubusercontent.com/82676512/246671619-898f6981-b61d-4660-b258-1ddf48d2ee94.png)
      График показывает зависимость распределения времени от количества запросов.
      ![img_23.png](https://user-images.githubusercontent.com/82676512/246671623-3b500e89-4d98-4150-9c3a-794feb28fdf9.png)

Вывод: Из результатов тестов видно чем больше показатели Number of Threads и Ramp-up period тем больше показатель 
Максимальное время ответа. Так же пропорционально растет отклонение. На графиках отчетливо видно, что на начальных 
показателях количество Thread, и количество распределения времени от количества запросов. Практически не измены. 
Время ответа сервера колеблется в незначительных пределах. При нагрузке Number of Threads – 400, Ramp-up period – 10s,
начинается отслеживание более существенные отклонения от показателей, что были прежде. Так по графикам видно увеличение
количества thread. Так же большой пик времени ответа. При нагрузке Number of Threads – 2000, Ramp-up period – 20s.
Уже наблюдаются существенное ухудшение производительности. Максимальное время ответа от сервера – 1350 ms. 
Отклонение (Std. Dev.) – 48.76. В графиках "количество Thread" и "показателях времени ответа на запросы к приложению"
появилось больше пиковых моментов. Сами графики стали менее плавными, что показывает не плавность протекания процесса.
Так же на графиках показывающих зависимость распределения времени от количества запросов, наблюдаем увелечения количества
времени ответа.
Из полученных результатов и целей плановой нагрузки делаем вывод, что наиболее оптимальная нагрузка для приложения является показатели 
Number of Threads – от 0 да 400, Ramp-up period – от 0 до 10s. При нагрузке Number of Threads – 2000, Ramp-up period – 20s
и выше видим существенные временные задержки, что может негативно восприниматься конечными пользователями. Само же приложение
продолжает работать и каких либо ошибок не возникает


