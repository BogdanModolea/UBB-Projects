<?php

class Grade implements JsonSerializable {
    private $studentId;
    private $courseId;
    private $grade;
    private $courseName;
    private $username;

    public function __construct($studentId, $courseId, $grade, $courseName, $username) {
        $this->studentId = $studentId;
        $this->courseId = $courseId;
        $this->grade = $grade;
        $this->courseName = $courseName;
        $this->username = $username;
    }

    public function getStudentId() {
        return $this->studentId;
    }
    public function getCourseId() {
        return $this->courseId;
    }
    public function getGrade() {
        return $this->grade;
    }
    function getCourseName()
    {
        return $this->courseName;
    }
    public function setStudentId($studentId) {
        $this->studentId = $studentId;
    }
    public function setCourseId($courseId) {
        $this->courseId = $courseId;
    }
    public function setGrade($grade) {
        $this->grade = $grade;
    }
    public function setCourseName($courseName)
    {
        $this->courseName = $courseName;
    }
    public function getUsername()
    {
        return $this->username;
    }
    public function setUsername($username)
    {
        $this->username = $username;
    }



    public function jsonSerialize() {
        $vars = get_object_vars($this);
        return $vars;
    }
}

?>
