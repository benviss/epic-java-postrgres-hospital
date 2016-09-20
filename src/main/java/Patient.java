import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;


public class Patient {
  private int id;
  private String name;
  private String birthdate;
  private int doctor_id;

  public Patient(String name, String birthdate) {
    this.name = name;
    this.birthdate = birthdate;
  }

  public String getName() {
    return name;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO patients(name, birthdate) VALUES (:name, :birthdate);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("birthdate", this.birthdate)
      .executeUpdate()
      .getKey();
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients WHERE id=:id;";
      Patient patient = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Patient.class);
      return patient;
    }
  }

  public static List<Patient> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients;";
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

    @Override
    public boolean equals(Object otherPatient) {
      if(!(otherPatient instanceof Patient)) {
        return false;
      } else {
        Patient newPatient = (Patient) otherPatient;
        return this.getName().equals(newPatient.getName()) && this.getId() == newPatient.getId() && this.getBirthdate().equals(newPatient.getBirthdate());
      }
    }

}
