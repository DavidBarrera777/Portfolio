<?php
// Step 1: Connect to the MySQL database
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "financeportal";

$conn = mysqli_connect($servername, $username, $password, $dbname);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

// Step 2: Process Form Data
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve input values
    $rent = $_POST['rent'];
    $light = $_POST['light'];
    $groceries = $_POST['groceries'];
    $water = $_POST['water'];
    $gas = $_POST['gas'];
    $internet = $_POST['internet'];
    $phone = $_POST['phone'];
    $propertyTaxes = $_POST['propertyTaxes'];
    $creditCards = $_POST['creditCards'];
    $healthInsurance = $_POST['healthInsurance'];
    $carInsurance = $_POST['carInsurance'];
    $carPayments = $_POST['carPayments'];

    // Sanitize input values (prevent SQL injection)
    $rent = mysqli_real_escape_string($conn, $rent);
    $light = mysqli_real_escape_string($conn, $light);
    $groceries = mysqli_real_escape_string($conn, $groceries);
    $water = mysqli_real_escape_string($conn, $water);
    $gas = mysqli_real_escape_string($conn, $gas);
    $internet = mysqli_real_escape_string($conn, $internet);
    $phone = mysqli_real_escape_string($conn, $phone);
    $propertyTaxes = mysqli_real_escape_string($conn, $propertyTaxes);
    $creditCards = mysqli_real_escape_string($conn, $creditCards);
    $healthInsurance = mysqli_real_escape_string($conn, $healthInsurance);
    $carInsurance = mysqli_real_escape_string($conn, $carInsurance);
    $carPayments = mysqli_real_escape_string($conn, $carPayments);

    // Retrieve user ID from session
    session_start();
    if(isset($_SESSION['user_id'])) {
        $user_id = $_SESSION['user_id'];
    } else {
        // Redirect to login page or handle the case where user is not logged in
        exit("User not logged in");
    }

    // Insert data into the expenses table
    $sql = "INSERT INTO expenses (user_id, rent, light, groceries, water, gas, internet, phone, property_taxes, credit_cards, health_insurance, car_insurance, car_payments) 
            VALUES ('$user_id', '$rent', '$light', '$groceries', '$water', '$gas', '$internet', '$phone', '$propertyTaxes', '$creditCards', '$healthInsurance', '$carInsurance', '$carPayments')";

    if (mysqli_query($conn, $sql)) {
        echo "Data inserted successfully";
    } else {
        echo "Error: " . $sql . "<br>" . mysqli_error($conn);
    }
}

// Step 3: Close Connection
mysqli_close($conn);
?>
