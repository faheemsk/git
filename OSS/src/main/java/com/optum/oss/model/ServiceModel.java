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
//@Component
public class ServiceModel {

    private int id;
    private String name;
    private String maker;
    private boolean ticked;
    
    public ServiceModel(){
        
    }

    public ServiceModel(int id, String name, String maker, boolean ticked) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.ticked = ticked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public boolean isTicked() {
        return ticked;
    }

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

}
