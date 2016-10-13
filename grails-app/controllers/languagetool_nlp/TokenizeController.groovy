package languagetool_nlp


import grails.converters.*
import grails.rest.*

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.*
//import com.wordnik.swagger.annotations.*

@Api(value = "Tokenization services", 
    description = "Tokenization services for Ukrainian language",
    produces = 'application/json',
    consumes = 'application/json',
	tags=["tokenize"] 
)
@Controller(value="/tokenize")
class TokenizeController extends ControllerBase {

    def tokenizeService


    @ApiOperation(value = "Tokenizes the text into sentences and then into words", 
            httpMethod = "POST",
            response = TokenizeResponse.class,
			extensions = [
				@Extension(properties = [
					@ExtensionProperty(name = "x-taskClass", value = "tokenization"), 
					@ExtensionProperty(name = "x-taskAlgo", value = "nlp_uk")//,
//					@ExtensionProperty(name = "x-taskModel", value = "dict_uk")
				])
			]
	)
    @ApiResponses([
        @ApiResponse(code = 400, message = "Invalid body provided"),
        @ApiResponse(code = 400, message = "Text limit exceeded")
    ])
    @ApiImplicitParams([
        @ApiImplicitParam(name = 'body', paramType = 'body', required = true, dataType='InputBody', 
            value='Body text; e.g<br>{"text": "Сьогодні у продажі. 12-те зібрання творів 1969 р. І. П. Котляревського."}')
    ])
	@RequestMapping(value="/")
    def save() {

        if( ! validateRequest(request) )
            return

        try {
            def tokens = tokenizeService.tokenize(request.JSON, params)

            def response = new TokenizeResponse(tokens: tokens)

            updateNotes(response)

            render response as JSON
        }
        catch(Exception e) {
            e.printStackTrace()
            render(status: 500, text: "Internal error: " + e.getMessage())
            return
        }

    }

    static class TokenizeResponse {
        List<List<String>> tokens
        String notes
    }
}
