wins = 0;
N = 100;

for i = 1:N
    if game
        wins = wins + 1;
    end
end

winRate = wins/N
lost = N - wins
earned = wins * 10
profit = earned - spent

function won = game()
    heads = 0;
    for i = 1:4
        if flipCoin == "Head"
            heads = heads + 1;
        end
    end
    won = heads == 3;
end

function side = flipCoin()
    if rand > 0.5
        side = "Head";
    else
        side = "Tail";
    end
end