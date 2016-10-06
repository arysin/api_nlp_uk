package languagetool_nlp


import grails.rest.*
import grails.converters.*

//import io.swagger.annotations.*
import com.wordnik.swagger.annotations.*


@Api(value = "Lemmatization services", 
    description = "Lemmatization services for Ukrainian language",
    produces = 'application/json',
    consumes = 'application/json'
)
class LemmatizeController extends ControllerBase {

    def lemmatizeService


    @ApiOperation(value = "Lemmatizes the text", 
                httpMethod = "POST",
                response = Response.class)
    @ApiResponses([
        @ApiResponse(code = 400, message = "Invalid body provided"),
        @ApiResponse(code = 400, message = "Text limit exceeded")
    ])
    @ApiImplicitParams([
        @ApiImplicitParam(name = 'body', paramType = 'body', required = true, dataType='InputBody', 
            value='Body text; e.g<br>{"text": "Сьогодні у продажі. 12-те зібрання творів 1969 р. І. П. Котляревського."}')
    ])
    def save() {
        if( ! validateRequest(request) )
            return

        try {
            def tokens = lemmatizeService.lemmatize(request.JSON, params)

            def response = new Response(lemmatizedSentences: tokens)

            updateNotes(response)

            render response as JSON
        }
        catch(Exception e) {
            e.printStackTrace()
            render(status: 500, text: "Internal error: " + e.getMessage())
            return
        }

    }


    static class Response {
        List<String> lemmatizedSentences
        String notes
    }
}
