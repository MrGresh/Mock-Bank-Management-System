create table personalDetail(
                               id bigint,
                               pincode bigint,
                               name varchar(255),
                               dob varchar(255),
                               gender varchar(255),
                               email varchar(255),
                               address varchar(255),
                               city varchar(255),
                               state varchar(255)
);

ALTER TABLE personalDetail ADD card bigint;
ALTER TABLE personalDetail ADD pin bigint;
ALTER TABLE personalDetail ADD pan varchar(10);
ALTER TABLE personalDetail ADD aadhar bigint;

create table bank(
                     id bigint,
                     date varchar(255),
                     amount bigint,
                     type varchar(255)
);

ALTER TABLE bank ADD balance bigint;

create sequence hibernate_sequence;