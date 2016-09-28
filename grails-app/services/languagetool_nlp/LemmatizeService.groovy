package languagetool_nlp

import org.languagetool.*
import org.languagetool.tokenizers.*
import org.languagetool.language.*
import org.languagetool.uk.*
import org.languagetool.tokenizers.uk.*


class LemmatizeService {
    static transactional = false

    def WORD_PATTERN = ~/[а-яіїєґА-ЯІЇЄҐa-zA-Z0-9]/

    JLanguageTool langTool = new MultiThreadedJLanguageTool(new Ukrainian());


    def lemmatize(def body, def params) {
        def text = body.text
        params.firstLemma = true

        List<AnalyzedSentence> analyzedSentences = langTool.analyzeText(text);


        analyzedSentences.collect { AnalyzedSentence analyzedSentence ->
            analyzedSentence.getTokens().collect { AnalyzedTokenReadings readings ->
                if( readings.isWhitespace() || readings.getAnalyzedToken(0).lemma == null ) {
                    readings.token
                }
                else {
                    params.firstLemma ? readings[0].getLemma() : readings*.lemma.unique().join("|")
                }
            }.join("")
        }

    }
}
