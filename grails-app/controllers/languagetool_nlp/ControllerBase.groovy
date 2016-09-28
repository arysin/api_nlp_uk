package languagetool_nlp


import grails.rest.*
import grails.converters.*

//import io.swagger.annotations.*
import com.wordnik.swagger.annotations.*


abstract class ControllerBase {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST"]
    static defaultAction = "save"
    static TEXT_LIMIT = 50*1024


    def validateRequest(request) {
        if( ! request.JSON?.text ) {
            render(status: 400, text: "\"text\" field not specified in the request")
            return false
        }

        if( request.JSON.text.size() > TEXT_LIMIT ) {
            render(status: 400, text: String.format("text length cannot exceed %d characters", TEXT_LIMIT))
            return false
        }

        return true
    }

    def updateNotes(response) {
        if( testLatCyrMix(request.JSON.text) ) {
            response.notes = "Text contains mix of Cyrillic and Lating which may produce suboptimal results"
        }
    }

    def testLatCyrMix(text) {
        return text =~ /[а-яіїєґА-ЯІЇЄҐ]['’ʼ-]?[a-zA-Z]|[a-zA-Z]['’ʼ-]?[а-яіїєґА-ЯІЇЄҐ]/
    }


}
