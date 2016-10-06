package languagetool_nlp


import grails.rest.*
import grails.converters.*

//import io.swagger.annotations.*
import com.wordnik.swagger.annotations.*


@Api(value = "Tagging services",
    description = "Tagging services for Ukrainian language",
    produces = 'application/json',
    consumes = 'application/json'
)
class TagController extends ControllerBase {

    def taggingService


    @ApiOperation(value = "Tags the text", 
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
            def tokens = taggingService.tag(request.JSON, params)

            def response = new Response(taggedText: tokens)

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
        List<String> taggedText
        String notes
    }
}
