package service;

import domain.Student;
import org.junit.Assert;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.Assert.assertThrows;

public class ServiceTest {

    private final Service service;

    public ServiceTest() {
        String filenameStudent = "fisiere/tests/StudentiTest.xml";
        String filenameTema = "fisiere/tests/TemeTest.xml";
        String filenameNota = "fisiere/tests/NoteTest.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        this.service = new Service(studentXMLRepository, new StudentValidator(), temaXMLRepository, new TemaValidator(), notaXMLRepository, notaValidator);
    }

    @Test
    public void testAddStudentWithNull() {
        var exception = assertThrows(RuntimeException.class, () -> {
            service.addStudent(null);
        });

        var expectedMessage = "Cannot invoke \"domain.Student.getID()\" because \"entity\" is null";
        var actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAddStudentWithValidInput(){
        var studentId = String.valueOf(Math.random() % 1000);
        var student = new Student(studentId, "Dori", 933, "daie2710@scs.ubbcluj.ro");
        var addedStudent = service.addStudent(student);
        Assert.assertNull(addedStudent);

        var retrievedStudent = service.findStudent(studentId);
        Assert.assertEquals(retrievedStudent.getID(), student.getID());
        Assert.assertEquals(retrievedStudent.getNume(), student.getNume());
        Assert.assertEquals(retrievedStudent.getGrupa(), student.getGrupa());
        Assert.assertEquals(retrievedStudent.getEmail(), student.getEmail());
    }
}