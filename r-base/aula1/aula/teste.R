produtos <- read.csv("~/Documentos/produtos.csv", sep=";")
dolar <- read.csv("~/Documentos/dolar.csv", sep=";")
clima <- read.csv("~/Documentos/clima.csv", sep=";")
ipca <- read.csv("~/Documentos/ipca.csv", sep=";")

summary(produtos)
--------------
install.packages("plyr")
install.packages("ggplot2")
library(plyr)
library(ggplot2)
View(produtos)
View(ipca)
View(clima)
order(count(produtos, 'produto')$freq)
--------------

clima2019 <- clima[which(clima$ano == 2014),names(clima)%in% c("semanaano","tempmin")]
climaDiff <- clima[which(clima$ano == 2014),names(clima)%in% c("semanaano","amplitude")]
dolar2019 <- dolar[which(dolar$ano == 2014),names(dolar)%in% c("semanaano","valor")]
produto210 <- produtos[which(produtos$produto==475&produtos$ano==2014),names(produtos) %in% c("semanaano","valor")]
grupo15 <- produtos[which(produtos$grupo==15),names(produtos) %in% c("semanaano","valor")]

boxplot(produto210$valor~produto210$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = produto210)
boxplot(dolar2019$valor~dolar2019$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = dolar2019)
boxplot(clima$tempmin~clima$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = clima)
boxplot(climaDiff$amplitude~climaDiff$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = climaDiff)
boxplot(grupo15$valor~grupo15$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = grupo15)

ggplot(produto210, aes(x=semanaano, y=valor)) + geom_point()

linha = lm(produto210$valor ~ produto210$semanaano, data=produto210)
boxplot(linha)

hist(produto210$semanaano)
hist(dolar2019$semanaano)
hist(clima2019$semanaano)
hist(grupo15$semanaano)

boxplot(proDia$valor~proDia$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = proDia)
boxplot(dolar$valor~dolar$semanaano, xlab = 'dia', ylab='valor', main='Tabela',data = dolar)

summary(proDia)

linha = lm(proDia$valor ~ proDia$dia, data=proDia)

plot(linha)

View(proDia)

hist(proDia)

tabelaDia = aggregate(x = produtos$valor, by = list(produtos$semanaano), FUN = mean)



boxplot(tabelaDia$x~tabelaDia$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaDia)
plot(tabelaDia$x~tabelaDia$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaDia)

tabelaBairro = aggregate(x = produtos$valor, by = list(produtos$grupo), FUN = mean)

boxplot(tabelaBairro$x~tabelaBairro$Group.1, xlab = 'dia', ylab='valor', main='Tabela',data = tabelaBairro)

print(tabela)
plot(tabela)

linha = lm(tabelaDia$Group.1 ~tabelaDia$x, data=tabelaDia)

boxplot(linha)