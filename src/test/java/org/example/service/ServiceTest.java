package org.example.service;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    Service service;

    @Before
    public void runBeforeTestMethod() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/TestStudenti.xml";
        String filenameTema = "fisiere/TestTeme.xml";
        String filenameNota = "fisiere/TestNote.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addStudent() {
        Student student = new Student("4000", "Name", 932, "examle@domain.com");
        int size = getStudentiSize();
        service.addStudent(student);
        assertEquals(size + 1, getStudentiSize());
    }

    @Test
    public void addStudent2() {
        Student student = new Student("4000", "Name", 932, "examle@domain.com");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
        service.deleteStudent("4000");
        assertEquals(size-1, getStudentiSize());
    }

    private int getStudentiSize() {
        List<Student> studenti = StreamSupport
                .stream(service.getAllStudenti().spliterator(), false)
                .collect(Collectors.toList());
        return studenti.size();
    }

    @Test
    public void addStudentWithGoodParameters() {
        Student student = new Student("2278", "Ion Popescu", 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        service.addStudent(student);
        assertEquals(size + 1, getStudentiSize());
        service.deleteStudent("2278");
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadId1() {
        Student student = new Student("", "Ion Popescu", 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadId2() {
        Student student = new Student(null, "Ion Popescu", 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadName1() {
        Student student = new Student("2278", "", 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadName2() {
        Student student = new Student("2278", "12", 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadName3() {
        Student student = new Student("2278", null, 932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadGroup() {
        Student student = new Student("2278", "Ion Popescu", -932, "ion.popescu@scs.ubbcluj.ro");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {
        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadEmail1() {
        Student student = new Student("2278", "Ion Popescu", 932, "");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadEmail2() {
        Student student = new Student("2278", "Ion Popescu", 932, "test");
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    @Test
    public void addStudentWithBadEmail3() {
        Student student = new Student("2278", "Ion Popescu", 932, null);
        int size = getStudentiSize();
        try {
            service.addStudent(student);
        } catch (Exception ignored) {

        }
        assertEquals(size, getStudentiSize());
    }

    private int getTemeSize() {
        List<Tema> teme = StreamSupport
                .stream(service.getAllTeme().spliterator(), false)
                .collect(Collectors.toList());
        return teme.size();
    }

    @Test
    public void addAssignment() {
        Tema tema = new Tema("2", "Descriere", 12, 10);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size + 1, getTemeSize());
        service.deleteTema(tema.getID());
    }

    @Test
    public void addAssignment2() {
        Tema tema = new Tema("4", "Descriere", -1, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadId1() {
        Tema tema = new Tema("", "Descriere", 14, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadId2() {
        Tema tema = new Tema(null, "Descriere", 14, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }


    @Test
    public void addAssignmentBadDescription() {
        Tema tema = new Tema("6", "", 14, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadDeadline1() {
        Tema tema = new Tema("6", "Descriere", 0, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadDeadline2() {
        Tema tema = new Tema("6", "Descriere", 15, 12);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadReceive1() {
        Tema tema = new Tema("6", "Descriere", 14, 0);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void addAssignmentBadReceive2() {
        Tema tema = new Tema("6", "Descriere", 14, 15);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size, getTemeSize());
    }

    @Test
    public void integrationTestingAddAssignment() {
        Student student = new Student("4070", "Name", 932, "examle@domain.com");
        int studentsSize = getStudentiSize();
        service.addStudent(student);
        assertEquals(studentsSize + 1, getStudentiSize());

        Tema tema = new Tema("2", "Descriere", 12, 10);
        int temeSize = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(temeSize + 1, getTemeSize());
        service.deleteTema(tema.getID());
        service.deleteStudent(student.getID());
    }

    @Test
    public void integrationTestingAddGrade() {
        Student student = new Student("4070", "Name", 932, "examle@domain.com");
        int studentsSize = getStudentiSize();
        service.addStudent(student);
        assertEquals(studentsSize + 1, getStudentiSize());

        Tema tema = new Tema("2", "Descriere", 12, 10);
        int temeSize = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        Nota nota = new Nota("101", student.getID(), tema.getID(), 9.5, LocalDate.of(2018, 10, 8));
        service.addNota(nota, "Keep up the good work!");

        assertEquals(temeSize + 1, getTemeSize());
        service.deleteNota(nota.getID());
        service.deleteTema(tema.getID());
        service.deleteStudent(student.getID());
    }
}