install.packages("tidyverse")
install.packages("ggplot2")

produtos <- read.csv("~/Documentos/produtos.csv", sep=";")
dolar <- read.csv("~/Documentos/dolar.csv", sep=";")

dolar2019 <- dolar[which(dolar$ano == 2014),names(dolar)%in% c("diaano","valor")]
produto210 <- produtos[which(produtos$produto==475),names(produtos) %in% c("diaano","valor")]

produtoAno <- produtos[which(produtos$produto==475),names(produtos) %in% c("ano","valor")]

pmean <- mean(produto210$valor)
pmed <- median(produto210$valor)
range(produto210$valor)
psd <- sd(produto210$valor)
var(produto210$valor)


boxplot(produto210$valor~produto210$diaano, xlab = 'dia', ylab='valor', main='Tabela',data = produto210)
boxplot(dolar2019$valor~dolar2019$diaano, xlab = 'dia', ylab='valor', main='Tabela',data = dolar2019)

boxplot(produtoAno$valor~produtoAno$ano, xlab = 'dia', ylab='valor', main='Tabela',data = produtoAno)
boxplot(dolar$valor~dolar$diaano, xlab = 'ano', ylab='valor', main='Tabela',data = dolar)
