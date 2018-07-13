package com.projet.ecommerce.entrypoint;

import com.projet.ecommerce.business.IPhotoBusiness;
import com.projet.ecommerce.business.impl.PhotoException;
import com.projet.ecommerce.business.impl.UtilisateurBusiness;
import com.projet.ecommerce.entrypoint.authentification.Token;
import com.projet.ecommerce.entrypoint.graphql.GraphQlUtility;
import com.projet.ecommerce.entrypoint.image.DimensionImage;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * Contrôleur de graphql permettant de répondre sur l'URL "/graphql" et d'exécuter les requêtes curl demandées.
 */

@RestController
public class GraphQLController {

    private GraphQL graphQL;

    @Autowired
    private UtilisateurBusiness utilisateurBusiness;
    @Autowired
    private IPhotoBusiness photoBusiness;

    @Autowired
    private GraphQlUtility graphQlUtility;

    GraphQLController(GraphQlUtility graphQlUtility) {
        graphQL = graphQlUtility.createGraphQlObject();
    }

    @RequestMapping(value = "/graphql/admin", method = RequestMethod.POST)
    public Object handleAdmin(@RequestHeader("Authorization") String authData,
                              @RequestBody Map<String, String> query) {
        Token supposedToken = new Token();
        supposedToken.setToken(authData);
        if (!utilisateurBusiness.isLogged(supposedToken)) {
            return null;
        }
        ExecutionResult result = graphQL.execute(query.get("query"));
        if (result.getErrors().isEmpty()) {
            return result.getData();
        } else {
            List<GraphQLError> graphQLErrors = result.getErrors();
            return graphQlUtility.graphQLErrorHandler(graphQLErrors);
        }
    }

    @RequestMapping(value = "/graphql", method = RequestMethod.POST)
    public Object handleClient(@RequestBody Map<String, String> query) {
        ExecutionResult result = graphQL.execute(query.get("query"));
        if (result.getErrors().isEmpty()) {
            return result.getData();
        } else {
            List<GraphQLError> graphQLErrors = result.getErrors();
            return graphQlUtility.graphQLErrorHandler(graphQLErrors);
        }
    }

    @RequestMapping(value = "/graphql/login", method = RequestMethod.POST)
    public Object handle(@RequestBody Map<String, String> query) {
        // TODO bloquer les requéte qui ne sont pas une mutation utilisateur "signinUtilisateur"
        ExecutionResult result = graphQL.execute(query.get("query"));
        if (result.getErrors().isEmpty()) {
            return result.getData();
        } else {
            List<GraphQLError> graphQLErrors = result.getErrors();
            return graphQlUtility.graphQLErrorHandler(graphQLErrors);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    Boolean handleFileUpload(
            @RequestParam("fichier") MultipartFile file,
            @RequestParam("ref") String refProduit
    ) {
        try {
            photoBusiness.upload(file, refProduit);
        } catch (PhotoException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @GetMapping("/fichier/{refProduit}/{nomFichier:.+}_{height}x{width}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String nomFichier, @PathVariable String refProduit, @PathVariable int height, @PathVariable int width) {
        DimensionImage dimension = new DimensionImage();
        dimension.setSize(width, height);
        Resource fichier = photoBusiness.loadPhotos(nomFichier, refProduit, dimension);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fichier.getFilename() + "\"")// "attachment; filename=" est une norme http
                .body(fichier);
    }

}