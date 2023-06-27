<?php

class DBUtils {
    private $host = 'localhost:3306';
    private $db   = 'students';
    private $user = 'root';
    private $pass = '';
    private $charset = 'utf8';

    private $pdo;
    private $error;

    public function __construct () {
        $dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
        $opt = array(PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES   => false);
        try {
            $this->pdo = new PDO($dsn, $this->user, $this->pass, $opt);
        } // Catch any errors
        catch(PDOException $e){
            $this->error = $e->getMessage();
            echo "Error connecting to DB: " . $this->error;
        }
    }

    public function getStudent($username, $password) {
        $stmt = $this->pdo->prepare("SELECT * FROM student WHERE username= :user AND password= :pass");
        $stmt->bindParam(':user', $username);
        $stmt->bindParam(':pass', $password);
        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getProfessor($username, $password) {
        $stmt = $this->pdo->prepare("SELECT * FROM professor WHERE username= :user AND password= :pass");
        $stmt->bindParam(':user', $username);
        $stmt->bindParam(':pass', $password);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getGradesStudent($username) {
        $stmt = $this->pdo->prepare("SELECT * FROM grades INNER JOIN student ON grades.studentId = student.id INNER JOIN course ON course.id = grades.courseId WHERE username= :user");
        $stmt->bindParam(':user', $username);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getGradesProfessor($group, $course) {
        $stmt = $this->pdo->prepare("SELECT * FROM grades INNER JOIN student ON grades.studentId = student.id INNER JOIN course ON grades.courseId = course.id WHERE studentGroup = :grp AND name = :course");
        $stmt->bindParam(':grp', $group);
        $stmt->bindParam(':course', $course);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getAllStudents($group) {
    $stmt = $this->pdo->prepare("SELECT * FROM student WHERE studentGroup = :grp");
    $stmt->bindParam(':grp', $group);
    $stmt->execute();
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getStudents($group, $size, $offset) {
        $stmt = $this->pdo->query("SELECT * FROM student WHERE studentGroup = '" . $group . "' LIMIT " . $offset . ", " . $size);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getCourseIdByName($course) {
        $stmt = $this->pdo->prepare("SELECT id FROM course WHERE name= :crs");
        $stmt->bindParam(':crs', $course);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getNameByUsername($username) {
        $stmt = $this->pdo->query("SELECT * FROM student WHERE username='" . $username . "'");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function addGrade($studentId, $courseId, $grade) {
        $affectedRows = $this->pdo->exec("INSERT INTO grades(studentId, courseId, grade) VALUES (" . $studentId . "," . $courseId . "," . $grade . ")");
        return $affectedRows;
    }

    public function updateGrade($studentId, $courseId, $grade) {
        $affectedRows = $this->pdo->exec("UPDATE grades SET grade=" . $grade . " WHERE studentId=" . $studentId . " AND courseId=" . $courseId . " ");
        return $affectedRows;
    }
}


?>

