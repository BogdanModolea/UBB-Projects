<?php

require_once '../repo/DBUtils.php';
require_once 'entity/student.php';
require_once 'entity/professor.php';
require_once 'entity/grade.php';


class Model {
    private $db;

    public function __construct() {
        $this->db = new DBUtils ();
    }

    public function getStudent($username, $password) {
        $resultset = $this->db->getStudent($username, $password);
        if(count($resultset) != 0)
            $student = new Student($resultset[0]['id'], $resultset[0]['firstName'], $resultset[0]['lastName'], $resultset[0]['studentGroup'], $resultset[0]['username'], $resultset[0]['password']);
        else
            $student = new Student(-1, '', '', '', '', '');
        return $student;
    }

    public function getProfessor($username, $password) {
        $resultset = $this->db->getProfessor($username, $password);
        if(count($resultset) != 0)
            $professor = new Professor($resultset[0]['id'], $resultset[0]['firstName'], $resultset[0]['username'], $resultset[0]['password']);
        else
            $professor = new Professor(-1, '', '', '');
        return $professor;
    }

    public function getGradesStudent($username)
    {
        $resultset = $this->db->getGradesStudent($username);
        $grades = array();
        foreach($resultset as $key=>$val) {
            $gr = new Grade($val['studentId'], $val['courseId'], $val['grade'], $val['name'], $val['username']);
            array_push($grades, $gr);
        }
        return $grades;
    }

    public function getGradesProfessor($group, $course)
    {
        $resultset = $this->db->getGradesProfessor($group, $course);
        $grades = array();
        foreach($resultset as $key=>$val) {
            $gr = new Grade($val['studentId'], $val['courseId'], $val['grade'], $val['name'], $val['username']);
            array_push($grades, $gr);
        }
        return $grades;
    }

    public function getAllStudents($group)
    {
        $resultset = $this->db->getAllStudents($group);
        $students = array();
        foreach($resultset as $key=>$val) {
            $stud = new Student($val['id'], $val['firstName'], $val['lastName'], $val['studentGroup'], $val['username'], $val['password']);
            array_push($students, $stud);
        }
        return $students;
    }

    public function getStudents($group, $page, $size)
    {
        //$total = count($this->getAllStudents($group));

        $offset = $page * $size;
        //$totalPages = $total / $size;
        $resultset = $this->db->getStudents($group, $size, $offset);
        $students = array();
        foreach($resultset as $key=>$val) {
            $stud = new Student($val['id'], $val['firstName'], $val['lastName'], $val['studentGroup'], $val['username'], $val['password']);
            array_push($students, $stud);
        }
        return $students;
    }

    public function getCourseIdByName($course)
    {
        $resultset = $this->db->getCourseIdByName($course);
        $id = $resultset[0]['id'];
        return $id;
    }

    public function getNameByUsername($username)
    {
        $resultset = $this->db->getNameByUsername($username);
        $name = $resultset[0]['firstName'] . " " . $resultset[0]['lastName'];
        return $name;
    }

    public function addGrade($studentId, $courseId, $grade)
    {
        return $this->db->addGrade($studentId, $courseId, $grade);
    }

    public function updateGrade($studentId, $courseId, $grade)
    {
        return $this->db->updateGrade($studentId, $courseId, $grade);
    }
}

?>
