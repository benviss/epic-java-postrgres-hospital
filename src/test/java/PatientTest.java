import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import org.sql2o.*;

public class PatientTest {

  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hospital_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
    }
  }

  @Test
  public void Patient_instantiesCorrectly_true() {
    Patient testPatient = new Patient("Jon Doe", "1990-01-01");
    assertTrue(testPatient instanceof Patient);
  }

  @Test
  public void Patient_PatientStoresName_bob() {
    Patient newPatient = new Patient("Bob", "2000-01-01");
    assertEquals("Bob", newPatient.getName());
  }

  @Test
  public void Patient_storesBirthdate_1990_01_01() {
    Patient newPatient = new Patient("Jon Doe", "1990-01-01");
    assertEquals("1990-01-01", newPatient.getBirthdate());
  }

  @Test
  public void save_PatientSavesToDatabase_true() {
    Patient newPatient = new Patient("Jon Doe", "1990-01-01");
    newPatient.save();
    assertTrue(newPatient.getId() > 0);
  }

  @Test
  public void find_returnsPatientWithSameId_newPatient() {
    Patient newPatient = new Patient("Jon Doe", "1990-01-01");
    newPatient.save();
    assertEquals(Patient.find(newPatient.getId()).getName(), newPatient.getName());
  }

  @Test
  public void Patient_storesMultiplePatients_true() {
    Patient firstPatient = new Patient("Jon Doe", "1990-01-01");
    firstPatient.save();
    Patient secondPatient = new Patient("Jon Doe", "1990-01-01");
    secondPatient.save();
    assertTrue(Patient.all().get(0).equals(firstPatient));
    assertTrue(Patient.all().get(1).equals(secondPatient));
  }

}
