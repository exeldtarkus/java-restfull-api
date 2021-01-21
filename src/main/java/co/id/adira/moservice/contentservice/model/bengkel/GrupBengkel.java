package co.id.adira.moservice.contentservice.model.bengkel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_group_bengkel")
public class GrupBengkel implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "group_bengkel_name")
    private String name;
    
    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @Column(name = "created")
	private Date created;

	@Column(name = "updated")
    private Date updated;
    
    public Long getId(){
        return id;
    } 

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }

    public Boolean getIsDeleted(){
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
