package com.javaparttwo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.javaparttwo.model.Course;
import com.javaparttwo.model.Grade;

public class GradeService {
	
	 /**
     * An instance of the database connection.
     */
    private DataSource ds;

    /**
     * Initializes login service.
     * 
     * @param ds
     *            The data source instance.
     */
    public GradeService(DataSource ds) {
    	this.ds = ds;
    }
    
    public Grade getGrade(String stdId, String courseId) {
    	Connection con = null;
    	ResultSet rs = null;
    	PreparedStatement stmt = null;

    	String str = "SELECT * FROM javapart2.grades LEFT JOIN javapart2.courses ON javapart2.grades.course_id = javapart2.courses.id WHERE student_id=? AND course_id=?";

    	try {
    	    con = ds.getConnection();

    	    stmt = con.prepareStatement(str);
    	    stmt.setString(1, stdId);
    	    stmt.setString(2, courseId);

    	    rs = stmt.executeQuery();

    	    if (rs.next()) {
    		return new Grade(rs.getString("id"), rs.getString("title"), rs.getInt("ects"),
    			rs.getInt("teaching_hours"), rs.getString("instructor_username"), rs.getInt("grade"), rs.getInt("semester"), rs.getString("department_id"));
    	    }

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
	    		rs.close();
	    		stmt.close();
	    		con.close();
    	    } catch (SQLException e) {
    	    	e.printStackTrace();
    	    }
    	}

    	return null;
    }
    
    
    public List<Grade> getGrades(String stdId) {
    	
    	return null;
    }

}