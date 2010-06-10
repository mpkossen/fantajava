          
function doBevestigen() {
 var bevestiging = confirm("Weet u zeker dat u wilt uitloggen?\nKlik op OK om uit te loggen.");
 
 if(bevestiging == true) {
	 return true;
}
else if(bevestiging == false) {
	return false;
}
}