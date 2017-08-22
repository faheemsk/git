/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.model;

/**
 *
 * @author mrejeti
 */
public class CategoriesModel {
    
     private String id;
    private String name;
    private String maker;
    private boolean ticked;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * @param maker the maker to set
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * @return the ticked
     */
    public boolean isTicked() {
        return ticked;
    }

    /**
     * @param ticked the ticked to set
     */
    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }
    
}
