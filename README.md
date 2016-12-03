## Це — веб-служба (REST API) для аналізу українських текстів (NLP) за допомогою LanguageTool. ##

## This project provides REST API for analyzing Ukrainian texts with LanguageTool. ##


### Як встановити ###
* Встановити java (JDK 8 або новішу)
* Клонувати проект з github
* Запустити `./gradlew war`
* Скопіювати build/libs/languagetool.war до сервера (напр. <TOMCAT_HOME>/webapps/)
* Запустити або перезапустити сервер (якщо він не робить автоперезавантаження)
* Документація в JSON: http://localhost:8080/languagetool/v2/api-docs
* Документація з UI: http://localhost:8080/languagetool/swagger-ui.html

Вільно розповсюджується за умов ліцензії GPL версії 3.

Copyright (c) 2016 Andriy Rysin, команда БрУК
