abacaxis <- read.csv("~/Documentos/abacaxi.csv", sep=";")


arow <- nrow(abacaxis)
amean <- mean(abacaxis$valor)
amedian <- median(abacaxis$valor)
asd <- sd(abacaxis$valor)

# O coeficiente de variação
acv <- (asd/amean)*100

aam <- asd/sqrt(arow)
