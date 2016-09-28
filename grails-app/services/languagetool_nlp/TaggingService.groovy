package languagetool_nlp

import org.languagetool.*
import org.languagetool.tokenizers.*
import org.languagetool.language.*
import org.languagetool.uk.*
import org.languagetool.tokenizers.uk.*


class TaggingService {
    static transactional = false

//    def WORD_PATTERN = ~/[а-яіїєґА-ЯІЇЄҐa-zA-Z0-9]/

    JLanguageTool langTool = new MultiThreadedJLanguageTool(new Ukrainian());


    def tag(def body, def params) {
        def text = body.text

        List<AnalyzedSentence> analyzedSentences = langTool.analyzeText(text);

        analyzedSentences.collect { sent ->
            sent.tokens
            .findAll { it.readings[0].POSTag != "SENT_START" }
            .findAll { ! (it.token ==~ /[ \u00A0]/) }
            .collect { token ->
        
                def lemmas = token.readings[0].lemma == null
                    ? null
                    : token.readings.collect { reading ->
                        [lemma: reading.lemma,
                        tag: reading.POSTag]
                    }

        
                def response = [ "token": token.token ]
                if( lemmas != null ) {
                    response["lemmas"] = lemmas
                }
            
                return response
            }
        }

    }
}
