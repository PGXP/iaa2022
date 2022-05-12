produtos <- read.csv("~/Documentos/produtos.csv", sep=";")
dolar <- read.csv("~/Documentos/dolar.csv", sep=";")

--------------
install.packages("plyr")
library(plyr)
View(produtos)
order(count(produtos, 'produto')$freq)
--------------
 
produtos210 <- produtos[which(produtos$ano == 2014&produtos$produto==91),names(produtos) %in% c("semanaano","valor")]
proDia <- subset(produtos210, select = c("semanaano","valor"))
boxplot(proDia$valor~proDia$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = proDia)

produtos210 <- produtos[which(produtos$ano == 2014&produtos$produto==296&produtos$mercado==2&produtos$bairro==13),names(produtos) %in% c("semanaano","valor")]

hist(produtos210$semanaano)
hist(dolar$valor)


proDia$diaano <- as.factor(proDia$diaano)

str(proDia)

boxplot(proDia$valor~proDia$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = proDia)
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