import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirth {
    public void Printnames() {
        FileResource fr = new FileResource();
        String gender;
        int numBorn;

        for (CSVRecord record : fr.getCSVParser(false)) { // CSV file does not have a header row
            numBorn = Integer.parseInt(record.get(2));

            if (record.get(1).equals("F")) { gender = "Female"; }
            else { gender = "Male"; }

            if (numBorn<=100) {
                System.out.println("Name is " + record.get(0)
                        + ", " + gender
                        + ", Num born is " + numBorn);
            }
        }
    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        o.Printnames();
    }
}
