package co.id.adira.moservice.contentservice.model.bengkel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="ref_provinsi", schema = "db_bengkel")
public class Province {

  @Id
  @Column(name="provinsi_id")
  private Long id;

  @Column(name="provinsi_name")
  private String name;

  @OneToMany(targetEntity=City.class)
  @JoinColumn(name = "provinsi_id", referencedColumnName = "provinsi_id", insertable = false, updatable = false)
  @JsonBackReference
  private List<City> cities;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}