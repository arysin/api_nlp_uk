package languagetool_nlp

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"(parseRequest: true){
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'application', action:'index')

        "/tokenize"(controller: 'tokenize', action:'save')
        "/lemmatize"(controller: 'lemmatize', action:'save')
        "/tag"(controller: 'tag', action:'save')
        "/uber"(controller: 'uber', action:'save')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
