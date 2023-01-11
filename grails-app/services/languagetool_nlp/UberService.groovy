package languagetool_nlp

import org.languagetool.*
import org.languagetool.tokenizers.*
import org.languagetool.language.*
import org.languagetool.uk.*
import org.languagetool.tokenizers.uk.*


import static groovyx.gpars.GParsPool.*

class UberService {
    static transactional = false

    def WORD_PATTERN = ~/[а-яіїєґА-ЯІЇЄҐa-zA-Z0-9]/

    SRXSentenceTokenizer sentTokenizer = new SRXSentenceTokenizer(new Ukrainian())
    UkrainianWordTokenizer wordTokenizer = new UkrainianWordTokenizer()
    JLanguageTool langTool = new MultiThreadedJLanguageTool(new Ukrainian());

    List<List<List<String>>> uber(def body, def params) {
        def batch = body

        withPool {
            batch.collectParallel { text ->
                List<String> sentences = sentTokenizer.tokenize(text);
                List<AnalyzedSentence> analyzedSentences = langTool.analyzeSentences(sentences);

                [sentences, analyzedSentences].transpose().collect {
                    def sent = it[0]
                    def analyzedSentence = it[1]
                    def words = wordTokenizer.tokenize(sent).findAll { WORD_PATTERN.matcher(it) }
                    def lemmas = analyzedSentence.getTokens().collect { AnalyzedTokenReadings readings ->
                        if( readings.isWhitespace() || readings.getAnalyzedToken(0).lemma == null ) {
                            readings.token
                        }
                        else {
                            readings[0].getLemma()
                        }
                    }
                    
                    [sent, words, lemmas]
                }
            }
        }
    }
}
