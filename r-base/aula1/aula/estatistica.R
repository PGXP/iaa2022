install.packages("tidyverse")
install.packages("ggplot2")
install.packages("fdth")
install.packages("RcmdrMisc")
install.packages("readxl")
install.packages("plotly")
install.packages("tigerstats")
install.packages("BSDA")
install.packages("stats")
install.packages("car")
install.packages("carData")

if(!require(car)) install.packages("car") 
library(car)
library(tidyverse)

library(RcmdrMisc)
library(fdth)
library(readxl)
library(plotly)
library(tidyverse)
library(tigerstats)
library(BSDA)
library(stats)
library(car)
library(carData)

produtos <- read.csv("~/Documentos/produtos.csv", sep=";")
dolar <- read.csv("~/Documentos/dolar.csv", sep=";")

qnorm(0.025)

dolar2019 <- dolar[which(dolar$ano == 2014),names(dolar)%in% c("diaano","valor")]

produto210 <- produtos[which(produtos$produto==475&produtos$ano==2014),names(produtos) %in% c("diaano","valor")]

produtoAno <- produtos[which(produtos$produto==475),names(produtos) %in% c("ano","valor")]

prow <- nrow(produto210)
pmean <- mean(produto210$valor)
pmedian <- median(produto210$valor)
psd <- sd(produto210$valor)

# O coeficiente de variação
pcv <- (psd/pmean)*100

pam <- psd/sqrt(prow)

var(produto210)

qqPlot(produto210$valor)
shapiro.test(produto210$valor)

?var.test

resultado_teste_f <- var.test(produto210$valor ~ produto210$diaano , data = produto210)

z.test(produto210$valor, y = NULL, alternative = "two.sided", mu = 0, sigma.x = psd,
       sigma.y = NULL, conf.level = 0.95)

boxplot(produto210$valor~produto210$diaano, xlab = 'dia', ylab='valor', main='Tabela',data = produto210)
boxplot(dolar2019$valor~dolar2019$diaano, xlab = 'dia', ylab='valor', main='Tabela',data = dolar2019)
boxplot(produtoAno$valor~produtoAno$ano, xlab = 'dia', ylab='valor', main='Tabela',data = produtoAno)
boxplot(dolar$valor~dolar$diaano, xlab = 'ano', ylab='valor', main='Tabela',data = dolar)
