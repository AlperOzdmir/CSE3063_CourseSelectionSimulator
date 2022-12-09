package iteration2.Controllers;

import iteration2.Models.*;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RandomizationController extends Controller {

    private final String[] firstNames = {"John", "Jane", "Bob", "Alice", "Joe", "Mary", "Tom", "Sally", "Bill", "Sue"};
    private final String[] lastNames = {"Smith", "Jones", "Brown", "Johnson", "Williams", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};

    public RandomizationController(RegistrationError error) {
        super.setError(error);
        try {
            File dir = new File(System.getProperty("user.dir") + "/iteration2/Data/Input/Students");
            for(File file: dir.listFiles())
                if (!file.isDirectory())
                    file.delete();

            dir = new File(System.getProperty("user.dir") + "/iteration2/Data/Output/Students");
            for(File file: dir.listFiles()){
                file.delete();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public ArrayList<Student> generateStudentsAndExport(ArrayList<Course> courses, ArrayList<Advisor> advisors) {
        JSONObject parameters = null;
        try {
            Object obj = new JSONParser()
                    .parse(new FileReader(System.getProperty("user.dir") + "/iteration2/Data/Input/parameters.json"));
            parameters = new JSONObject(obj.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int semester = parameters.getString("semester").equals("FALL") ? 1 : 2;
        int newStudentsCount = parameters.getInt("student_count");

        ArrayList<Student> students = generateStudents(newStudentsCount, courses, advisors, semester);
        students.forEach(this::exportJSONFile);
        return students;
    }

    public ArrayList<Student> generateStudents(int count, ArrayList<Course> courses, ArrayList<Advisor> advisors, int semester) {
        ArrayList<Student> students = new ArrayList<>();
        semester = semester + 2 * ((int)(Math.random() * 4));
        String ssn = "";

        for (int i = 0; i < count; i++) {
            ssn = String.valueOf((long) (Math.random() * 1000000000));
            ssn += String.valueOf((int) (Math.random() * 100));
            Advisor advisor = chooseRandomAdvisor(students, advisors);
            students.add(new Student(
                    firstNames[(int) (Math.random() * firstNames.length)],
                    lastNames[(int) (Math.random() * lastNames.length)],
                    ssn,
                    (int) (Math.random() * 2) == 1 ? 'm' : 'f',
                    "1501" + String.valueOf(2022 - (semester / 2)).substring(1, 3).concat(String.valueOf((int) (Math.random() * 899 + 100))),
                    semester == 4 && (int) (Math.random() * 2) == 1,
                    2022 - (semester / 2),
                    semester,
                    generateTranscript(semester, courses),
                    advisor
            ));

            advisor.getStudents().add(students.get(students.size() - 1));

            students.forEach((n) -> {
                n.setSelectedCourses(new HashMap<Course, Boolean>());
            });

        }

        System.out.println("Generated new " + (count) + " students.");

        return students;
    }


    public Transcript generateTranscript(int semester, ArrayList<Course> courses) {
        Transcript transcript = new Transcript(
                0,
                0,
                new ArrayList<>(),
                new ArrayList<>()
        );

        boolean provides = true;

        for (Course course : courses) {
            if (course.getSemester() <= semester) {
                //Student passed the course
                if ((int) (Math.random() * 2) == 1) {
                    provides = true;
                    for (Course prerequisite : course.getPreRequisiteCourses()) {
                        if (!transcript.getCompletedCourses().contains(prerequisite)) {
                            provides = false;
                            break;
                        }
                    }

                    if (provides) {
                        transcript.addToCompletedCourses(course);
                        transcript.setCompletedCredit(transcript.getCompletedCredit() + course.getCredit());
                    }

                } else { //Student failed the course
                    transcript.addToFailedCourses(course);
                }
            }
        }

        transcript.calculateGPA();

        return transcript;
    }

    public Advisor chooseRandomAdvisor(ArrayList<Student> students, ArrayList<Advisor> advisors) {
        double studentCount = students.size();
        for (Advisor advisor : advisors) {
            if (advisor.getStudents().size() <= studentCount / advisors.size()) {
                return advisor;
            }
        }

        return advisors.get(0);
    }

    @Override
    public boolean exportJSONFile(Object object) {
        String content = (((Student) object).toJson()).toString();

        String fullFileName = System.getProperty("user.dir") + "/iteration2/Data/Input/Students/"
                + ((Student) object).getId() + ".json";

        try {
            File myObj = new File(fullFileName);
            myObj.createNewFile();

            FileWriter myWriter = new FileWriter(fullFileName);
            myWriter.write(content);
            myWriter.close();
        } catch (Exception e) {
            System.out.println("An error occurred while exporting random .json file data.");
            e.printStackTrace();
        }

        return true;
    }
}