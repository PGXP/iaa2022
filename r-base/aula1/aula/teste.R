produtos <- read.csv("~/Documentos/produtos.csv", sep=";")

View(produtos)

tabela = aggregate(x = produtos$valor, by = list(produtos$dia), FUN = mean)
print(tabela)
plot(tabela)

linha = lm(tabela$Group.1~tabela$x, data=tabela)

plot(linha)