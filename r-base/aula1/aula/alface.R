alfaces <- read.csv("~/Documentos/alface.csv", sep=";")
dolares <- read.csv("~/Documentos/dolar.csv", sep=";")

arow <- nrow(alfaces)
amean <- mean(alfaces$valor)
amedian <- median(alfaces$valor)
asd <- sd(alfaces$valor)
acv <- (asd/amean)*100
aam <- asd/sqrt(arow)

qqPlot(alfaces$valor)
boxplot(alfaces$valor~alfaces$semanaano,alfaces$ano, xlab = 'dia', ylab='valor', main='Tabela',data = alfaces)
