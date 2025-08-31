
select * from banking_app.admin_register;
use banking_app;
show tables;
alter table banking_app.admin_register modify profile_photo mediumblob;
select * from banking_app.user_register;
desc banking_app.admin_register;

truncate table banking_app.admin_register; where id=2;

delete * from banking_app.admin_register where id=27;
