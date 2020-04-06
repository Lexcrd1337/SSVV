package org.example;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.service.Service;
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

public class IntegrationTest {
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
        Student student = new Student("4002", "Name", 932, "examle@domain.com");
        int size = getStudentiSize();
        service.addStudent(student);

        assertEquals(size + 1, getStudentiSize());
        service.deleteStudent(student.getID());
    }

    @Test
    public void addAssignment() {
        Tema tema = new Tema("201", "Descriere", 12, 10);
        int size = getTemeSize();

        try {
            service.addTema(tema);
        } catch (Exception ignored) {

        }

        assertEquals(size + 1, getTemeSize());
        service.deleteTema(tema.getID());
    }

    @Test
    public void addGrade() {
        Nota nota = new Nota("100", "4000", "2", 10, LocalDate.now());
        int size = getNoteSize();

        try {
            service.addNota(nota, "E oke");
        } catch (Exception ignored) {

        }

        assertEquals(size, getNoteSize());
    }

    @Test
    public void integrationTest() {
        int studentsSize = getStudentiSize();
        int temeSize = getTemeSize();
        int noteSize = getNoteSize();

        Student student = new Student("4001", "Name", 932, "examle@domain.com");
        Tema tema = new Tema("200", "Descriere", 12, 10);
        service.addStudent(student);
        service.addTema(tema);

        Nota nota = new Nota("101", student.getID(), tema.getID(), 10, LocalDate.of(2018, 11, 16));
        service.addNota(nota, "Perfect");

        assertEquals(studentsSize + 1, getStudentiSize());
        assertEquals(temeSize + 1, getTemeSize());
        assertEquals(noteSize + 1, getNoteSize());

        service.deleteNota(nota.getID());
        service.deleteTema(tema.getID());
        service.deleteStudent(student.getID());
    }

    private int getStudentiSize() {
        List<Student> studenti = StreamSupport
                .stream(service.getAllStudenti().spliterator(), false)
                .collect(Collectors.toList());
        return studenti.size();
    }

    private int getTemeSize() {
        List<Tema> teme = StreamSupport
                .stream(service.getAllTeme().spliterator(), false)
                .collect(Collectors.toList());
        return teme.size();
    }

    private int getNoteSize() {
        List<Nota> note = StreamSupport
                .stream(service.getAllNote().spliterator(), false)
                .collect(Collectors.toList());
        return note.size();
    }
}
