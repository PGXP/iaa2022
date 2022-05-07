produtos <- read.csv("~/Documentos/produtos.csv", sep=";")

--------------
install.packages("plyr")
library(plyr)
View(produtos)
order(count(produtos, 'produto')$freq)
--------------
  
produtos210 <- produtos[which(produtos$produto == 210),names(produtos) %in% c("dia","valor")]

hist(produtos210$dia)

proDia <- subset(produtos210, select = c("dia","valor"))
proDia$dia <- as.factor(proDia$dia)

str(proDia)

boxplot(proDia$valor~proDia$dia, xlab = 'dia', ylab='valor', main='Tabela',data = proDia)

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