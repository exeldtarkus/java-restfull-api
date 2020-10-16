package co.id.adira.moservice.contentservice.model.bengkel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="ref_city", schema = "bengkel")
public class City {
  @Id
  @Column(name="city_id")
  private Long id;

  @Column(name="city_name")
  private String name;

  @Column(name="sort_1")
  private String sort;
  
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "provinsi_id", referencedColumnName = "provinsi_id", insertable = false, updatable = false)
  @JsonManagedReference
  private Province province;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}