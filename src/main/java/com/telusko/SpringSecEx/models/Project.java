package com.telusko.SpringSecEx.models;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.SpringSecEx.Entity.Users;
import jakarta.persistence.*;
        import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "JSON")
    private String techStack;  // Handle JSON manually

    private String difficulty;

    private String status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Users lead;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // JSON handling methods
    public void setTechStack(List<String> techStack) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.techStack = objectMapper.writeValueAsString(techStack);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTechStack() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(this.techStack, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
