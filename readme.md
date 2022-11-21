# Backend

Команда: !False progers

### Установка

1. Пререквизиты: docker (20.10.14), docker-compose (1.29.2)

2. Запуск сервиса локально
```
cd cicd && docker-compose up --build -d
```

3. Бэкенд будет доступен на `localhost:8000`


### Деплой на виртуалку
1. Зайти на гитхабе в Actions
2. Слева в Workflows выбрать Run server deploy
3. Справа нажать Run workflow
4. Нажать на зеленую кнопку Run workflow

### API бэкенда
API бэкенда подробно описано на: http://194.58.123.176:8000/swagger-ui.html
либо локально на localhost:8000/swagger-ui.html

Пример:
![image](https://user-images.githubusercontent.com/84656815/203142745-4c7fa4e2-6e80-4952-808e-d8fa578c9fe0.png)

### Стркуткура и используемые технологии
- Бэкенд написан с использованием Spring Framework
- В качестве БД используется MySQL
- Интегрируется с ApiLayer для получения текущих курсов валют и истории (https://apilayer.com/marketplace/currency_data-api)
- Для авторизации необходимо передать в header'aх http запроса токен пользователя

### Ссылки

[RnD](https://github.com/tutkarma/scb-nfp-rnd) (сервис предсказания цены валюты)
[Frontend](https://github.com/comp-master-byte/sovkombank-hackathon)
