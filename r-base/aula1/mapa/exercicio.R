# Install devtools, rmarkdown, knitr, testthat and Rcpp
# if not already installed.
install.packages(c("devtools", "rmarkdown", "Rcpp",
                   "knitr", "testthat"))

# Install the Suggested packages that are used by sits
install.packages(c("DBI","dendextend", "dtwclust",
                   "dtwSat", "e1071", "flexclust",
                   "imager", "imputeTS", "kohonen",
                   "lwgeom", "MASS", "mgcv", "nnet",
                   "proto", "proxy", "ptw", "ranger",
                   "RCurl", "RSQLite", "signal",
                   "xgboost", "zoo"))

# Install the Keras package from the RStudio repository
library(devtools)
install_github("rstudio/reticulate")
install_github("rstudio/keras")

# Build the keras environment
library(keras)
install_keras()

# Cria uma referencia ao servico WTSS do INPE
wtss <- sits_cube(type = "WTSS",
                  URL = "http://www.esensing.dpi.inpe.br/wtss",
                  name = "MOD13Q1")

# Obtem uma serie temporal nas coordenadas indicadas
ts1 <- sits_get_data(cube = wtss,
                     longitude = -55.57320,
                     latitude = -11.50566)
