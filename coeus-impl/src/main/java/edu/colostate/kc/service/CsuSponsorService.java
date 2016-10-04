package edu.colostate.kc.service;


public interface CsuSponsorService {
    
    
    /**
     * This method tests whether a certain sponsorCode is in a given sponsor hierarchy.
     * @param sponsorCode - sponsorcode to test
     * @param sponsorHierarchy The name of a sponsor hierarchy
     * @return
     */
    public boolean isSponsorInHierarchy(String sponsorCode, String sponsorHierarchy);

}
