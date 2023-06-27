<?php

    session_start();
    $question = $_SESSION['secretQuestion'];
    $username = $_SESSION['name'];
    echo '<h2>' . $question . '</h2>';

    if (isset($_POST['submit'])) {
        $ans = $_POST['answer'];
        $con = mysqli_connect("127.0.0.1", "root", "", "prepwp");
        $sql = "SELECT * FROM users WHERE user = '$username' LIMIT 1";
        $query = $con->query($sql);
        if ($query) {
            $row = mysqli_fetch_row($query);
            $name = $row[1];
            $userId = $row[0];
            $secretQuestion = $row[2];
            $secretAnswer = $row[3];
        }
        if ($secretAnswer == $ans) {  
            header("Location: home.php");
            // header("Location: home.html");
    
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
            <form method="post" action="secret.php">
                <input type="text" name="answer" class="form-control" placeholder="Enter answer">
                <br>
                <input type="submit" name="submit" value="Submit">
            </form>
        </div>
    </body>
</html>