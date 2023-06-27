<?php
// header('Access-Control-Allow-Origin: *');
// header('Access-Control-Allow-Headers: Content-Type');
// header('Access-Control-Allow-Methods: POST, OPTIONS');
// header('Content-Type: application/json');
session_start();
if (isset($_SESSION['id'])) {
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];
    $firstName = $_SESSION['firstName'];
} else {
    echo "<b><i>Nu-i bine</i><b>";
    // header('Location: http://localhost:4200/');
    die();
}

if (isset($_POST['logout'])) {
    session_destroy();
    header('Location: http://localhost:4200/');
}
?>

<!DOCTYPE html>
<html>

<head>
    <title>Welcome!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>

<body>
    <div class="col-md-2 col-md-offset-5">
        <h3>Welcome, <?php echo $firstName; ?>! </h3>

        <div>
            <p>Your grades:</p>
            <?php
            if (isset($_GET['pageno'])) {
                $pageno = $_GET['pageno'];
            } else {
                $pageno = 1;
            }
            $no_of_records_per_page = 4;
            $offset = ($pageno - 1) * $no_of_records_per_page;

            $con = mysqli_connect("127.0.0.1", "root", "", "students");

            $total_pages_sql = "SELECT COUNT(*) FROM students.grades INNER JOIN students.course ON students.grades.courseId=students.course.id WHERE studentId='$userId'";
            $result = mysqli_query($con, $total_pages_sql);
            $total_rows = mysqli_fetch_array($result)[0];
            $total_pages = ceil($total_rows / $no_of_records_per_page);

            $query = "SELECT * FROM students.grades INNER JOIN students.course ON students.grades.courseId=students.course.id WHERE studentId='$userId' LIMIT $offset, $no_of_records_per_page";
            $result = mysqli_query($con, $query);
            if (mysqli_num_rows($result) > 0) {
                echo "<table border = \"1\">";
                echo "<tr>";
                echo "<th>Course name</th>";
                echo "<th>Grade</th>";
                echo "</tr>";
                while ($row = mysqli_fetch_array($result)) {
                    echo "<tr>";
                    echo "<th>" . $row['name'] . "</th>";
                    echo "<th>" . $row['grade'] . "</th>";
                    echo "</tr>";
                }
                echo "</table>";
            }

            $con->close();
            ?>
            <br>
            <ul class="pagination">
                <li><a href="?pageno=1">First</a></li>&emsp;
                <li class="<?php if ($pageno <= 1) {
                                echo 'disabled';
                            } ?>">
                    <a href="<?php if ($pageno <= 1) {
                                    echo '#';
                                } else {
                                    echo "?pageno=" . ($pageno - 1);
                                } ?>">Prev</a>
                </li>&emsp;
                <li class="<?php if ($pageno >= $total_pages) {
                                echo 'disabled';
                            } ?>">
                    <a href="<?php if ($pageno >= $total_pages) {
                                    echo '#';
                                } else {
                                    echo "?pageno=" . ($pageno + 1);
                                } ?>">Next</a>
                </li>&emsp;
                <li><a href="?pageno=<?php echo $total_pages; ?>">Last</a></li>&emsp;
            </ul>
        </div>
        <br>
        <form method="post">
            <input type="submit" class="btn btn-secondary" name="logout" value="Logout">
        </form>
        <br>
    </div>
</body>

</html>