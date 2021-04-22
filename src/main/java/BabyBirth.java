import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyBirth {
    public void Printnames() {
        FileResource fr = new FileResource();
        String gender;

        for (CSVRecord record : fr.getCSVParser(false)) { // CSV file does not have a header row

            if (record.get(1).equals("F")) { gender = "Female"; }
            else { gender = "Male"; }

            System.out.println("Name is " + record.get(0)
                    + ", " + gender
                    + ", Num born is " + record.get(2));
        }
    }

    public static void main(String[] args) {
        BabyBirth o = new BabyBirth();
        //o.testHottestInDay();
        o.Printnames();
    }
}
