import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import org.sql2o.*;

public class SpecialtyTest {

  @Before
  public void initialize() {
    Specialty.main(new String[] {"a", "b"});
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hospital_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String clearSpecialtyList = "DELETE FROM specialties *;";
      con.createQuery(clearSpecialtyList).executeUpdate();
    }
  }

  @Test
  public void Specialty_instantiatesCorrecttly_true() {
    Specialty testSpecialty = new Specialty("Pediatric");
    assertTrue(testSpecialty instanceof Specialty);
  }

  @Test
  public void Specialty_AllInstancesfound_true() {
    System.out.println(Specialty.all().size());
    assertTrue(Specialty.all().size() > 0);
  }
}
