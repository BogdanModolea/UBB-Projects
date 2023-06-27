<?php
    session_start();
    
    $username = $_SESSION['name'];
    $con = mysqli_connect("127.0.0.1", "root", "", "prepwp");

    $newunicast = array();
    $_SESSION['newunicast'] = $newunicast;


    if (isset($_POST['submit'])) {
        $type = $_POST['type'];
        $msg = $_POST['msg'];
        $receivers = $_POST['receivers'];

        $sql = "SELECT MAX(id) FROM prepwp.messages";

        $myid = mysqli_query($con, $sql);
        $maxid = $myid->fetch_array()['MAX(id)'] ?? 0;

        if($type == "multicast") {
            $sql = "INSERT INTO prepwp.messages(id, sender, type, text, receivers) VALUES(" . $maxid + 1 . ", " . "'" . $username . "' " . ", '" . $type . "', '" . $msg . "', '" . $receivers . "')";
            mysqli_query($con, $sql);
        }
        else {
            $sql = "INSERT INTO prepwp.messages(id, sender, type, text, receivers) VALUES(" . $maxid + 1 . ", " . "'" . $username . "' " . ", '" . $type . "', '" . $msg . "', '" . $receivers . "')";
            mysqli_query($con, $sql);
        }
        
    }


    $con->close();
?>


<!DOCTYPE html>
<html>
    <body>
        <div id ="demo"></div>
        <div>
            <h2>Send a message</h2>
            <form method="post" action="home.php">
                <input type="text" name="type" class="form-control" placeholder="Enter type">
                <br>
                <input type="text" name="msg" class="form-control" placeholder="Enter text">
                <br>
                <input type="text" name="receivers" class="form-control" placeholder="Enter receivers">
                <br>
                <input type="submit" name="submit" value="Send">
            </form>
        </div>

        <script>
            function loadDoc() {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                };
                xhttp.open("GET", "load.php", true);
                xhttp.send();
            }

            setInterval(function(){ 
                loadDoc();
            }, 1000);
        </script>
    </body>
</html>