<?php
    session_start();
    $username = $_SESSION['name'];
    $newunicast = $_SESSION['newunicast'];
    $con = mysqli_connect("127.0.0.1", "root", "", "prepwp");

    $sql = "SELECT * FROM messages WHERE receivers like '%" . $username . "%'";

    $result = mysqli_query($con, $sql);

    echo '<h1>New messages here</h1>';

    while($row = mysqli_fetch_array($result, MYSQLI_NUM))
    {   
        if($row[2] == "multicast") {
            echo '<p>' . $row[3] . '</p>';
            $newviews = $row[5] . ' ' . $username;
            $sql2 = "UPDATE messages set views = '" . $newviews . "' where id = " . $row[0] . " and (views not like '%" . $username . "%' or views is null)";
            $con->query($sql2);
        }
        else {
            array_push($newunicast, $row[3]);
            $sql3 = "delete from messages where id = " . $row[0] . "";
            $con->query($sql3);
        }
    }
    $_SESSION['newunicast'] = $newunicast;
    foreach($newunicast as $msg) {
        echo "<p>" . $msg . "</p>";
    }

    $con->close();
?>