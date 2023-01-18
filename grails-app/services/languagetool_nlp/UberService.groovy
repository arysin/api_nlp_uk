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

                    words = adjustTokens(words, true).collect { word ->
                        word.replace("\n", options.newLine).replace("\t", " ")
                    };

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
    
    public static Pattern WITH_PARTS = ~/(?iu)([а-яіїєґ][а-яіїєґ'\u2019\u02bc-]+)[-\u2013](бо|но|то|от|таки)$/
    
    List<String> adjustTokens(List<String> words, boolean withHyphen) {
        List<String> newWords = []
        String hyph = withHyphen ? "-" : ""
        
        words.forEach { String word ->
            String lWord = word.toLowerCase().replace('\u2013', '-')
            if( lWord.contains('-') && ! (lWord in notParts) ) {
                def matcher = WITH_PARTS.matcher(word)

                if( matcher ) {
                    newWords << matcher.group(1) << hyph + matcher.group(2)
                    return
                }
            }

            newWords << word
        }
        
        return newWords
    }
}
