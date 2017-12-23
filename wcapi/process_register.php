<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=wdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}


		 function randomGen()
		  { 
		  	try {
	    $con = new PDO('mysql:host=localhost;dbname=wdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}
		  $id=random_int(0, 1000);
		$stmt = $con->prepare("SELECT * FROM user WHERE id LIKE :id");
		$stmt->bindParam(':id', $id, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() >= 1){
				$id=randomGen();
				return $id;
		}
		return $id;    
}

		$name=$_POST['name'];
		$password=$_POST['pass'];
		$email=$_POST['email'];
		
		$stmt = $con->prepare("SELECT * FROM user WHERE username LIKE :name");
		$stmt->bindParam(':name', $name, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() == 1){
		    $code = "reg_failed";
			$message = "Username already exists.";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		}else {
		    $sql = 'INSERT INTO user VALUES (:name,:email,:pwsd,:id)';    
		    $query = $con->prepare($sql);
		    $i=randomGen();
		    $query->execute(array(
		    	':id'=>$i,
		    ':name' => $name,
		    ':pwsd'=>$password,
		    ':email' => $email,
		  

		    ));
		    
		    $code = "reg_success";
			$message = "Thank you for signing up.Login now!";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		    }
	   
?>