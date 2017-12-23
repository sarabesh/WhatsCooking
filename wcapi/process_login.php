<?php
	try {
	    $con = new PDO('mysql:host=localhost;dbname=wdb;charset=utf8mb4','root', '');
	    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch (PDOException $e){
	    exit($e->getMessage());
	}

	 
		$un = $_POST['un'];
		$pswd = $_POST['pwd'];
		
		$stmt = $con->prepare("SELECT * FROM user WHERE username= :username AND password= :password");
		$stmt->bindParam(':username', $un, PDO::PARAM_STR);
		$stmt->bindParam(':password', $pswd, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() == 1){
			$res= $stmt -> fetch(); 
		    $code = "login_success";
			$message = "Welcome ";
			array_push($response, array("code"=>$code, "message"=>$message, "uname"=>$res['username'], "pass"=>$res['password'],"email"=>$res['email'],"id"=>$res['id']));
			//echo json_encode($response);
		

			echo json_encode($response);

		}
		else {
		    $code = "login_failed";
			$message = "Username or password is wrong.";
			array_push($response, array("code"=>$code, "message"=>$message));
			header('Content-Type: application/json');
			echo json_encode($response);

		    }
	
	

