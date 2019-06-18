package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.ICategorieBusiness;
import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.dto.transformer.CategorieTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.CategorieSupprimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service permettant de gérer les actions effectuées pour les catégories.
 */

@Service
@Transactional
public class CategorieBusiness implements ICategorieBusiness {

	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private CategorieSupprimeBusiness categorieSupprimeBusiness;

	@Autowired
	private CategorieSupprimeRepository categorieSupprimeRepository;

	/**
	 * Association des enfants et des parents possibles
	 *
	 * @param enfants la liste des enfants dont on doit trouver les parents
	 * @param parents les parents disponibles
	 * @return une HashMap contennant en clés, chaque enfant, et en valeur le chemin vers cet enfant
	 */
	private static HashMap<Categorie, Collection<Categorie>> associer(Collection<Categorie> enfants, Collection<Categorie> parents) {

		// Le Hashmap à retourner
		HashMap<Categorie, Collection<Categorie>> resultat = new HashMap<>();

		// Pour chaque enfant, aller chercher les parents
		Iterator<Categorie> it = enfants.iterator();
		while (it.hasNext()) {
			Categorie enf = it.next();
			Collection<Categorie> cheminInitial = new ArrayList<>();
			Collection<Categorie> chemin = chercherChemin(enf, parents, cheminInitial);


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
	private static Collection<Categorie> chercherChemin(Categorie enfant, Collection<Categorie> parents, Collection<Categorie> chemin) {

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
			if (enfant != null && p.getLevel() == enfant.getLevel() - 1) {
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
			chemin.add(tempParent);
		}

		return chercherChemin(tempParent, parents, chemin);
	}

	/**
	 * Classe les catégories en fonction de leur level, les plus petits en premier.
	 *
	 * @param cats les catégories à classer
	 * @return une collection de catégories classées
	 */
	private static Collection<Categorie> sortAccordingToLevelDesc(Collection<Categorie> cats) {

		Collection<Categorie> result = new ArrayList<>();

		while (!cats.isEmpty()) {

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
	public List<CategorieDTO> getTree() {
		List<CategorieDTO> categorieDTOList = getCategorie(0, null, false, false);
		return categorieDTOList.stream().filter(elt -> elt.getLevel() == 1).collect(Collectors.toList());
	}

	/**
	 * Va chercher toutes les catégories, ou la catégorie donnée en nom. Récupère aussi les sous-catégories si demandées.
	 *
	 * @param id            l'id de la catégorie à recherché
	 * @param nom           le nom de la catégorie à aller chercher. "null" si on cherche à lister toutes les catégories.
	 * @param sousCategorie true si on veut lister les sous-catégories sous forme d'arbre, false si on souhaite lister toutes les catégories
	 * @param parent        true si on souhaite avoir le parent des catégories
	 * @return Une liste de CategorieDTO
	 */
	@Override
	public List<CategorieDTO> getCategorie(int id, String nom, boolean sousCategorie, boolean parent) {

		// Initialisation
		Categorie parentDirect = null;
		Collection<Categorie> categorieCollection;

		// On va chercher les catégories
		if (nom != null) {
			categorieCollection = findByNom(nom, sousCategorie);
		} else if (id != 0) {
			categorieCollection = findById(id, sousCategorie);
		} else {
			categorieCollection = categorieRepository.findAllByOrderByNomCategorie();
		}

		// On va aussi chercher son parent direct si demandé
		if (parent) {
			Iterator<Categorie> it = categorieCollection.iterator();
			Boolean notFound = true;
			while (it.hasNext() && notFound) {
				Categorie temp = it.next();
				if (temp.getNomCategorie().equals(nom)) {
					notFound = false;
					parentDirect = categorieRepository.findDirectParent(temp);
				}
			}
		}
		// Mise en forme des objets CategorieDTO
		HashMap<Categorie, Collection<Categorie>> chemins = construireAssociationEnfantsChemins(categorieCollection);

		ArrayList<CategorieDTO> categorieDTOArrayList = new ArrayList<>(CategorieTransformer.entityToDto(new ArrayList<>(categorieCollection), chemins, sousCategorie, parent, parentDirect));

		return categorieDTOArrayList;

	}

	@Override
	public HashMap<Categorie, Collection<Categorie>> construireAssociationEnfantsChemins(Collection<Categorie> categories) {

		// Construire un tableau des catégories retournées par findAllWithCriteria
		HashMap<Integer, Categorie> categoriesPourParents = new HashMap<>();
		Iterator<Categorie> it = categories.iterator();
		int i = 1;
		while (it.hasNext()) {
			Categorie cat = it.next();
			categoriesPourParents.put(i, cat);
			i++;
		}

		// Request all the parents for these categories
		// FIXME changer le paramètre
		Collection<Categorie> parents = categorieRepository.findParents(categoriesPourParents);

		// Classer cette collection pour mettre chaque parents en face de chaque catégorie de départ
		HashMap<Categorie, Collection<Categorie>> associationsEnfantsChemins;
		associationsEnfantsChemins = associer(categories, parents);

		return associationsEnfantsChemins;
	}

	/**
	 * Ajout d'une catégorie parent
	 *
	 * @param nomCategorie Le nom de la catégorie
	 * @return la catégorie crée
	 */
	@Override
	public CategorieDTO addParent(String nomCategorie) {
		if (nomCategorie.isEmpty()) {
			throw new GraphQLCustomException("Le nom de categorie est vide");
		}
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
		if (nomCategorie.isEmpty()) {
			throw new GraphQLCustomException("Le nom de categorie est vide");
		}
		// On recherche si le père existe
		Optional<Categorie> categorieParentOptional = categorieRepository.findById(idParent);
		if (!categorieParentOptional.isPresent()) {
			throw new GraphQLCustomException("Aucune catégorie parent trouvé: " + idParent);
		}
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
		if (!categorie.isPresent()) {
			return false;
		}

		// on supprime les categorie supprimé déja présente, en effet actuellement une seule catégorie et ses sous catégories peuvent être stocké
		categorieSupprimeRepository.deleteAll();

		// Récupération des enfants éventuels de la catégorie
		ArrayList<Categorie> cats = new ArrayList<>(categorieRepository.findByIdCategorieWithSousCat(idCategorie));

		// On cherche la borne gauche minimale et la borne droite maximale et suppression de la BDD
		int bgMin = cats.get(0).getBorneGauche();
		int bdMax = cats.get(0).getBorneDroit();
		for (Categorie c : cats) {
			if (c.getIdCategorie() == idCategorie) {
				bgMin = c.getBorneGauche();
				bdMax = c.getBorneDroit();
			}

			// Suppression de la catégorie
			categorieSupprimeBusiness.saveInCategorieSupprime(c);
			categorieRepository.delete(c);
		}

		// Intervalle supporimé
		int intervalleSupprime = bdMax - bgMin + 1;

		// Réarrangement des index bornes gauches et droites: on décale toutes les bornes à droite
		// de la catégorie supprimée (> à bd) de l'intervalle supprimé.
		categorieRepository.rearrangerBornes(bgMin, intervalleSupprime);

		return true;
	}

	@Override
	public boolean moveCategorie(int idADeplacer, int idNouveauParent) {

		// Test qu'on a bien un déplacement
		if (idADeplacer == idNouveauParent) {
			return false;
		}

		// Aller chercher la catégorie à déplacer et ses enfants
		ArrayList<Categorie> categoriesADeplacer = new ArrayList<>(findById(idADeplacer, true));

		// Trouver les bornes min et max de toutes les catégories à déplacer + leur level le plus haut (le plus petit donc)
		int borneMin = categoriesADeplacer.get(0).getBorneGauche();
		int borneMax = categoriesADeplacer.get(0).getBorneDroit();
		int levelCatADeplacer = categoriesADeplacer.get(0).getLevel();
		for (Categorie c : categoriesADeplacer) {

			if (c.getBorneGauche() < borneMin) {
				borneMin = c.getBorneGauche();
			}
			if (c.getBorneDroit() > borneMax) {
				borneMax = c.getBorneDroit();
			}
			if (c.getLevel() < levelCatADeplacer) {
				levelCatADeplacer = c.getLevel();
			}
		}

		// Si l'id du nouveau parent est 0, on déplace sans affecter à un nouveau parent
		if (idNouveauParent == 0) {
			return deplacerSansParent(categoriesADeplacer, borneMin, borneMax, levelCatADeplacer);
		}

		// Sinon, on déplace vers la catégorie parente
		return deplacerSurParent(categoriesADeplacer, borneMin, borneMax, levelCatADeplacer, idNouveauParent);

	}

	/**
	 * Déplace une catégorie et ses enfants vers une nouvelle catégorie parente
	 *
	 * @param categoriesADeplacer Liste des catégories à déplacer
	 * @param borneMin            Borne la plus petite de ces catégories à déplacer
	 * @param borneMax            Borne la plus grande de ces catégories à déplacer
	 * @param levelCatADeplacer   Level de la catégorie la plus haute des catégories à déplacer
	 * @param idNouveauParent     id du nouveau parent
	 * @return true si le déplacement réussit.
	 */
	private boolean deplacerSurParent(List<Categorie> categoriesADeplacer, int borneMin, int borneMax, int levelCatADeplacer, int idNouveauParent) {

		// Dans le cas où on affecte à un nouveau parent
		// Aller chercher la catégorie parent
		Categorie nouveauParent = (Categorie) findById(idNouveauParent, false).toArray()[0];

		// Sauvegarder le level du nouveau parent
		int levelNouveauParent = nouveauParent.getLevel();

		// Intervalle entre les 2 (c'est à dire l'espace que prend les catégories à déplacer)
		int interBornes = borneMax - borneMin + 1;

		// Nombre de bornes dont on doit décaler les catégories à déplacer
		int intervalleDeDeplacement = nouveauParent.getBorneGauche() + 1 - borneMin;

		// Décaler toutes les bornes supérieures à la borne gauche du nouveau parent de l'intervalle que prennent les
		// catégories à déplacer

		categorieRepository.ecarterBornes(nouveauParent.getBorneGauche(), interBornes);

		// Mettre à jour l'intervalle de déplacement si on déplace de droite à gauche
		if (intervalleDeDeplacement < 0) {
			intervalleDeDeplacement -= interBornes;
		}

		// Déplacer les catégories de intervalleDeDeplacement et réarranger leur levels
		deplacer(categoriesADeplacer, intervalleDeDeplacement, levelCatADeplacer, levelNouveauParent, false);

		// Les catégories déplacées ont laissé un vide dans les bornes à leur emplacement d'origine: combler le vide
		if (intervalleDeDeplacement >= 0) {
			categorieRepository.rearrangerBornes(borneMin, interBornes);
		} else {
			categorieRepository.rearrangerBornes(borneMax, interBornes);
		}
		return true;
	}


	/**
	 * Déplace une catégorie (et ses enfants) vers le level 1 (c'est à dire sans parent)
	 *
	 * @param categoriesADeplacer Liste des catégories à déplacer
	 * @param borneMin            la borne minimale de toutes les catégories à déplacer
	 * @param borneMax            la borne maximale de toutes les catégories à déplacer
	 * @param levelCatADeplacer   le level de la catégorie à déplacer (celle tout en haut de l'arborescence à déplacer)
	 * @return true
	 */
	private boolean deplacerSansParent(List<Categorie> categoriesADeplacer, int borneMin, int borneMax, int levelCatADeplacer) {

		int borneMaxDeBddEntiere = categorieRepository.findBorneMax();

		// Calculer le déplacement
		int intervalleDeDeplacement = borneMaxDeBddEntiere - borneMin + 1;
		// Déplacer toutes les bornes des catégories à déplacer de cet intervalle + Ajuster le level
		deplacer(categoriesADeplacer, intervalleDeDeplacement, levelCatADeplacer, 0, false);

		// Les catégories déplacées ont laissé un vide dans les bornes à leur emplacement d'origine: les combler
		categorieRepository.rearrangerBornes(borneMin, borneMax - borneMin + 1);

		return true;
	}


	/**
	 * Déplace les bornes des catégories à déplacer et réarrange leurs levels
	 *
	 * @param categoriesADeplacer     liste des catégories à déplacer
	 * @param intervalleDeDeplacement intervalle de déplacement
	 * @param levelCatADeplacer       niveau de la catégorie la plus haute à déplacer
	 * @param levelNouveauParent      niveau de son nouveau parent
	 * @param isDeleted               vrai si la catégorie est une catégorie supprimé faux sinon
	 */
	private boolean deplacer(List<Categorie> categoriesADeplacer, int intervalleDeDeplacement, int levelCatADeplacer, int levelNouveauParent, boolean isDeleted) {
		// Liste des ids des catégories à déplacer
		List<Integer> ids = new ArrayList<Integer>();

		for (Categorie cat : categoriesADeplacer) {
			ids.add(cat.getIdCategorie());
		}

		// Calcul de l'intervalle de déplacement du level
		int intervalLevel = levelNouveauParent + 1 - levelCatADeplacer;

		// Changer le level de chaque catégorie et leurs bornes
		if (isDeleted) {
			categorieSupprimeRepository.changerBornesEtLevel(ids, intervalleDeDeplacement, intervalLevel);
		} else {
			categorieRepository.changerBornesEtLevel(ids, intervalleDeDeplacement, intervalLevel);
		}


		return true;
	}

	@Override
	public CategorieDTO updateCategorie(int id, String nomCategorie) {
		if (nomCategorie.isEmpty()) {
			throw new GraphQLCustomException("Le nom de categorie est vide");
		}

		Optional<Categorie> optionalCategorie = categorieRepository.findById(id);

		// Trouver la catégorie à modifier et la transformer en DTO

		return optionalCategorie
				.map((categorie) -> {
					Categorie cat = optionalCategorie.get();
					cat.setNomCategorie(nomCategorie);
					return CategorieTransformer.entityToDto(categorieRepository.save(cat));
				})
				.orElseThrow(() -> new GraphQLCustomException("La catégorie n'a pas été trouvée"));
	}

	/**
	 * Retourne un tableau de catégorie en fonction du nom.
	 *
	 * @param nom           Nom de la catégorie à rechercher
	 * @param sousCategorie true si on veut lister les sous-catégories sous forme d'arbre sinon false
	 * @return un tableau de catégorie.
	 */
	private Collection<Categorie> findByNom(String nom, boolean sousCategorie) {
		Collection<Categorie> categorieCollection;
		if (sousCategorie) {
			categorieCollection = categorieRepository.findByNomCategorieWithSousCat(nom);
		} else {
			categorieCollection = categorieRepository.findByNomCategorie(nom);
		}
		if (categorieCollection.isEmpty()) {
			throw new GraphQLCustomException("Aucune catégorie avec ce nom.");
		}
		return categorieCollection;
	}

	/**
	 * Retourne un tableau de catégorie en fonction de l'id recherchée et du paramètre sousCat.
	 *
	 * @param id            ID de la catégorie à rechercher
	 * @param sousCategorie true si on veut lister les sous-catégories sous forme d'arbre sinon false
	 * @return un tableau de catégorie.
	 */
	private Collection<Categorie> findById(int id, boolean sousCategorie) {
		Collection<Categorie> categorieCollection = new ArrayList<>();
		if (sousCategorie) {
			categorieCollection = categorieRepository.findByIdCategorieWithSousCat(id);
		} else {
			Optional<Categorie> categorieOptional = categorieRepository.findById(id);
			if (categorieOptional.isPresent()) {
				categorieCollection.add(categorieOptional.get());
			}
		}
		if (categorieCollection.isEmpty()) {
			throw new GraphQLCustomException("Aucune catégorie avec cet ID.");
		}
		return categorieCollection;
	}

	/**
	 * Retourne une page de catégorie.
	 *
	 * @param pageNumber le page souhaitée
	 * @param nb         le nombre de catégorie à afficher dans la page
	 * @return une page paginée
	 */
	@Override
	public Page<Categorie> getPage(int pageNumber, int nb) {
		PageRequest page = (pageNumber == 0) ? PageRequest.of(pageNumber, nb) : PageRequest.of(pageNumber - 1, nb);
		return categorieRepository.findAll(page);
	}

	@Override
	public Long countCategories() {
		return categorieRepository.countCategories();
	}

}
