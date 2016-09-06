package languagetool_nlp

import org.languagetool.*
import org.languagetool.tokenizers.*
import org.languagetool.language.*
import org.languagetool.uk.*
import org.languagetool.tokenizers.uk.*


class TokenizeService {
    def WORD_PATTERN = ~/[а-яіїєґА-ЯІЇЄҐa-zA-Z0-9]/

    SRXSentenceTokenizer sentTokenizer = new SRXSentenceTokenizer(new Ukrainian())
    UkrainianWordTokenizer wordTokenizer = new UkrainianWordTokenizer()
    def onlyWords = true


    def tokenize(def body, def params) {
        def text = body.text

        List<String> sentences = sentTokenizer.tokenize(text);

//        ParallelEnhancer.enhanceInstance(sentences)

        return sentences.collect { sent ->
            def words = wordTokenizer.tokenize(sent)

            if( onlyWords ) {
//                System.err.println( words.findAll { it.trim() && ! WORD_PATTERN.matcher(it) }.join('\n') )
                words.findAll { WORD_PATTERN.matcher(it) }
            }
            else {
//                words.each { word ->
//                sb.append(word.replace("\n", "\\n").replace("\t", "\\t")).append('|')
            //    }
            }
        }

    }
}
