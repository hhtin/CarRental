use master
go

DROP DATABASE CarRental
go

CREATE DATABASE CarRental
go

use CarRental
go

CREATE TABLE tblUser(
	email		nvarchar(100)	not null primary key,
	password	nvarchar(100)	not null,
	fullName	nvarchar(100)	not null,
	sex			bit				not null,
	phoneNumber	varchar(15)		not null,
	address		varchar(100)	not null,
	createDate	DATETIME		not null,
	role		nvarchar(10)	not null,
	status		bit				not null,
	verifyCode	nvarchar(20)	null,
) 

CREATE TABLE tblCar(
	carId		nvarchar(20)	not null primary key,
	carName		nvarchar(30)	not null,
	color		nvarchar(20)	not null,
	carImage	nvarchar(100)	not null,
	quantity	int				not null,
	price		float			not null,
	category	varchar(20)		not null,
	year		int				not null,
	status		bit				not null,
)

CREATE TABLE tblDiscount(
	discountID	nvarchar(20)	not null primary key,
	startDate	datetime		not null,
	endDate		datetime		not null,
)

CREATE TABLE tblFeedback(
	feedbackId		nvarchar(50)	not null primary key,
	feedbackContent	nvarchar(300)	not null,
	rating			int				not null,
	carId			nvarchar(20)	not null,
)

CREATE TABLE tblBooking(
	bookingId		nvarchar(20)	not null primary key,
	bookingDate		datetime		not null,
	totalPrice		float			not null,
	discountCode	nvarchar(20)	not null,
	endPrice		float			not null,
	email			nvarchar(100)	not null,
	discountID		nvarchar(20)	not null,
	status			bit				not null,
)

CREATE TABLE tblBookingDetail(
	bookingDetailId		nvarchar(20)	not null primary key,
	carId				nvarchar(20)	not null,
	quantity			nvarchar(20)	not null,
	rentalDate			datetime		not null,
	returnDate			datetime		not null,
	bookingId			nvarchar(20)	not null,
)

ALTER TABLE	tblBooking
ADD FOREIGN KEY(discountID) 
REFERENCES tblDiscount(discountID);

ALTER TABLE	tblBooking
ADD FOREIGN KEY(email) 
REFERENCES tblUser(email);

ALTER TABLE	tblBookingDetail
ADD FOREIGN KEY(bookingId) 
REFERENCES tblBooking(bookingId);

ALTER TABLE	tblBookingDetail
ADD FOREIGN KEY(carId) 
REFERENCES tblCar(carId);

ALTER TABLE	tblFeedback
ADD FOREIGN KEY(carId) 
REFERENCES tblCar(carId);

INSERT INTO tblUser(email,password,fullName,sex,phoneNumber,address,createDate,role,status,verifyCode)
VALUES ('tinhhse140856@fpt.edu.vn','856','Huynh Huu Tin',1,'0912700360','123 duong nguyen thi minh khai, binh thanh',convert(datetime,'10-01-21 09:30:01 PM',5),'admin',1,'a'),
		('thuannlse140855@fpt.edu.vn','855','Nguyen Le Thuan',1,'0912700361','512 duong nguyen thi minh khai, binh thanh',convert(datetime,'10-01-21 09:30:01 PM',5),'member',1,'a'),
		('longbvse140854@fpt.edu.vn','854','Bui Viet Long',1,'0912758431','965 duong nguyen thi minh khai, binh thanh',convert(datetime,'10-01-21 09:30:01 PM',5),'member',1,'a'),
		('datdtse140853@fpt.edu.vn','853','Do Trong Dat',1,'0918473125','847 duong nguyen thi minh khai, binh thanh',convert(datetime,'10-01-21 09:30:01 PM',5),'member',1,'a');

