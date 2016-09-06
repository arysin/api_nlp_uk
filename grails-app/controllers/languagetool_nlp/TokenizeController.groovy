package languagetool_nlp


import grails.rest.*
import grails.converters.*

import io.swagger.annotations.*


@Api(value = "Tokenization services", description = "Tokenization services for Ukrainian language")
class TokenizeController {
    static responseFormats = ['json']
    static allowedMethods = [save: "POST"]

    def tokenizeService


    @ApiOperation(value = "Tokenizes the text into sentences and then into words")
    def save() {
        if( ! request.JSON.body?.text ) {
            render(status: 400, text: "body.text not specified: " + request.JSON)
            return
        }

        try {
            def tokens = tokenizeService.tokenize(request.JSON.body, params)
            render tokens: tokens
        }
        catch(Exception e) {
            e.printStackTrace()
            render(status: 500, text: "Internal error: " + e.getMessage())
            return
        }

    }
}
