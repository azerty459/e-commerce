package com.projet.ecommerce.entrypoint;

import com.projet.ecommerce.business.IPhotoBusiness;
import com.projet.ecommerce.entrypoint.graphQL.GraphQlUtility;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.util.List;
import java.util.Map;


/**
 * Contrôleur de graphQL permettant de répondre sur l'URL "/graphql" et d'exécuter les requêtes curl demandées.
 */

@RestController
public class GraphQLController {

    private GraphQL graphQL;

    @Autowired
    private IPhotoBusiness photoBusiness;

    @Autowired
    private GraphQlUtility graphQlUtility;

    GraphQLController(GraphQlUtility graphQlUtility) {
        graphQL = graphQlUtility.createGraphQlObject();
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    public Object handle(@RequestBody Map<String,String> query) {
        ExecutionResult result = graphQL.execute(query.get("query"));
        if(result.getErrors().isEmpty()){
            return result.getData();
        }else{
            List<GraphQLError> graphQLErrors = result.getErrors();
            return graphQlUtility.graphQLErrorHandler(graphQLErrors);
        }
    }
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody Boolean handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("ref") String refProduit
    ){
        return photoBusiness.upload(file,refProduit);
    }

    @GetMapping("/fichier/{refProduit}/{nomFichier:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String nomFichier,@PathVariable String refProduit) {
        Resource fichier = photoBusiness.loadPhotos(nomFichier,refProduit);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fichier.getFilename() + "\"")// "attachment; filename=" est une norme http
                .body(fichier);
    }

}