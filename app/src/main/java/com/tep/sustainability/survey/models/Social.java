package com.tep.sustainability.survey.models;

import java.util.ArrayList;

public class Social {

    private Info self, spouse;
    private ArrayList<Info> children = new ArrayList<>();

    public void SocioEconomic() {
        this.self = new Info();
        this.spouse = new Info();
        this.children = new ArrayList<Info>();
    }

    public void SocioEconomic(Info self, Info spouse, ArrayList<Info> children) {
        this.self = self;
        this.spouse = spouse;
        int size = children.size();
        this.children.clear();

        for (int i = 0; i < size; i++) {
            this.children.add(children.get(i));
        }
    }

    public Info getSelf() { return this.self; }
    public Info getSpouse() { return this.spouse; }
    public ArrayList<Info> getChildren() { return this.children; }

    public void setSelf(Info self) { this.self = self; }
    public void setSpouse(Info spouse) { this.spouse = spouse; }
    public void setChildren(ArrayList<Info> children) {
        int size = children.size();
        this.children.clear();

        for (int i = 0; i < size; i++) {
            this.children.add(children.get(i));
        }
    }
}
