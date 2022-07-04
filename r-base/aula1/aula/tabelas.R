produtos <- read.csv("~/Documentos/tabelas.csv", sep=";")

View(produtos)
str(produtos)
summary(produtos$valor)

hist(produtos$valor, main = 'Histograma', xlab = 'Valores')