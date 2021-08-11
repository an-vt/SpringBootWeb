create database ShopBanHang;

create table shopbanhang.user(
	id int not null primary key auto_increment,
	name varchar(255),
    age int,
    role varchar(255),
    enabled boolean,
    username varchar(255),
    password varchar(255),
    address varchar(255),
    gender varchar(255),
    phone varchar(255),
    email varchar(255),
    avatar varchar(255)
);

create table shopbanhang.category(
	id int not null primary key auto_increment,
	name varchar(255)
);

create table shopbanhang.product(
	id int not null primary key auto_increment,
	name varchar(255),
    quantity int,
    price long,
    image varchar(255),
    description varchar(255),
    id_category int,
    FOREIGN KEY (id_category) REFERENCES category(id)
);

create table shopbanhang.bill(
	id int not null primary key auto_increment,
	id_user int,
    buy_date date,
    price_total long,
    coupon varchar(255),
    coupon_present int,
    status varchar(255),
    FOREIGN KEY (id_user) REFERENCES user(id)
);

create table shopbanhang.billProduct(
	id int not null primary key auto_increment,
    unit_price int,
	quantity int,
    id_bill int,
    id_product int,
    FOREIGN KEY (id_bill) REFERENCES bill(id),
    FOREIGN KEY (id_product) REFERENCES product(id)
);

create table shopbanhang.billProduct(
	id int not null primary key auto_increment,
    unit_price long,
	quantity int,
    id_bill int,
    id_product int,
    FOREIGN KEY (id_bill) REFERENCES bill(id),
    FOREIGN KEY (id_product) REFERENCES product(id)
);

create table shopbanhang.coupon(
	id int not null primary key auto_increment,
    code varchar(255),
    present int
);

create table shopbanhang.useraddress(
	id int not null primary key auto_increment,
	name varchar(255),
    phone_number varchar(255),
    city varchar(255),
    district varchar(255),
    address varchar(255),
    user_id int,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

create table shopbanhang.comment(
	id int not null primary key auto_increment,
	content varchar(255),
    createdDate date,
    user_id int,
    product_id int,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

create table shopbanhang.review(
	id int not null primary key auto_increment,
	star_number int,
    review_date date,
    user_id int,
    product_id int,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

SET FOREIGN_KEY_CHECKS=0; DROP TABLE shopbanhang.review; SET FOREIGN_KEY_CHECKS=1;