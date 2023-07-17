<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
			<h3>To View all the Candidate Details</h3>
			<a href="candidates">Click here</a> <br>


			<h3> Select Canidates by Name</h3>
			<form action=getCandidateByName>
			<input type="text" name="name"> <br>
			<input type="submit"> <br>
			</form>
			<h3> Select Canidates by Gender</h3>
			<form action="getCandidateByGender">
			<input type="text" name="gender"> <br>
			<input type="submit"> <br>
			</form>
			<h3> Select Canidates by Mobile Number</h3>
			<form action="getCandidateByMobile">
			<input type="text" name="mobile"> <br>
			<input type="submit"> <br>
			</form>
			<h3> Select Canidates by Experience</h3>
			<form action="getCandidateByExperience">
			<input type="text" name="experience"> <br>
			<input type="submit"> <br>
			</form>
			<h3> Select Canidates by Qualification</h3>
			<form action="getCandidateByQualification">
			<input type="text" name="qualification"> <br>
			<input type="submit"> <br>
			</form>
			<h3> Select Canidates by Branch</h3>
			<form action="getCandidateByBranch">
			<input type="text" name="branch"> <br>
			<input type="submit"> <br>
			</form><h3> Select Canidates by Percentage</h3>
			<form action="getCandidateByPercentage">
			<input type="text" name="percentage"> <br>
			<input type="submit"> <br>
			</form><h3> Select Canidates by PrimarySkill</h3>
			<form action="getCandidateByPrimarySkill">
			<input type="text" name="primarySkill"> <br>
			<input type="submit"> <br>
			
			</form><h3> Select Canidates Experience Greater Than</h3>
			<form action="getExperienceGreaterThan">
			<input type="text" name="experience"> <br>
			<input type="submit"> <br>
			
			</form><h3> Select Canidates Percentage Greater Than</h3>
			<form action="getPercentageGreaterThan">
			<input type="text" name="percentage"> <br>
			<input type="submit"> <br>
			
			
			
			</form><h3> Select Canidates by PrimarySkill Sorted order by Experience</h3>
			<form action="getPrimarySkillSorted">
			<input type="text" name="primarySkill"> <br>
			<input type="submit"> <br>
			
			
			</form>
		
			

</body>
</html>