package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Task type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks")
@Index(name = "byTeam", fields = {"teamName","title","desc","state"})
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TEAM_NAME = field("Task", "teamName");
  public static final QueryField TITLE = field("Task", "title");
  public static final QueryField DESC = field("Task", "desc");
  public static final QueryField STATE = field("Task", "state");
  public static final QueryField FILE = field("Task", "file");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String teamName;
  private final @ModelField(targetType="String") String title;
  private final @ModelField(targetType="String") String desc;
  private final @ModelField(targetType="String") String state;
  private final @ModelField(targetType="String") String file;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTeamName() {
      return teamName;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDesc() {
      return desc;
  }
  
  public String getState() {
      return state;
  }
  
  public String getFile() {
      return file;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String teamName, String title, String desc, String state, String file) {
    this.id = id;
    this.teamName = teamName;
    this.title = title;
    this.desc = desc;
    this.state = state;
    this.file = file;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Task task = (Task) obj;
      return ObjectsCompat.equals(getId(), task.getId()) &&
              ObjectsCompat.equals(getTeamName(), task.getTeamName()) &&
              ObjectsCompat.equals(getTitle(), task.getTitle()) &&
              ObjectsCompat.equals(getDesc(), task.getDesc()) &&
              ObjectsCompat.equals(getState(), task.getState()) &&
              ObjectsCompat.equals(getFile(), task.getFile()) &&
              ObjectsCompat.equals(getCreatedAt(), task.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), task.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTeamName())
      .append(getTitle())
      .append(getDesc())
      .append(getState())
      .append(getFile())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Task {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("teamName=" + String.valueOf(getTeamName()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("desc=" + String.valueOf(getDesc()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("file=" + String.valueOf(getFile()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TeamNameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Task justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Task(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      teamName,
      title,
      desc,
      state,
      file);
  }
  public interface TeamNameStep {
    BuildStep teamName(String teamName);
  }
  

  public interface BuildStep {
    Task build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep title(String title);
    BuildStep desc(String desc);
    BuildStep state(String state);
    BuildStep file(String file);
  }
  

  public static class Builder implements TeamNameStep, BuildStep {
    private String id;
    private String teamName;
    private String title;
    private String desc;
    private String state;
    private String file;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          teamName,
          title,
          desc,
          state,
          file);
    }
    
    @Override
     public BuildStep teamName(String teamName) {
        Objects.requireNonNull(teamName);
        this.teamName = teamName;
        return this;
    }
    
    @Override
     public BuildStep title(String title) {
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep desc(String desc) {
        this.desc = desc;
        return this;
    }
    
    @Override
     public BuildStep state(String state) {
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep file(String file) {
        this.file = file;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String teamName, String title, String desc, String state, String file) {
      super.id(id);
      super.teamName(teamName)
        .title(title)
        .desc(desc)
        .state(state)
        .file(file);
    }
    
    @Override
     public CopyOfBuilder teamName(String teamName) {
      return (CopyOfBuilder) super.teamName(teamName);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder desc(String desc) {
      return (CopyOfBuilder) super.desc(desc);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder file(String file) {
      return (CopyOfBuilder) super.file(file);
    }
  }
  
}
