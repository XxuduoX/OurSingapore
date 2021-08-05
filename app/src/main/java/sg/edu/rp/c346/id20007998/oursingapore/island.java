package sg.edu.rp.c346.id20007998.oursingapore;

import java.io.Serializable;

public class island implements Serializable {
    private int id;
    private String name;
    private String description;
    private int square;
    private float stars;

    public island(String name,String description,int square,float stars){
        this.name=name;
        this.description=description;
        this.square=square;
        this.stars=stars;
    }

    public island(int id,String name,String description,int square,float stars){
        this.id=id;
        this.name=name;
        this.description=description;
        this.square=square;
        this.stars=stars;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
