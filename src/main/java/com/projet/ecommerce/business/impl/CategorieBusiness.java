package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.impl.CategorieRepositoryCustomImpl;
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

    @Autowired
    private CategorieRepositoryCustomImpl categorieRepositoryCustom;

    /**
     * Va chercher toutes les catégories, ou la catégorie donnée en nom. Récupère aussi les sous-catégories si demandées.
     *
     * @param nom           le nom de la catégorie à aller chercher. "null" si on cherche à lister toutes les catégories.
     * @param sousCategorie true si on veut lister les sous-catégories sous forme d'arbre, false si on souhaite lister toutes les catégories
     * @return Une liste de CategorieDTO
     */
    @Override

    public List<CategorieDTO> getCategorie(int id, String nom, boolean sousCategorie, boolean parent) {

        // Initialisation
        Categorie parentDirect = null;

        // On va chercher les catégories
        Collection<Categorie> categorieCollection = categorieRepository.findAllWithCriteria(id, nom, sousCategorie);

        // S'il n'y a pas de résultat: message indiquant aucune catégorie de ce nom.
        if(categorieCollection.size() == 0){
            throw new GraphQLCustomException("Aucune catégorie(s) trouvé(es).");
        }

        // On va aussi chercher son parent direct si demandé
        if(parent) {
            Iterator<Categorie> it = categorieCollection.iterator();
            Boolean notFound = true;
            while(it.hasNext() && notFound) {
                Categorie temp = it.next();
                if(temp.getNomCategorie().equals(nom)) {
                    notFound = false;
                    parentDirect = categorieRepository.findDirectParent(temp);
                }
            }
        }
        // Mise en forme des objets CategorieDTO
        HashMap<Categorie,String> chemins = this.construireAssociationEnfantsChemins(categorieCollection);
        return new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categorieCollection), chemins, sousCategorie, parent, parentDirect));

    }

    @Override
    public HashMap<Categorie, String> construireAssociationEnfantsChemins(Collection<Categorie> categories) {
        // Construire un tableau des catégories retournées par findAllWithCriteria
        HashMap<Integer, Categorie> categoriesPourParents = new HashMap<Integer, Categorie>();
        Iterator<Categorie> it = categories.iterator();
        int i = 1;
        while (it.hasNext()) {
            categoriesPourParents.put(i, it.next());
            i++;
        }

        // Request all the parents for these categories
        Collection<Categorie> parents = this.categorieRepository.findParents(categoriesPourParents);

        // Classer cette collection pour mettre chaque parents en face de chaque catégorie de départ
        HashMap<Categorie, String> associationsEnfantsChemins = new HashMap<Categorie, String>();
        associationsEnfantsChemins = associer(categories, parents);

        return associationsEnfantsChemins;
    }

    /**
     * Association des enfants et des parents possibles
     *
     * @param enfants la liste des enfants dont on doit trouver les parents
     * @param parents les parents disponibles
     * @return une HashMap contennant en clés, chaque enfant, et en valeur le chemin vers cet enfant
     */
    private static HashMap<Categorie, String> associer(Collection<Categorie> enfants, Collection<Categorie> parents) {

        // Le Hashmap à retourner
        HashMap<Categorie, String> resultat = new HashMap<Categorie, String>();

        // Pour chaque enfant, aller chercher les parents
        Iterator<Categorie> it = enfants.iterator();
        while (it.hasNext()) {
            Categorie enf = it.next();
            String chemin = chercherChemin(enf, parents, "");
            if (chemin.length() > 1) {
                chemin = chemin.substring(0, chemin.length() - 3);
            }

            resultat.put(enf, chemin);
        }

        return resultat;

    }

    /**
     * Fonction récursive cherchant les parents pour un enfant donné
     *
     * @param enfant  l'enfant dont on veut trouver les parents
     * @param parents la collection des parents possibles
     * @return le chemin vers l'enfant
     */
    private static String chercherChemin(Categorie enfant, Collection<Categorie> parents, String chemin) {

        // Condition d'arrêt de l'algorithme
        if (enfant == null || enfant.getLevel() == 1) {
            return chemin;
        }

        // Borne gauche max du parent potentiel
        int max = 0;

        // Parent potentiel
        Categorie tempParent = null;

        // Construction du chemin
        Iterator<Categorie> it = parents.iterator();

        while (it.hasNext()) {
            Categorie p = it.next();
            if(enfant != null && p.getLevel() == enfant.getLevel() - 1) {
                // On est à un niveau au-dessus dans la hiérarchie des catégories
                // On recherche la borne gauche inférieure la plus proche de celle de l'enfant
                if (p.getBorneGauche() < enfant.getBorneGauche()) {
                    // On recherche la borne gauche maximale dans celles qui restent
                    if (p.getBorneGauche() > max) {
                        max = p.getBorneGauche();
                        tempParent = p;
                    }
                }
            }
        }

        // On a trouvé le parent juste au-dessus dans la hiérarchie et on construit le chemin
        if (tempParent != null) {
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

        if (!categorieList.isEmpty()) {
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
     * @param idParent     L'ID parent de la catégorie
     * @return la catégorie crée
     */
    @Override
    public CategorieDTO addEnfant(String nomCategorie, int idParent) {
        // On recherche si le père existe
        Optional<Categorie> categorieParentOptional = categorieRepository.findById(idParent);
        if (categorieParentOptional.isPresent()) {
            Categorie categorieParent = categorieParentOptional.get();
            //Permet de décaler les catégorie de + 2 par rapport à la borne droite du père
            List<Categorie> categorieList = new ArrayList<>(categorieRepository.findAll());
            // On par cours tout le tableau de catégorie
            for (Categorie retour : categorieList) {
                if (retour.getBorneDroit() > categorieParent.getBorneDroit()) {
                    // Si la catégorie est compris dans le parrent, on ajoute que +2 à la borne droite
                    if (retour.getBorneGauche() < categorieParent.getBorneGauche()) {
                        retour.setBorneDroit(retour.getBorneDroit() + 2);
                        categorieRepository.save(retour);
                    } else { // Sinon on ajoute + 2 à la borne gauche et droite
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
        } else {
            throw new GraphQLCustomException("Aucune catégorie parent trouvé: " + idParent);
        }
    }

    /**
     * Supprime la catégorie donnée en paramètre et tous ses enfants de la base de données.
     *
     * @param idCategorie ID de la catégorie à supprimer
     * @return true si la catégorie a été trouvée, false sinon
     */
    @Override
    public boolean delete(int idCategorie) {

        Optional<Categorie> categorie = categorieRepository.findById(idCategorie);

        // Si on ne trouve pas de catégorie correspondant à l'id
        if(!categorie.isPresent()) {
            return false;
        }

        // Récupération des enfants éventuels de la catégorie
        ArrayList<Categorie> cats = new ArrayList<Categorie>(categorieRepository.findAllWithCriteria(idCategorie, null, true));

        // On cherche la borne gauche minimale et la borne droite maximale et suppression de la BDD
        int bgMin = cats.get(0).getBorneGauche();
        int bdMax = cats.get(0).getBorneDroit();

        for(Categorie c: cats) {
            if(c.getBorneGauche() < bgMin) {
                bgMin = c.getBorneGauche();
            }
            if(c.getBorneDroit() > bdMax) {
                bdMax = c.getBorneDroit();
            }

            // Suppression de la catégorie
            categorieRepository.delete(c);

        }

        // Intervalle supporimé
        int intervalleSupprime = bdMax - bgMin + 1;


        // Réarrangement des index bornes gauches et droites: on décale toutes les bornes à droite
        // de la catégorie supprimée (> à bd) de l'intervalle supprimé.
        categorieRepositoryCustom.rearrangerBornes(bgMin, bdMax, intervalleSupprime);



        return true;

    }

    /**
     * Classe les catégories eb fonction de leur level, les plus petits en premier.
     * @param cats les catégories à classer
     * @return une collection de catégories classées
     */
    private static Collection<Categorie> sortAccordingToLevelDesc(Collection<Categorie> cats) {

        Collection<Categorie> result = new ArrayList<Categorie>();

        while(!cats.isEmpty()) {

            Iterator<Categorie> it = cats.iterator();

            int levelMax = 0;
            Categorie catWithMaxLevel = null;

            while (it.hasNext()) {
                Categorie c = it.next();
                if (c.getLevel() > levelMax) {
                    levelMax = c.getLevel();
                    catWithMaxLevel = c;
                }
            }

            result.add(catWithMaxLevel);
            cats.remove(catWithMaxLevel);
        }

        return result;
    }


    @Override
    public boolean moveCategorie(int idADeplacer, int idNouveauParent) {

        // Test qu'on a bien un déplacement
        if(idADeplacer == idNouveauParent) {
            return false;
        }

        // Aller chercher la catégorie à déplacer et ses enfants
        ArrayList<Categorie> categoriesADeplacer = new ArrayList<Categorie>(this.categorieRepositoryCustom.findAllWithCriteria(idADeplacer, null, true));

        // Trouver les bornes min et max de toutes les catégories à déplacer + leur level le plus haut (le plus petit donc)
        int borneMin = categoriesADeplacer.get(0).getBorneGauche();
        int borneMax = categoriesADeplacer.get(0).getBorneDroit();
        int levelCatADeplacer = categoriesADeplacer.get(0).getLevel();
        for(Categorie c: categoriesADeplacer) {

            if(c.getBorneGauche() < borneMin) {
                borneMin = c.getBorneGauche();
            }
            if(c.getBorneDroit() > borneMax) {
                borneMax = c.getBorneDroit();
            }
            if(c.getLevel() < levelCatADeplacer) {
                levelCatADeplacer = c.getLevel();
            }
        }

        // Si l'id du nouveau parent est 0, on déplace sans affecter à un nouveau parent
        if(idNouveauParent == 0) {
            return this.deplacerSansParent(categoriesADeplacer, borneMin, borneMax, levelCatADeplacer);
        }

        // Dans le cas où on affecte à un nouveau parent
        // Aller chercher la catégorie parent
        Categorie nouveauParent = null;
        nouveauParent = (Categorie) this.categorieRepositoryCustom.findAllWithCriteria(idNouveauParent, null, false).toArray()[0];

        // Sauvegarder le level du nouveau parent
        int levelNouveauParent = nouveauParent.getLevel();

        // Intervalle entre les 2 (c'est à dire l'espace que prend les catégories à déplacer)
        int interBornes = borneMax - borneMin + 1;

        // Nombre de bornes dont on doit décaler les catégories à déplacer
        int intervalleDeDeplacement = nouveauParent.getBorneGauche() + 1 - borneMin;

        // Décaler toutes les bornes supérieures à la borne gauche du nouveau parent de l'intervalle que prennent les
        // catégories à déplacer
        categorieRepository.ecarterBornes(nouveauParent, interBornes);

        // Déplacer les catégories de intervalleDeDeplacement et réarranger leur levels
        for(Categorie cat: categoriesADeplacer) {
            int bg = cat.getBorneGauche();
            int bd = cat.getBorneDroit();
            int level = cat.getLevel();
            cat.setBorneDroit(bd + intervalleDeDeplacement);
            cat.setBorneGauche(bg + intervalleDeDeplacement);
            System.out.println("Level du nouveau parent:");
            System.out.println(levelNouveauParent);
            System.out.println("Level de la catégorie examinée:");
            System.out.println(level);
            System.out.println("Level de la catégorie la plus élevée des catégories à déplacer");
            System.out.println(levelCatADeplacer);
            System.out.println("set Level à: ");
            System.out.println(levelNouveauParent + 1 + level - levelCatADeplacer);
            System.out.println();
            cat.setLevel(levelNouveauParent + 1 + level - levelCatADeplacer);

            // categorieRepository.save(cat);
        }

        // Les catégories déplacées ont laissé un vide dans les bornes à leur emplacement d'origine: combler le vide
        if(intervalleDeDeplacement >= 0) {
            this.categorieRepositoryCustom.rearrangerBornes(borneMin, borneMax, interBornes);
        }
        else {
            this.categorieRepositoryCustom.rearrangerBornes(borneMax, borneMax + interBornes, interBornes);
        }

        return true;

    }

    /**
     * Déplace une catégorie (et ses enfants) vers le level 1 (c'est à dire sans parent)
     * @param categoriesADeplacer Liste des catégories à déplacer
     * @param borneMin la borne minimale de toutes les catégories à déplacer
     * @param borneMax la borne maximale de toutes les catégories à déplacer
     * @param levelCatADeplacer le level de la catégorie à déplacer (celle tout en haut de l'arborescence à déplacer)
     * @return true
     */
    private boolean deplacerSansParent(List<Categorie> categoriesADeplacer, int borneMin, int borneMax, int levelCatADeplacer) {

        // Aller chercler la borne maximale de toutes les catégories de la base de donbées
        int borneMaxDeBddEntiere = this.categorieRepositoryCustom.findBorneMax();

        // Calculer le déplacement
        int intervalleDeDeplacement = borneMaxDeBddEntiere - borneMin + 1;

        // Déplacer toutes les bornes des catégories à déplacer de cet intervalle + Ajuster le level
        for(Categorie cat: categoriesADeplacer) {
            int bg = cat.getBorneGauche();
            int bd = cat.getBorneDroit();
            int level = cat.getLevel();
            cat.setBorneDroit(bd + intervalleDeDeplacement);
            cat.setBorneGauche(bg + intervalleDeDeplacement);
            cat.setLevel(1 - levelCatADeplacer + level);
        }

        // Les catégories déplacées ont laissé un vide dans les bornes à leur emplacement d'origine: les combler
        this.categorieRepositoryCustom.rearrangerBornes(borneMin, borneMax, borneMax - borneMin + 1);

        return true;
    }

    @Override
    public CategorieDTO updateCategorie(int id, String newName) {

        Categorie cat = null;
        CategorieDTO catDto = null;

        Optional<Categorie> optionalCategorie = this.categorieRepository.findById(id);

        // Trouver la catégorie à modifier et la transformer en DTO
        if(optionalCategorie.isPresent()) {
            cat = optionalCategorie.get();
            cat.setNomCategorie(newName);
            catDto = CategorieTransformer.entityToDto(categorieRepository.save(cat));
        } else {
            throw new GraphQLCustomException("La catégorie n'a pas été trouvée");
        }

        return catDto;

    }


    /**
     * Retourne un objet page de catégorie.
     *
     * @param pageNumber le page souhaitée
     * @param nb         le nombre de produit à afficher dans la page
     * @return un objet page de produit
     */
    @Override
    public Page<Categorie> getPage(int pageNumber, int nb) {
        PageRequest page = (pageNumber == 0)? PageRequest.of(pageNumber, nb): PageRequest.of(pageNumber-1, nb);
        return categorieRepository.findAll(page);
    }
}
