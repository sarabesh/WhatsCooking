<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=wdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}
	
		
		
		$stmt = $con->prepare("SELECT * FROM recipes");
		$stmt->bindParam(':id', $id, PDO::PARAM_STR);
		
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() >= 1){
			$response=$stmt->fetchAll(PDO::FETCH_ASSOC);
			//echo "present";

			echo json_encode($response);
		


		}
		else {
			
		    $code = "new";
			$message = "no recipes yet";
			array_push($response, array("code"=>$code, "message"=>$message));
			header('Content-Type: application/json');
			echo json_encode($response);

		    }
	
	
?>