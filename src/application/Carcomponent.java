package application;

import java.sql.Date;

public class Carcomponent {
	private String carId;
    private String brand;
    private String model;
    private String price;
    private String status;
    public String getCarId() {
		return carId;
	}


	public void setCarId(String carId) {
		this.carId = carId;
		
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
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


	public Date getDate() {
		return date;
	}

 
	public void setDate(Date date) {
		this.date = date;
	}


	private Date date;
   
	
    
    public Carcomponent(String carId, String brand, String model  , String price, String status, Date date){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.status = status;
        this.date = date;
        
    }
}