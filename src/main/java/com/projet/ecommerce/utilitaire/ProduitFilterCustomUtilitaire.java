package com.projet.ecommerce.utilitaire;

public class ProduitFilterCustomUtilitaire {
    private final Float averageLowerBoundFilter;
    private final Float averageUpperBoundFilter;
    private final String fullNameFilter;
    private final String partNameFilter;
    private final String sameCatFilter;
    private final String subCatFilter;

    public ProduitFilterCustomUtilitaire(Float averageLowerBoundFilter, Float averageUpperBoundFilter, String fullNameFilter, String partNameFilter, String sameCatFilter, String subCatFilter) {
        this.averageLowerBoundFilter = averageLowerBoundFilter;
        this.averageUpperBoundFilter = averageUpperBoundFilter;
        this.fullNameFilter = fullNameFilter;
        this.partNameFilter = partNameFilter;
        this.sameCatFilter = sameCatFilter;
        this.subCatFilter = subCatFilter;
    }

    public Float getAverageLowerBoundFilter() {
        return averageLowerBoundFilter;
    }

    public Float getAverageUpperBoundFilter() {
        return averageUpperBoundFilter;
    }

    public String getFullNameFilter() {
        return fullNameFilter;
    }

    public String getPartNameFilter() {
        return partNameFilter;
    }

    public String getSameCatFilter() {
        return sameCatFilter;
    }

    public String getSubCatFilter() {
        return subCatFilter;
    }
}
