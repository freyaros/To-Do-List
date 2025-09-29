package com.example.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marks this class as a JPA entity (table in the database)
@Data // Lombok: Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok: Generates a no-argument constructor
public class TodoItem {

    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID
    private Long id;

    @SuppressWarnings("unused")
	private String task;

    @SuppressWarnings("unused")
	private Boolean completed = false; // Default value is false
    
 // ... (id, task, completed fields)

    // NEW: Link to the User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
    // Lombok's @Data and @NoArgsConstructor cover most needs, 
    // but you might add a constructor for creating new tasks if not using Lombok.
 // Add this if you don't use Lombok or if Lombok is causing issues
    public TodoItem() {
        // Required by JPA for entity instantiation
    }

	

	public void setTask(Object task2) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	public Boolean getCompleted() {
		// TODO Auto-generated method stub
		return null;
	}
	// Manually added Getters and Setters (if @Data is failing)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Use isCompleted() for the primitive boolean type
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }



    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}