<?php
// enable CORS - required for Angular UI
header("Access-Control-Allow-Headers: *");
header("Access-Control-Allow-Origin: *");

require_once '../model/model.php';
require_once '../view/view.php';

class Controller
{
    private $view;
    private $model;

    public function __construct(){
        $this->model = new Model ();
        $this->view = new View();
    }

    public function service() {
        if (isset($_GET['action']) && !empty($_GET['action'])) {

            if ($_GET['action'] == "getGradesStudent") {
                $this->{$_GET['action']}($_GET['username']);
            } else if ($_GET['action'] == "getGradesProfessor") {
                $this->{$_GET['action']}($_GET['group'], $_GET['course']);
            } else if ($_GET['action'] == "getStudents") {
                $this->{$_GET['action']}($_GET['group'],$_GET['page'],$_GET['size']);
            } else if ($_GET['action'] == "getAllStudents") {
                $this->{$_GET['action']}($_GET['group']);
            } else if ($_GET['action'] == "addGrade") {
                $this->{$_GET['action']}($_GET['studentId'], $_GET['course'], $_GET['grade']);
            } else if ($_GET['action'] == "updateGrade") {
                $this->{$_GET['action']}($_GET['studentId'], $_GET['course'], $_GET['grade']);
            } else if ($_GET['action'] == "getNameByUsername") {
                $this->{$_GET['action']}($_GET['username']);
            } else if ($_GET['action'] == "getStudent") {
                $this->{$_GET['action']}($_GET['username'],$_GET['password']);
            } else if ($_GET['action'] == "getProfessor") {
                $this->{$_GET['action']}($_GET['username'],$_GET['password']);
            }
        }
        else {
            $postdata = file_get_contents("php://input");
            if (isset($postdata) && !empty($postdata)) {
                $request = json_decode($postdata, true);
                if ($request['action'] == "getStudent") {
                    $this->{$request['action']}($request['username'],$request['password']);
                } else if ($request['action'] == "getProfessor") {
                    $this->{$request['action']}($request['username'],$request['password']);
                }
            }
        }
    }

    public function getStudent($username, $password) {
        $student = $this->model->getStudent($username, $password);
        return $this->view->output($student);
    }

    public function getProfessor($username, $password) {
        $professor = $this->model->getProfessor($username, $password);
        return $this->view->output($professor);
    }

    public function getGradesStudent($username)
    {
        $grades = $this->model->getGradesStudent($username);
        return $this->view->output($grades);
    }

    public function getGradesProfessor($group, $course)
    {
        $grades = $this->model->getGradesProfessor($group, $course);
        return $this->view->output($grades);
    }

    public function getAllStudents($group)
    {
        $students = $this->model->getAllStudents($group);
        return $this->view->output($students);
    }

    public function getStudents($group, $page, $size)
    {
        $students = $this->model->getStudents($group, $page, $size);
        return $this->view->output($students);
    }

    public function getNameByUsername($username)
    {
        $name = $this->model->getNameByUsername($username);
        return $this->view->output($name);
    }

    public function addGrade($studentId, $course, $grade)
    {
        $courseId = $this->model->getCourseIdByName($course);

        $result = $this->model->addGrade($studentId, $courseId, $grade);
        if ($result>0) { $r = "Success"; }
        else { $r = "Failure"; }
        $this->view->returnResult($r);
    }

    public function updateGrade($studentId, $course, $grade)
    {
        $courseId = $this->model->getCourseIdByName($course);

        $result = $this->model->updateGrade($studentId, $courseId, $grade);
        if ($result>0) { $r = "Success"; }
        else { $r = "Failure"; }
        $this->view->returnResult($r);
    }
}

$controller = new Controller();
$controller->service();

?>
