insert into testtable1 (field1, field2)
values
(1, 'one'),
(2, 'two'),
(3, 'three'),
(4, 'four'),
(5, 'five');

select * from testtable1;

insert into testtable1 (field1, field2)
values
    (6, 'six')

select field2, field1, 'fiexed value' as fixxedd from testtable1;
