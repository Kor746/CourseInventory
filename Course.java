/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.util.Objects;

/**
 *
 * @author Daniel
 */
public class Course implements Comparable<Course> {

    private String id;
    private String title;
    private int credit;
    private String category;
    
    public Course() 
    {
        set("", "", 0, "");
        
    }
    public Course(String id)
    {
        this.id = id;
        
    }
    
    
    public Course(String id, String title, int credit, String category)
    {
        set(id,title,credit,category);
        
    }
    
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getCredit() { return credit; }
    public String getCategory() { return category; }
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setCredit(int credit) { this.credit = credit; }
    public void setCategory(String category){ this.category = category; }
    public void set(String id, String title,int credit,String category)
    {
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Course)
        {
            return this.id.equals(((Course)obj).getId());
        }
        return this == obj;
    }
    
    @Override
    public String toString()
    {
        return "Course(" + this.id + ", " + this.title + ")";
    }
    
    @Override
    public int compareTo(Course c)
    {
        return this.id.compareTo(c.getId());
    }
    
    
    
    
    
}

