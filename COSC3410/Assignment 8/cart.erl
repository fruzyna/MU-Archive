-module(cart).
-export([start/0, show_shelves/1, add_item/3, choose/3, remove/2, show_cart/1, checkout/1, price_check/2]).

%%
% COSC 3410, HW #8
%
% Liam Fruzyna
%
% cart.erl, expands calls out into easier functions
%%

start() -> weepod:start().

show_shelves(Pid) -> weepod:send_wait(Pid, show_shelves).

add_item(Pid, Name, Price) -> weepod:send_wait(Pid, {add_item, Name, Price}).

choose(Pid, Name, Count) -> weepod:send_wait(Pid, {choose, Name, Count}).

remove(Pid, Name) -> weepod:send_wait(Pid, {remove, Name}).

show_cart(Pid) -> weepod:send_wait(Pid, show_basket).

checkout(Pid) -> weepod:send_wait(Pid, checkout).

price_check(Pid, Name) -> weepod:send_wait(Pid, {price_check, Name}).