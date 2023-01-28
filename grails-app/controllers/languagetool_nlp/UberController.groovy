package languagetool_nlp

import grails.converters.*
import grails.rest.*

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.*

@Api(value = "Ubertext services", 
    description = "Ubertext services for Ukrainian language",
    produces = 'application/json',
    consumes = 'application/json',
    tags=["uber"] 
)
@Controller(value="/uber")
class UberController extends ControllerBase {
    def uberService

    @ApiOperation(
        value = "Ubers the text into sentences, lemmas and tokens", 
        httpMethod = "POST"
    )
    @ApiResponses([
        @ApiResponse(code = 400, message = "Invalid body provided"),
        @ApiResponse(code = 400, message = "Text limit exceeded")
    ])
    @ApiImplicitParams([
        @ApiImplicitParam(name = 'body', paramType = 'body', required = true, dataType='InputBody', 
            value='Body text; e.g<br>["Сьогодні у продажі. 12-те зібрання творів 1969 р. І. П. Котляревського."]')
    ])

    @RequestMapping(value="/uber")
    def save() {

        try {
            def result = uberService.uber(request.JSON, params)

            render result as JSON
        }
        catch(Exception e) {
            e.printStackTrace()
            render(status: 500, text: "Internal error: " + e.getMessage())
            return
        }

    }
}
