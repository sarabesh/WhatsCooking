package com.wolf.silver.whatscooking;

/**
 * Created by sarabesh on 12/23/2017.
 */

public class Recipe {

    private String dish,recipe,image;

    public Recipe(String a,String b,String c)
    {
        this.dish=a;
        this.recipe=b;
        this.image=c;
    }
    public void setDish(String a)
    {
        this.dish=a;
    }

    public void setRecipe(String a)
    {
        this.recipe=a;
    }


    public String getImage(){
        return image;
    }
    public String getDish()
    {
        return dish;
    }

    public String getRecipe()
    {
        return recipe;
    }




}


