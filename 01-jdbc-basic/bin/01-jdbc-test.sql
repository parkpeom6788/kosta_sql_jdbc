select id,password,name,address from member;
-- insert 
-- sql은 무조건 싱글
insert into member(id,password,name,address) values('test','a','엔젤','오리');

-- id가 jdbc인 member 의 name과 address 를 조회
select name , address from member where id = 'jdbc';

-- update test
-- id 가 spring 인 회원의 address 를 미금에서 오리로 수정하고자 한다 
update member set address = '오리' where id='spring';
