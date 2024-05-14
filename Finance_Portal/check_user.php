<?php
// Establish database connection
$host = "localhost";
$dbusername = "root";
$dbpassword = "";
$dbname = "financeportal";

$conn = new mysqli($host, $dbusername, $dbpassword, $dbname);

if (mysqli_connect_error()) {
    die('ConnectError(' . mysqli_connect_errno() . ')' . mysqli_connect_error());
}

// Receive username and password
$username = $_POST['username'];
$password = $_POST['password'];

// Check if the provided username and password combination exists
$sql = "SELECT * FROM user_info WHERE username='$username' AND password='$password'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Username and password combination exists, redirect to MAIN_MENU.html
    echo "success";
} else {
    // Username and password combination does not exist, return failure
    echo json_encode(array("username" => "Incorrect username or password", "password" => ""));;
}

// Close database connection
$conn->close();
?>
