/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.model;

/**
 *
 * @author spatepuram
 */
//@Component
public class SummaryDropDownModel {

    private String id;
    private String name;
    private String maker;
    private boolean ticked;
    
    private String label;
    private String value;
    private boolean checked;

    public SummaryDropDownModel() {
    }

    
    public SummaryDropDownModel(String label, String maker, boolean checked) {
        this.label = label;
        this.maker = maker;
        this.checked = checked;
    }
    
    

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


}
