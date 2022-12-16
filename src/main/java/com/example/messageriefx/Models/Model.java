package com.example.messageriefx.Models;

import com.example.messageriefx.Views.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private Model(){
        this.viewFactory = new ViewFactory();
    }
    public static synchronized Model getInstance(){
        if(model == null) model = new Model();
        return model;
    }
    public ViewFactory getViewFactory(){
        return this.viewFactory;
    }
}
