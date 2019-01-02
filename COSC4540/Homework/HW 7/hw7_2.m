% 2) Using a web of pages, we will click on a link with probability 0.85
%    and go to another page with probability 0.15.
%    How many times is each page visited?

% Web:
% 1 > 4
% 2 > 1
% 3 > 1
% 4 > 2, 3, 5
% 5 > 3

% build connectivity matrix and compute for part a
sites = 5;
connectivity = zeros(sites,sites);
connectivity(1, :) = [0 0 0 1 0];
connectivity(2, :) = [1 0 0 0 0];
connectivity(3, :) = [1 0 0 0 0];
connectivity(4, :) = [0 1 1 0 1];
connectivity(5, :) = [0 0 1 0 0];
pageRank(connectivity, sites)

% build connectivity matrix and compute for part b
sites = 6;
connectivity = zeros(sites,sites);
connectivity(1, :) = [0 0 0 1 0 0];
connectivity(2, :) = [1 0 0 0 0 0];
connectivity(3, :) = [1 0 0 0 0 0];
connectivity(4, :) = [0 1 1 0 1 0];
connectivity(5, :) = [0 0 1 0 0 1];
connectivity(6, :) = [0 0 0 0 0 0];
pageRank(connectivity, sites)

% compute the page ranks with a connectivity matrix
function[prob] = pageRank(connectivity, sites)
    % constant variables
    n = 5000;
    stayOdds = 0.85;
    randOdds = (1 - stayOdds) / sites;
    
    % build a pdf and cdf matrix for the pages
    pdf = zeros(sites,sites);
    cdf = zeros(sites,sites);
    for i=1:sites
        % counds the total links
        count = sum(connectivity(i, :));
        if count == 0
            count = 1;
        end
        % pdf is odds of selecting a given page
        pdf(i, :) = (connectivity(i, :) / count) * stayOdds + randOdds;
        % cdf is odds of clicking a link or one before it
        % used to get the range of random number that is clicking the link
        cdf(i, 1) = pdf(i, 1);
        for j=2:sites
            cdf(i, j) = cdf(i, j-1) + pdf(i, j);
        end
    end

    % compute the page rank
    visits = zeros(1,sites);
    page = 1;
    for i=1:n
        % generate a random number and use that to click a link on the
        % current page
        odds = rand(1);
        for j=1:sites
            if odds < cdf(page, j)
                page = j;
                break;
            end
        end
        visits(page) = visits(page) + 1;
    end
    % compute probability of each page
    prob = visits / n;
end