produtos <- read.csv("~/Documentos/produtos.csv", sep=";")
dolar <- read.csv("~/Documentos/dolar.csv", sep=";")

--------------
install.packages("plyr")
library(plyr)
View(produtos)
order(count(produtos, 'produto')$freq)
--------------
  
produtos210 <- produtos[which(produtos$ano == 2016),names(produtos) %in% c("diaano","valor")]

hist(produtos210$diaano)
hist(dolar$valor)

proDia <- subset(produtos210, select = c("diaano","valor"))
proDia$diaano <- as.factor(proDia$diaano)

str(proDia)

boxplot(proDia$valor~proDia$diaano, xlab = 'dia', ylab='valor', main='Tabela',data = proDia)
boxplot(dolar$valor~dolar$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = dolar)

summary(proDia)

linha = lm(proDia$valor ~ proDia$dia, data=proDia)

plot(linha)

View(proDia)

hist(proDia)

tabelaDia = aggregate(x = produtos$valor, by = list(produtos$dia), FUN = mean)



boxplot(tabelaDia$x~tabelaDia$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaDia)
plot(tabelaDia$x~tabelaDia$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaDia)

tabelaBairro = aggregate(x = produtos$valor, by = list(produtos$bairro), FUN = mean)

boxplot(tabelaBairro$x~tabelaBairro$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaBairro)

print(tabela)
plot(tabela)

linha = lm(tabelaDia$Group.1 ~tabelaDia$x, data=tabelaDia)

boxplot(linha)