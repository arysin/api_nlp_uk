## Це — веб-служба (REST API) для аналізу українських текстів (NLP) за допомогою LanguageTool. ##

## This project provides REST API for analyzing Ukrainian texts with LanguageTool. ##


### Як встановити ###
* Встановити java (JDK 8 або новішу)
* Клонувати проект з github
* Запустити `./gradlew bootRun`
* Документація в JSON: http://localhost:8080/v2/api-docs
* Документація з UI: http://localhost:8080/swagger-ui.html

### Як використовувати через Docker ###

```
docker build -t api_nlp_uk:latest .
docker run -it -p 8080:8080 api_nlp_uk:latest
curl -X POST -H "Content-Type: application/json" -d "{'text': 'Сьогодні у продажі. 12-те зібрання творів 1969 р. І. П. Котляревського.'}" http://localhost:8080/lemmatize/
```

Або можна викоритсовувати Docker image який підтримується в іншому репозиторії.

```
docker run -it -p 5000:5000 chaliy/api_nlp_uk:latest
```

Вільно розповсюджується за умов ліцензії GPL версії 3.

Copyright (c) 2016 Andriy Rysin, команда БрУК
