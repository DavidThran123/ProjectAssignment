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

    /**
     * The Recipe constructor. Creates a recipe.
     *
     * @param title The title/name of the recipe
     * @param website The website to which the recipe can be found
     * @param ingredients The ingredients required for this recipe
     */
    Recipe(String title, String website, String ingredients)
    {
        setTitle(title);
        setWebsite(website);
        setIngredients(ingredients);
    }

    /**
     * The Recipe constructor. Creates a recipe.
     *
     * @param title The title/name of the recipe
     * @param website The website to which the recipe can be found
     * @param ingredients The ingredients required for this recipe
     * @param id The database id for this recipe
     */
    Recipe(String title, String website, String ingredients, long id)
    {
        setTitle(title);
        setWebsite(website);
        setIngredients(ingredients);
        setId(id);
    }


    /**
     * Sets the title of this recipe
     *
     * @param title The title/name of the recipe
     */
    void setTitle(String title)
    {
        this.title = title;
    }
    /**
     * Sets the website of this recipe
     *
     * @param website The website of the recipe
     */
    void setWebsite(String website)
    {
        this.website = website;
    }
    /**
     * Sets the ingredients of this recipe
     *
     * @param ingredients The website of the recipe
     */
    void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }

    /**
     * Retrieves the title of this recipe
     */
    public String getTitle()
    {
        return title;
    }
    /**
     * Retrieves the website of this recipe
     */
    public String getWebsite()
    {
        return website;
    }
    /**
     * Retrieves the ingredients of this recipe
     */
    public String getIngredients()
    {
        return ingredients;
    }

    /**
     * Sets the database id of this recipe
     *
     * @param id The db id of the recipe
     */
    public void setId(long id)
    {
        this.id = id;
    }
    /**
     * Retrieves the database id of this recipe
     */
    public long getId()
    {
        return this.id;
    }

}