/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Daniel
 */
public class InventoryModel {
    //instance vars
    private final ArrayList<Course> courses;
    private final ArrayList<String> categories;
    
    private Course co;
    private String id;
    private File file;
    private String newid;
    
    // no-arg ctor
    public InventoryModel()
    {
        
        co = new Course();
        id = co.getId();
        newid = null;
        courses= new ArrayList<>();
        categories = new ArrayList<>();
        
        Collections.addAll(categories,"DATABASE","INFORMATION","MATH",
                          "PROGRAMMING","SYSTEM");
    }
    //getters
    public ArrayList<Course> getCourses() 
    {
        return courses;
    }
    public ArrayList<String> getCategories()
    {
        return categories;
    }
    public ArrayList<String> getCourseIds()
    {
        
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i < courses.size(); ++i)
        {
            ids.add(courses.get(i).getId());
        }
        return ids;  
    }
   
    public Course getCourse(String id)
    {
        co = new Course(id);
        int index = getCourseIndex(co);
        
        if(index >= 0) { return courses.get(index); }
        return null; 
          
    }
    
    public int getCourseIndex(Course id)
    {
       return Collections.binarySearch(courses, id);
       //(o1,o2) -> o1.compareTo(o2)  
    }
    
    public int getCourseCount()
    {
        return courses.size();
    }
    public File getFile()
    {
        return this.file;
    }
    public void setFile(File file)
    {
      this.file = file;   
    }
    
    
    
    public void readCourseFile(File file)
    {   
        ArrayList<String> lines = new ArrayList<>();
        String record = null;
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            while((record = br.readLine()) != null)
            {
              lines.add(record);
            }
            br.close();
            
        }
        catch(Exception e)
        {
            System.err.println("]ERROR] " + e.getMessage());
            return;
        }
        //sort the records
        Collections.sort(lines);
        courses.clear(); // remove the previous data
        //parsing line by line
        
        for(int i = 0; i < lines.size(); ++i)
        {
            String[] tokens = lines.get(i).split(";");
            
            if(tokens.length < 4) continue;
            for(int j = 0; j < tokens.length; ++j)
            {
                tokens[j] = tokens[j].trim();
            }
            Course c = new Course(tokens[0], 
                                  tokens[1], 
                                  Integer.parseInt(tokens[2]), 
                                  tokens[3]);
            courses.add(c);
            
        }
        System.out.println("Loaded " + courses.size() + " courses from " 
                          + file.getName() + "!");
        
        
    }
    
    public void saveCourseFile(File file)
    {
        System.out.println("Entering Save file");
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file)))
        {
            System.out.println("Save to file");
            for(int i = 0; i < this.courses.size(); ++i)
            {
                Course c = this.courses.get(i);
                String record = c.getId() + " ; " +
                              c.getTitle() + " ; " +
                              c.getCredit() + " ; " +
                              c.getCategory();
                br.write(record);
                br.newLine();
                
            }
            //log 
            br.close();
            
            System.out.println("Saved " + this.courses.size() + " courses to " +
                              file.getName());
        }
        catch(Exception e)
        {
            System.out.println("]ERROR] " + e.getMessage());
            return;
        }
    }
    
    public Course getNewCourseId()
    {
        co = new Course(this.newid);
        return co;
    }
    public Course getSelectedCourseId()
    {
        co = new Course(id);
        return co;
    }
    
    public void setNewCourseId(String id)
    {
        this.newid = id;
    }
    
    public void setSelectedCourseId(String s)
    {
        this.id = s;
    }
    
    public ArrayList<Course> findCoursesById(String id)
    {
            //uppercase conversion
            String keyword = id.toUpperCase();
            
            
            //search and store matched items to list
            ArrayList<Course> list = new ArrayList<>();
            
            for(int i = 0; i < courses.size(); ++i)
            {
                Course course = courses.get(i);
                String courseId = course.getId().toUpperCase();
                //System.out.println(courses);
                //checks if id contains user input
                if(courseId.contains(keyword))
                    list.add(course);
            }
            
            return list; 
    }
    public ArrayList<Course> findCoursesByTitle(String title)
    {
        //uppercase conversion
            String keyword = title.toLowerCase();
            
            //search and store matched items to list
            ArrayList<Course> list = new ArrayList<>();
            for(int i =0; i < courses.size(); ++i)
            {
                Course course = courses.get(i);
                String courseTitle = course.getTitle().toLowerCase();
                if(courseTitle.contains(keyword))
                    list.add(course);
            }
            return list;
        
    }
    
    public ArrayList<Course> findCoursesByCategory(String title)
    {
        //uppercase conversion
            String keyword = title.toLowerCase();
            
            //search and store matched items to list
            ArrayList<Course> list = new ArrayList<>();
            for(int i =0; i < courses.size(); ++i)
            {
                Course course = courses.get(i);
                String courseCategory = course.getCategory().toLowerCase();
                if(courseCategory.contains(keyword))
                    list.add(course);
            }
            return list;
        
    }
    
    public void removeCourse(String id)
    {
        Course c = new Course(id);
        int index = getCourseIndex(c);
        if(index >= 0)
            courses.remove(index);
    }
    
    public void updateCourse(String id, String title, int credit, String cat)
    {
        Course c = getCourse(id);
        if(c != null) { c.set(id,title,credit,cat); }
        System.out.println("Updated!");
    }
    
    public boolean validateId(String id)
    {
        //regexp: 4 alphabets followed by 5 numeric digits
        final String PATTERN = "^[A-Za-z]{4}[0-9]{5}$";
        if(id != null && id.matches(PATTERN))
            return true;
        else
            return false;
    }

    public void addCourse(Course c)
    {
        //add value fields to courses arraylist
        courses.add(c);
        
        //sort list
        Collections.sort(courses, new Comparator<Course>(){
        @Override
            public int compare(Course c1, Course c2)
            {
                return c1.getId().compareTo(c2.getId());  
            }
            
        });
        
        //grab newid through course
        newid = c.getId();
        //set the new course id
        setNewCourseId(newid);
               
    }
    //Using this method return the course ids by category
    public ArrayList<String> getCourseWithCat(String category)
    {
        ArrayList<String> cat = new ArrayList<>();
        //int count = 0;
        for(int i=0; i < courses.size(); ++i)
        {
            //System.out.println(count + ":" + courses.get(i));
            Course c = courses.get(i);
            
            if(c.getCategory().equals(category))
            {
               //get the id's from the courses
               cat.add(c.getId());
            }
            //count++;
         
        }
        return cat;
    }
   
}
