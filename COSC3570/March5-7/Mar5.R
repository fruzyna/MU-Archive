# March 5 In Class Notes

library(mice)
library(VIM)

raw_data <- read.csv('SLID.csv')
summary(raw_data)

# View which data is missing
md.pattern(raw_data)
missingness_pot <- aggr(raw_data, numbers=TRUE, sortVars=TRUE, labels=names(raw_data), cex_axis=1.5, gap=3)

# Impute the missing data based on non-missing data
# Build 10 complete datasets, 30 times
imputed_data <- mice(raw_data, m=10, maxit=30)

# Compare to the original data
xyplot(imputed_data, education ~ wages | .imp, pch=20, cex=1.4)
densityplot(imputed_data)