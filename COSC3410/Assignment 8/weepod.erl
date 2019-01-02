-module(weepod).
-export([start/0, send_wait/2, remove_item/2]).

%%
% COSC 3410, HW #8
%
% Liam Fruzyna
%%

start() -> spawn(fun init/0).

init() ->
  put({item, lettuce}, 1.50),
  put({item, cupcake}, 0.75),
  put({item, soup}, 2.35),
  put(cart, []),
  loop().

add_items(Name, Count, []) -> [{Name, Count}];
add_items(Name, Count, [{Name, C}|L]) -> [{Name, C+Count}|L];
add_items(Name, Count, [H|L]) -> [H|add_items(Name, Count, L)].

% helper function that removes a given item from a given list
remove_item(Name, [{Name, Count} | Rest]) -> Rest;
remove_item(Name, [H|L]) -> [H|remove_item(Name, L)].

% helper function that returns the price of a single item
get_price(Name, []) -> 0;
get_price(Name, [{Name, Price} | Rem]) -> Price;
get_price(Name, [H|L]) -> get_price(Name, L).

% helper funtion that returns the combined price of all items in a given list
add_total([]) -> 0;
add_total([{Name, Count} | Rest]) -> Count * get_price(Name, shelf_list()) + add_total(Rest).

shelf_list() ->
  K = get_keys(),
  [{N,get(E)} || {item, N}=E <- K].

loop() ->
  receive
    {From, show_shelves} ->
      From ! {self(), shelf_list()}, loop();
    {From, {choose, Name, Count}} -> 
      put(cart, add_items(Name, Count, get(cart))),
      From ! {self(), ok}, loop();
    {From, {add_item, Name, Price}} ->
      put({item, Name}, Price),
      From ! {self(), ok}, loop();
    {From, {remove, Name}} ->
      put(cart, remove_item(Name, get(cart))),
      From ! {self(), ok}, loop();
    {From, show_basket} ->
      From ! {self(), get(cart)}, loop();
    {From, {price_check, Name}} ->
      From ! {self(), get_price(Name, shelf_list())}, loop();
    {From, checkout} ->
      From ! {self(), add_total(get(cart))}, loop();
    {From, Other} ->
      From ! {self(), {error, Other}}, loop()
  end.

% core client function
send_wait(Pid, Request) ->
  Pid ! {self(), Request},
  receive
    {Pid, Response} -> Response
  end.