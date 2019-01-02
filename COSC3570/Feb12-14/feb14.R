#February 14 In Class Work

library(mice)

# fetch Boston Housing mlbench data
data(BostonHousing, package='mlbench')
test_data <- BostonHousing
org_data <- BostonHousing

md.pattern(org_data)

# randomly remove some data
test_data[sample(1:nrow(test_data), 40), 'rad'] <- NA
test_data[sample(1:nrow(test_data), 40), 'ptratio'] <- NA

md.pattern(test_data)

# get mean of not missing data
rad_mean <- mean(test_data$rad, na.rm=TRUE)
ptr_mean <- mean(test_data$ptratio, na.rm=TRUE)

# get median of not missing data
rad_median <- median(test_data$rad, na.rm=TRUE)
ptr_median <- median(test_data$ptratio, na.rm=TRUE)

# replace missing data with mean/median
#test_data$ptratio[is.na(test_data$ptratio)] <- ptr_mean
#test_data$rad[is.na(test_data$rad)] <- rad_mean
test_data$ptratio[is.na(test_data$ptratio)] <- ptr_median
test_data$rad[is.na(test_data$rad)] <- rad_median

# compare the originals to the test data
summary(org_data$rad)
summary(test_data$rad)
summary(org_data$ptratio)
summary(test_data$ptratio)