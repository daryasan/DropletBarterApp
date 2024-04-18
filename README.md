# Droplet
## Описание
Курсовая работа, выполнена Судаковой Дарьей, студенткой ФКН НИУ ВШЭ на программе "Программная инженерия". Приложение создано для обмена вещами (бартера).
## Как запустить?
1. Необходимо скачать архив или склонировать репозиторий на личный компьютер. Рекомендуется запускать приложение в Android Studio. 
2. Перед началом работы необходимо убедиться, что на вашем устройстве установлен PostgreSQL, а также pgAdmin. Приложение использует БД со следующей структурой:

   ![image](https://github.com/daryasan/DropletBarterApp/assets/72216853/818c6958-eda7-4ce5-abfd-ad53f1b9cbef)

Так как сервер разворачивает локально на компьютере, необходимо воссоздать данную структуру БД на вашем устройстве.

3. Далее необходимо запустить сервер, который находится в папке [backend](backend). Это можно сделать с помощью IntelijIdea, открыв в ней проект, выбрав конфигурацию и нажав на стандартную кнопку запуска проекта, а также любым другим удобным способом компиляции Java-кода.
   
4. После всех приготовлений можно запустить мобильное приложение, которое находится в папке [frontend](frontend), с помощью Android Studio, выбрав устройство-эмулятор и нажав на стандартную кнопку запуска приложения.
