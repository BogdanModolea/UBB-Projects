<?php
session_start();
if (isset($_SESSION['id'])) {
    $userId = $_SESSION['id'];
    $username = $_SESSION['username'];
    $firstName = $_SESSION['firstName'];
} else {
    header('Location: login.php');
    die();
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
        <form action="update.php" method="post">
            <div id="update-grade-div">
                <input id="group" class="form-control" type="text" name="group" placeholder="Group">
                <br>
                <input id="course" class="form-control" type="text" name="course" placeholder="Course">
                <br>
                <input type="submit" class="btn btn-primary" name="update" value="Update grades for group and course">
                <br><br>
                <input type="submit" class="btn btn-primary" name="add" formaction="add.php" value="Add grades for group and course">
            </div>
        </form>
        <br>
        <form action="index.html">
            <input type="submit" class="btn btn-secondary" name="logout" value="Logout">
        </form>
        <br>
    </div>
</body>

</html>