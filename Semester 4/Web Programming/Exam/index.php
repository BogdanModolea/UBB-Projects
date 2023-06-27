<?php
  session_start();
  if (isset($_POST['submit'])) {
    $username = $_POST['username'];

    $con = mysqli_connect("127.0.0.1", "root", "", "prepwp");
    $sql = "SELECT * FROM users WHERE user = '$username' LIMIT 1";
    $query = $con->query($sql);
    if ($query) {
        $row = mysqli_fetch_row($query);
        $name = $row[1];
    }
    if ($username == $name) {
        $userId = $row[0];
        $secretQuestion = $row[2];
        $secretAnswer = $row[3];
        $_SESSION['name'] = $name;
        $_SESSION['id'] = $userId;
        $_SESSION['secretQuestion'] = $secretQuestion;
        $_SESSION['secretAnswer'] = $secretAnswer;
        
        header("Location: secret.php");
        // header("Location: secret.html");

    } else {
        echo "<b><i>Incorrect credentials</i><b>";
    }
        $con->close();
    }

?>

<!DOCTYPE html>
<html>
    <body>
        <div>
            <form method="post" action="index.php">
                <input type="text" name="username" class="form-control" placeholder="Enter username">
                <br>
                <input type="submit" name="submit" value="Login">
            </form>
        </div>
    </body>
</html>