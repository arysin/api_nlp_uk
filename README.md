## REST API для аналізу українських текстів (NLP) за допомогою LanguageTool

This project provides REST API for analyzing Ukrainian texts with LanguageTool.

### Quick Start

The `/uber` endpoint accepts a batch of texts, processes all of them parallel and returns [sentences, tokens, lemmas] for each text.

Start the web server on port 8080:

```
docker run -p 8080:8080 ghcr.io/proger/api_nlp_uk:uber
```

Run example query:

```
curl -s  -X POST -H "Content-Type: application/json" -d "['Привіт, котанче. Як справи?', 'Ну шо, приїхали?']" http://localhost:8080/uber \
  | jq -c '.[]'
```

This will respond like:
```
[[["Привіт, котанче. ",["Привіт",","," ","котанче","."," "],["привіт",","," ","котанче","."," "]],["Як справи?",["Як"," ","справи","?"],["як"," ","справа","?"]]],[["Ну шо, приїхали?",["Ну"," ","шо",","," ","приїхали","?"],["ну"," ","шо",","," ","приїхати","?"]]]]
```

Вільно розповсюджується за умов ліцензії GPL версії 3.

Copyright (c) 2016 Andriy Rysin, команда БрУК
