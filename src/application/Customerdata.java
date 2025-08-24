package application;

public class Customerdata 
{
   private String firstName;
   private String lastName;
   private String gender;
   private String carId;
   private String price;
   private String status;

   private String dateRetened;
   private String dateReturn;
   
   
   public Customerdata(String firstName, String lastName, String gender, String carId, String price, String status,
		String dateRetened, String dateReturn) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.gender = gender;
	this.carId = carId;
	this.price = price;
	this.status = status;
	this.dateRetened = dateRetened;
	this.dateReturn = dateReturn;
   }

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getCarId() {
	return carId;
}

public void setCarId(String carId) {
	this.carId = carId;
}

public String getPrice() {
	return price;
}

public void setPrice(String price) {
	this.price = price;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getDateRetened() {
	return dateRetened;
}

public void setDateRetened(String dateRetened) {
	this.dateRetened = dateRetened;
}

public String getDateReturn() {
	return dateReturn;
}

public void setDateReturn(String dateReturn) {
	this.dateReturn = dateReturn;
}
   
   
}
