<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=wdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}

	
	

$statement=$con->prepare("SELECT * FROM recipes");
$statement->execute();
$results=$statement->fetchAll(PDO::FETCH_ASSOC);

echo json_encode($results);
