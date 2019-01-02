# February 19 In Class Notes

library(mice)
library(VIM)

data("nhanes")

raw_data <- nhanes

# Convert Age to Factor
raw_data$age <- as.factor(raw_data$age)

str(nhanes)
md.pattern(raw_data)

# Plot missing values
raw_plot <- aggr(raw_data, col=mdc(1:2), numbers=TRUE, sortVars=TRUE, labels=names(raw_data), cex.axis=.7, gap=3, ylab=c(":: of missingness","Pattern"))

# Marginplot: Specialized version of a Scatterplot
marginplot(raw_data[, c("chl", "bmi")], pch = 20)

# Imputing missing values using MICE
imputed_data <- mice(raw_data, m=30, maxit = 40)

# Method used for data imputation
# The default method is ppm (predictive mean matching)
imputed_data$method
imputed_data$imp$bmi

# Plot our imputed data
xyplot(imputed_data, bmi ~ chl | .imp, pch = 20, cex = 1.4)
densityplot(imputed_data)

# Fit a linear model on all the datasets
lm_model <- with(imputed_data, lm(chl~age+bmi+hyp))

# Pool together the data from all datasets to build a more robust combined model
combined_model <- pool(lm_model)
summary(combined_model)
