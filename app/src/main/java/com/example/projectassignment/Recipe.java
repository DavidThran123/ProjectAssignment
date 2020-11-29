package com.example.projectassignment;

public class Recipe
{
    //Title
    private String title;
    //Website
    private String website;
    //Ingredients
    private String ingredients;

    private long id;

    Recipe(String title, String website, String ingredients)
    {
        setTitle(title);
        setWebsite(website);
        setIngredients(ingredients);
    }

    Recipe(String title, String website, String ingredients, long id)
    {
        setTitle(title);
        setWebsite(website);
        setIngredients(ingredients);
        setId(id);
    }

    void setTitle(String title)
    {
        this.title = title;
    }

    void setWebsite(String website)
    {
        this.website = website;
    }

    void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getTitle()
    {
        return title;
    }

    public String getWebsite()
    {
        return website;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return this.id;
    }

}