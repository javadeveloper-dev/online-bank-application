


select * from banking_app.admin_register;

alter table banking_app.admin_register modify profile_photo mediumblob;

desc banking_app.admin_register;
truncate table banking_app.admin_register; where id=2;

delete * from banking_app.admin_register where id=27;