<?php
$email = filter_input(INPUT_POST, 'email');
$username = filter_input(INPUT_POST, 'username');
$password = filter_input(INPUT_POST, 'password');

if (!empty($username) && !empty($password) && !empty($email)) {
    $host = "localhost";
    $dbusername = "root";
    $dbpassword = "";
    $dbname = "financeportal";

    // Create Connection
    $conn = new mysqli($host, $dbusername, $dbpassword, $dbname);

    if (mysqli_connect_error()) {
        die('ConnectError(' . mysqli_connect_errno() . ')' . mysqli_connect_error());
    } else {
        // Check if username or email already exist in the database
        $sql = "SELECT * FROM user_info WHERE username='$username' OR email='$email'";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            // Username or email already exist, display an error message
            echo "Username or email already exist!";
        } else {
            // Insert new record into the database
            $sql_insert = "INSERT INTO user_info (username, password, email) VALUES ('$username', '$password', '$email')";
            if ($conn->query($sql_insert)) {
                echo "New record inserted successfully!!!!";

                // Redirect to another HTML file
                header("Location: MAIN_MENU.html");
                exit();
            } else {
                echo "Error:" . $sql_insert . "<br>" . $conn->error;
            }
        }
        $conn->close();
    }
} else {
    echo "Username, password, and email should not be empty!";
    die();
}
?>
