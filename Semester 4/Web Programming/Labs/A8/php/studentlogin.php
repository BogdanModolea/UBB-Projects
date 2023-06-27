<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Headers: Content-Type');
// header('Access-Control-Allow-Methods: POST, OPTIONS');
// header('Content-Type: application/json');
session_start();

if (isset($_POST['submit'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    $con = mysqli_connect("127.0.0.1", "root", "", "students");
    $sql = "SELECT id,username,password,firstName FROM students.student where username = '$username' LIMIT 1";
    $query = $con->query($sql);
    if ($query) {
        $row = mysqli_fetch_row($query);
        $userId = $row[0];
        $dbUserName = $row[1];
        $dbPassword = $row[2];
        $firstName = $row[3];
    }
    if ($username == $dbUserName && $password == $dbPassword && $username != '') {
        $_SESSION['username'] = $username;
        $_SESSION['id'] = $userId;
        $_SESSION['firstName'] = $firstName;
        // echo json_encode($_SESSION['id']);
        header('Location: http://localhost:4200/student-home');
    } else {
        echo "<b><i>Incorrect credentials</i><b>";
    }
    $con->close();
}
?>

<!-- <!DOCTYPE html>
<html>

<head>
    <title>Login Page</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
    <div class="col-md-2 col-md-offset-5">
        <h1>Login</h1>
        <form method="post" action="studentlogin.php">
            <input type="text" name="username" class="form-control" placeholder="Enter username">
            <br>
            <input type="password" name="password" class="form-control" placeholder="Enter password here">
            <br>
            <input class="btn btn-primary btn-block" type="submit" name="submit" value="Login">
        </form>
    </div>

</body>

</html> -->