https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=30&isVerify=1&pageNo=1  dlt历史

https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=35&provinceId=0&pageSize=30&isVerify=1&pageNo=1 pls历史

https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=350133&provinceId=0&pageSize=30&isVerify=1&pageNo=1  plw历史

https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=04&provinceId=0&pageSize=30&isVerify=1&pageNo=1  qxc历史


drop table IF EXISTS t_caipiao_history;
drop table IF EXISTS t_caipiao_prize_level_list; 
drop table IF EXISTS t_caipiao_number；
drop table IF EXISTS t_caipiao_user;


CREATE TABLE public.t_caipiao_history(  
	lottery_game_name varchar,
	lottery_draw_num VARCHAR,
	lottery_draw_time varchar,
	lottery_draw_result VARCHAR , 
	pool_balance_after_draw varchar,
	total_sale_amount varchar, 
	prize_level_list varchar,
	
	PRIMARY KEY (lottery_draw_num, lottery_game_name)
)
WITH (
    OIDS = FALSE
);

comment on table public.t_caipiao_history is '彩票历史表'; 
comment on column public.t_caipiao_history.lottery_game_name is '类型'; 
comment on column public.t_caipiao_history.lottery_draw_num is '期数'; 
comment on column public.t_caipiao_history.lottery_draw_time is '开奖日期'; 
comment on column public.t_caipiao_history.lottery_draw_result is '开奖结果';  
comment on column public.t_caipiao_history.pool_balance_after_draw is '奖池奖金';  
comment on column public.t_caipiao_history.total_sale_amount is '销售额';  
comment on column public.t_caipiao_history.prize_level_list is '中奖情况';   

CREATE TABLE public.t_caipiao_prize_level_list(
	lottery_game_name varchar,
	lottery_draw_num VARCHAR,
	prize_level VARCHAR , 
	stake_amount varchar,
	stake_count varchar, 
	total_prize_amount varchar,  
	PRIMARY KEY (lottery_game_name, lottery_draw_num, prize_level)
)
WITH (
    OIDS = FALSE
);

comment on table public.t_caipiao_prize_level_list is '彩票历史表'; 
comment on column public.t_caipiao_prize_level_list.lottery_game_name is '类型'; 
comment on column public.t_caipiao_prize_level_list.lottery_draw_num is '期数'; 
comment on column public.t_caipiao_prize_level_list.prize_level is '奖项';  
comment on column public.t_caipiao_prize_level_list.stake_amount is '基本奖金';  
comment on column public.t_caipiao_prize_level_list.stake_count is '基本注数';  
comment on column public.t_caipiao_prize_level_list.total_prize_amount is '总奖金数';   

CREATE TABLE public.t_caipiao_number(
	id character varying(64) not null primary key, 
	lottery_game_name varchar,
	lottery_number varchar,
	lottery_draw_num VARCHAR,
	term_num integer,
	multiple integer,
	user_id varchar
)
WITH (
    OIDS = FALSE
);

comment on table public.t_caipiao_number is '彩票投注表'; 
comment on column public.t_caipiao_number.id is 'ID'; 
comment on column public.t_caipiao_number.lottery_game_name is '类型'; 
comment on column public.t_caipiao_number.lottery_number is '号码'; 
comment on column public.t_caipiao_number.lottery_draw_num is '开始期数';  
comment on column public.t_caipiao_number.term_num is '期数';  
comment on column public.t_caipiao_number.multiple is '倍数';   
comment on column public.t_caipiao_number.user_id is '用户ID';   



CREATE TABLE public.t_caipiao_user(
	id character varying(64) not null primary key, 
	name varchar,
	phone_number VARCHAR 
)
WITH (
    OIDS = FALSE
);

comment on table public.t_caipiao_user is '彩票用户表'; 
comment on column public.t_caipiao_user.id is 'ID'; 
comment on column public.t_caipiao_user.name is '用户名'; 
comment on column public.t_caipiao_user.phone_number is '手机号码';   