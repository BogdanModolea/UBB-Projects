package com.ssvv;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.*;
import org.example.service.Service;
import org.example.validation.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AddStudentTest {

    public static Service service;

    public static void createXML() {
        File xml = new File("studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n<student ID=\"1\">\n" +
                    "        <Nume>Ana</Nume>\n" +
                    "        <Grupa>221</Grupa>\n" +
                    "    </student>\n</Entitati>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xml2 = new File("temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml2))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Teme>\n<tema ID=\"1\">\n" +
                    "        <Descriere>File</Descriere>\n" +
                    "        <Deadline>7</Deadline>\n" +
                    "        <Startline>6</Startline>\n" +
                    "    </tema>\n</Teme>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void setup() {
        createXML();

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studentiTest.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "temeTest.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        AddStudentTest.service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testValidStudent() {
        assertThat(AddStudentTest.service.saveStudent("70", "Ion", 934), is(0));
    }

    @Test
    public void testInvalidStudent() {
        assertThat(AddStudentTest.service.saveStudent("6", "John", 1000), is(1));
    }

    @AfterAll
    public static void removeXML() {
        new File("studentiTest.xml").delete();
        new File("temeTest.xml").delete();
    }



    @Test
    public void testCase1_BBT_EC() {
        assertThat(service.saveStudent("0", "Bogdan", 934), is(0));
    }

    @Test
    public void testCase2_BBT_EC() {
        assertThat(service.saveStudent(null, "Bogdan", 934), is(1));
    }

    @Test
    public void testCase3_BBT_EC() {
        assertThat(service.saveStudent("0", "", 934), is(1));
    }

    @Test
    public void testCase4_BBT_EC() {
        assertThat(service.saveStudent("0", "Bogdan", 939), is(1));
    }

    @Test
    public void testCase5_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", 111), is(0));
    }

    @Test
    public void testCase6_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", 109), is(1));
    }

    @Test
    public void testCase7_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", 937), is(0));
    }

    @Test
    public void testCase8_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", 939), is(1));
    }

    @Test
    public void testCase9_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", 200), is(0));
    }

    @Test
    public void testCase10_BBT_BVA() {
        assertThat(service.saveStudent("0", "Bogdan", -1), is(1));
    }

    @Test
    public void testCase11_BBT_BVA() {
        assertThat(service.saveStudent("0", "abc", 123), is(0));
    }

    @Test
    public void testCase12_BBT_BVA() {
        assertThat(service.saveStudent("0", "", 123), is(1));
    }

    @Test
    public void testCase13_BBT_BVA() {
        assertThat(service.saveStudent("0", null, 123), is(1));
    }

    @Test
    public void testCase14_BBT_BVA() {
        assertThat(service.saveStudent("", "Bogdan", 934), is(1));
    }



    @Test
    public void testValidTema() {
        assertThat(AddStudentTest.service.saveTema("2", "TestValid", 9, 7), is(0));
    }

    @Test
    public void testInvalidTema() {
        assertThat(AddStudentTest.service.saveTema("3", "TestInvalid", 20, 7), is(1));
    }
}
