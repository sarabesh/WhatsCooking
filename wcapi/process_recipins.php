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
		$stmt = $con->prepare("SELECT * FROM recipes WHERE rid LIKE :id");
		$stmt->bindParam(':id', $id, PDO::PARAM_STR);
		$stmt->execute(); 

		$response = array();
		if($stmt->rowCount() >= 1){
				$id=randomGen();
				return $id;
		}
		return $id;    
}

		


		
		$dish=$_POST['dish'];
		$rec=$_POST['recipe'];
		$uid=$_POST['uid'];
		$i=randomGen();
		
		    $sql = 'INSERT INTO recipes 
		    VALUES (:rid,:uid,:dish,:recipe)';    
		    $query = $con->prepare($sql);
		  
		    $query->execute(array(
		    	':rid'=>$i,
		    ':uid' =>$uid,
		    ':dish'=>$dish,
		    ':recipe' =>$rec,
		    		    

		    ));
		    $response=array();
		    $code = "ins_success";
			$message = "successfuly inserted";
			array_push($response, array("code"=>$code, "message"=>$message));
			echo json_encode($response);
		    
	   
?>