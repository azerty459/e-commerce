package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service permettant de gérer les actions effectuées pour les catégories.
 */

@Service
@Transactional
public class CategorieBusiness implements ICategorieBusiness {

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Va chercher toutes les catégories, ou la catégorie donnée en nom. Récupère aussi les sous-catégories si demandées.
     * @param nom le nom de la catégorie à aller chercher. "null" si on cherche à lister toutes les catégories.
     * @param sousCategorie true si on veut lister les sous-catégories sous forme d'arbre, false si on souhaite lister toutes les catégories
     * @return Une liste de CategorieDTO
     */
    @Override
    public List<CategorieDTO> getCategorie(String nom, boolean sousCategorie, boolean parent) {

        // Initialisation
        Categorie parentDirect = null;

        // On va chercher les catégories
        Collection<Categorie> categories = categorieRepository.findAllWithCriteria(nom);
        int tailleListeCategories = categories.size();

        // S'il n'y a pas de résultat: message indiquant aucune catégorie de ce nom.
        if(tailleListeCategories == 0){
            throw new GraphQLCustomException("Aucune catégorie trouvé avec le nom " + nom);
        }

        // On va aussi chercher son parent direct si demandé et s'il n'y qu'un élément dont on doit chercher le parent.
        if(parent && tailleListeCategories == 1) {
            Iterator<Categorie> it = categories.iterator();
            parentDirect = categorieRepository.findDirectParent(it.next());
        }

        // Mise en forme des objets CategorieDTO
        HashMap<Categorie,String> chemins = this.construireAssociationEnfantsChemins(categories);
        return new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categories), chemins, sousCategorie, parent, parentDirect));

    }

    @Override
    public HashMap<Categorie,String> construireAssociationEnfantsChemins(Collection<Categorie> categories) {

        // US#192 - DEBUT

        // Construire un tableau des catégories retournées par findAllWithCriteria
        HashMap<Integer,Categorie> categoriesPourParents = new HashMap<Integer,Categorie>();
        Iterator<Categorie> it = categories.iterator();
        int i = 1;
        while(it.hasNext()) {
            categoriesPourParents.put(i, it.next());
            i++;
        }

        // Request all the parents for these categories
        Collection<Categorie> parents = this.categorieRepository.findParents(categoriesPourParents);

        // Classer cette collection pour mettre chaque parents en face de chaque catégorie de départ
        HashMap<Categorie,String> associationsEnfantsChemins = new HashMap<Categorie,String>();
        associationsEnfantsChemins = associer(categories, parents);

        return associationsEnfantsChemins;
    }

    /**
     * Association des enfants et des parents possibles
     * @param enfants la liste des enfants dont on doit trouver les parents
     * @param parents les parents disponibles
     * @return une HashMap contennant en clés, chaque enfant, et en valeur le chemin vers cet enfant
     */
    private static HashMap<Categorie,String> associer(Collection<Categorie> enfants, Collection<Categorie> parents) {

        // Le Hashmap à retourner
        HashMap<Categorie, String> resultat = new HashMap<Categorie,String>();

        // Pour chaque enfant, aller chercher les parents
        Iterator<Categorie> it = enfants.iterator();
        while(it.hasNext()) {
            Categorie enf = it.next();
            String chemin = chercherChemin(enf, parents, "");
            if(chemin.length() > 1) {
                chemin = chemin.substring(0, chemin.length() - 3);
            }

            resultat.put(enf, chemin);
        }

        return resultat;

    }

    /**
     * Fonction récursive cherchant les parents pour un enfant donné
     * @param enfant l'enfant dont on veut trouver les parents
     * @param parents la collection des parents possibles
     * @return le chemin vers l'enfant
     */
    private static String chercherChemin(Categorie enfant, Collection<Categorie> parents, String chemin) {

        // Condition d'arrêt de l'algorithme
        if(enfant == null ||enfant.getLevel() == 1) {
            return chemin;
        }

        // Borne gauche max du parent potentiel
        int max = 0;

        // Parent potentiel
        Categorie tempParent = null;

        // Construction du chemin
        Iterator<Categorie> it = parents.iterator();

        while(it.hasNext()) {
            Categorie p = it.next();
            if(enfant != null && p.getLevel() == enfant.getLevel() - 1) {
                // On est à un niveau au-dessus dans la hiérarchie des catégories
                // On recherche la borne gauche inférieure la plus proche de celle de l'enfant
                if(p.getBorneGauche() < enfant.getBorneGauche()) {
                    // On recherche la borne gauche maximale dans celles qui restent
                    if(p.getBorneGauche() > max) {
                        max = p.getBorneGauche();
                        tempParent = p;
                    }
                }
            }
        }

        // On a trouvé le parent juste au-dessus dans la hiérarchie et on construit le chemin
        if(tempParent != null) {
            chemin = tempParent.getNomCategorie() + " > " + chemin;
        }

        return chercherChemin(tempParent, parents, chemin);
    }

    // US#192 - FIN



    /**
     * Ajout d'une catégorie parent
     *
     * @param nomCategorie Le nom de la catégorie
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addParent(String nomCategorie) {
        //On récupère toute la liste des catégories
        List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
        int borndeDroit = 0;

        if(!categorieList.isEmpty()){
            //On déclare une borne droit par défaut qui va être utilisée juste après
            borndeDroit = categorieList.get(0).getBorneDroit();

            //On parcours la liste pour trouver la borne droit maximum dans la base de données par rapport au catégorie parent
            for (Categorie retour : categorieList) {
                if (retour.getLevel() == 1 && retour.getBorneDroit() > borndeDroit) {
                    borndeDroit = retour.getBorneDroit();
                }
            }
        }

        // On insére la catégorie et on la return en CategorieDTO
        Categorie categorieInserer = new Categorie();
        categorieInserer.setNomCategorie(nomCategorie);
        categorieInserer.setBorneGauche(borndeDroit + 1);
        categorieInserer.setBorneDroit(borndeDroit + 2);
        categorieInserer.setLevel(1);
        categorieInserer.setProduits(new ArrayList<>());

        return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer));
    }

    /**
     * Ajoute une catégorie enfant
     *
     * @param nomCategorie Le nom de la catégorie
     * @param parent       Le parent de la catégorie à insérer
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addEnfant(String nomCategorie, String parent) {
        // On recherche si le père existe
        Optional<Categorie> categorieParentOptional = categorieRepository.findCategorieByNomCategorie(parent);
        if (categorieParentOptional.isPresent()) {
            Categorie categorieParent = categorieParentOptional.get();
            //Permet de décaler les catégorie de + 2 par rapport à la borne droite du père
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            // On par cours tout le tableau de catégorie
            for (Categorie retour : categorieList) {
                if (retour.getBorneDroit() > categorieParent.getBorneDroit()) {
                    // Si la catégorie est compris dans le parrent, on ajoute que +2 à la borne droite
                    if(retour.getBorneGauche() < categorieParent.getBorneGauche()){
                        retour.setBorneDroit(retour.getBorneDroit() + 2);
                        categorieRepository.save(retour);
                    }else{ // Sinon on ajoute + 2 à la borne gauche et droite
                        retour.setBorneDroit(retour.getBorneDroit() + 2);
                        retour.setBorneGauche(retour.getBorneGauche() + 2);
                        categorieRepository.save(retour);
                    }
                }
            }
            // On créer la catégorie à insérer
            Categorie categorieInserer = new Categorie();
            categorieInserer.setNomCategorie(nomCategorie);
            categorieInserer.setBorneGauche(categorieParent.getBorneDroit());
            categorieInserer.setBorneDroit(categorieParent.getBorneDroit() + 1);
            categorieInserer.setLevel(categorieParent.getLevel() + 1);
            categorieInserer.setProduits(new ArrayList<>());
            // On ajoute + 2 au père sur sa borne droite puis au sauvegarde
            categorieParent.setBorneDroit(categorieParent.getBorneDroit() + 2);
            categorieRepository.save(categorieParent);
            return CategorieTransformer.entityToDto(categorieRepository.save(categorieInserer));
        }else{
            throw new GraphQLCustomException("Aucune catégorie parent trouvé: "+parent);
        }
    }

    /**
     * Supprime la catégorie dans la base de données.
     *
     * @param nomCategorie Nom de la catégorie à supprimer
     * @return true
     */
    @Override
    public boolean delete(String nomCategorie) {
        Optional<Categorie> categorie = categorieRepository.findCategorieByNomCategorie(nomCategorie);
        if(categorie.isPresent()){
            categorieRepository.delete(categorie.get());
            return true;
        }else{
            return false;
        }
    }

    /**
     * Retourne la liste complète des catégories liées à la base de données.
     *
     * @return une liste de catégorie
     */
    // TODO: A SUPPRIMER
//    @Override
//    public List<CategorieDTO> getAll() {
//        return new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categorieRepository.findAll()), false));
//    }

    /**
     * Retourne une categorieDTO parent et ses éventuelle enfants.
     *
     * @param nomCategorie Le nom de la catégorie parent à rechercher.
     * @return une categorieDTO parent et ses éventuelle enfants, null si la catégorie parent n'est pas trouvé
     */
    // TODO: A SUPPRIMER
//    @Override
//    public CategorieDTO getByNom(String nomCategorie) {
//        Optional<Categorie> categorie = categorieRepository.findCategorieByNomCategorie(nomCategorie);
//        if (categorie.isPresent()) {
//            return CategorieTransformer.entityToDto(categorie.get(), new ArrayList<>(categorieRepository.findAll()));
//        }
//        return null;
//    }

    /**
     * Retourne un objet page de catégorie.
     * @param pageNumber le page souhaitée
     * @param nb le nombre de produit à afficher dans la page
     * @return un objet page de produit
     */
    @Override
    public Page<Categorie> getPage(int pageNumber, int nb) {
        return  categorieRepository.findAll(PageRequest.of(pageNumber - 1, nb));
    }
}
