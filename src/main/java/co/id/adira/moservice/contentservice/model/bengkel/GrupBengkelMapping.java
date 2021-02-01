package co.id.adira.moservice.contentservice.model.bengkel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "map_group_bengkel")
public class GrupBengkelMapping implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "group_bengkel_od")
    private Long groupBengkelId;
    
    @Column(name = "bengkeL_id")
    private Long bengkelId;

    
    public Long getId(){
        return id;
    } 

    public void setId(Long id){
        this.id = id;
    }

    public Long getGroupBengkelId(){
        return groupBengkelId;
    }

    public void setgroupBengkelId(Long groupBengkelId) {
        this.groupBengkelId = groupBengkelId;
    }

    public Long getbengkelId(){
        return bengkelId;
    }

    public void setbengkelId(Long bengkelId){
        this.bengkelId = bengkelId;
    }

}
