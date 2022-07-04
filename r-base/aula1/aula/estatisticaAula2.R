install.packages("psych")
library(psych)

alfaces <- read.csv("~/Documentos/alface.csv", sep=";")
alfaces$data <- ISOdate(alfaces$ano, alfaces$mes, alfaces$dia)

View(alfaces)
str(alfaces)
summary(alfaces$valor)

hist(alfaces$valor, main = 'Histograma', xlab = 'Valores')

arow <- nrow(alfaces)
amean <- mean(alfaces$valor)
amedian <- median(alfaces$valor)
asd <- sd(alfaces$valor)
acv <- (asd/amean)*100
aam <- asd/sqrt(arow)

cor(alfaces[c("ano", "mes", "dia","semana","semanaano","diaano", "idfilial", "idbairro", "idlocal", "valor", "tempmin", "umidademax", "amplitude")])

pairs(alfaces[c("ano", "mes", "dia","semana","semanaano","diaano", "idfilial", "idbairro", "idlocal", "valor", "tempmin", "umidademax", "amplitude")])

modelo <- lm(valor ~ tempmin, data = alfaces)

boxplot(alfaces$valor~alfaces$data, xlab = 'dia', ylab='valor', main='Tabela',data = alfaces)