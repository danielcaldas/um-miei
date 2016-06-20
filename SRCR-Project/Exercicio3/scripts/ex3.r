library("neuralnet")
library("hydroGOF")

dataset <- read.csv("D:\\Dropbox\\SRCR\\Ex3_aleatorio.csv",header=TRUE,sep=";",dec=".")

teste <- dataset[15:20,]
teste2 <- dataset[550:560,]
teste3 <- dataset[440:450,]
teste4 <- dataset[236:348,]
teste5 <- dataset[60:90,]
teste6 <- dataset[700:800,]

total <- rbind(teste,teste2)
total_2 <- rbind (total, teste3)
total_3 <- rbind (total_2, teste4)
total_4 <- rbind (total_3, teste5)
total_5 <- rbind (total_4, teste6)

# treinar rede neuronal
fadiganet <- neuralnet(FatigueLevel ~ Performance.KDTMean+Performance.MAMean+Performance.MVMean+Performance.TBCMean+Performance.DDCMean+Performance.DMSMean+Performance.AEDMean+Performance.ADMSLMean+Performance.Task, dataset, hidden = c(6,5), threshold = 0.001)

# imprimir resultados
plot(fadiganet)

temp_teste  <-subset (total_5, select = c(Performance.KDTMean,Performance.MAMean,Performance.MVMean,Performance.TBCMean, Performance.DDCMean,Performance.DMSMean ,Performance.AEDMean, Performance.ADMSLMean, Performance.Task))

fadiganet.results <- compute(fadiganet, temp_teste)

res <- data.frame(actual = total_5$FatigueLevel, prediction = fadiganet.results$net.result)

#### Nova coluna (Fatigado ou não)
ex <- (fadiganet.results$net.result)

dd <- matrix(,nrow(ex))

tmp <- match(rownames(ex), rownames(dd))
st <- cbind( ex, dd[tmp,] )


for (i in 1:nrow(ex)) {
  if (ex[i,]<5) st[i,2]<-0 else  st[i,2]<-1 }
########



rmse(c(total_5$FatigueLevel),c(res$prediction))

