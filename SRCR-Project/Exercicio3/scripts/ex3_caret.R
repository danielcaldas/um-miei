#install.packages("devtools")
#install.packages("nnet")
#install.packages("ROCR")
#install.packages("caret")
#install.packages("car")
#install.packages("mlpWeightDecay")
#install.packages("scales")
library("neuralnet")
library("hydroGOF")
library("nnet")
library("ROCR")
library("car")
library("caret")
library("devtools")

# ler ficheiro 5.1,3.5,1.4,0.2,Iris-setosa
dataset <- read.csv("D:\\Dropbox\\SRCR\\Exercicio3\\Ex3_aleatorio_2.csv"
                    ,header=TRUE,sep=",",dec=".")
#trainset <- read.csv("D:\\Dropbox\\SRCR\\Exercicio3\\nnet\\iris.txt",header=TRUE,sep=",",dec=".")


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
fadiganet = nnet(FatigueLevel~., data=trainset,size=6,maxit=10000,decay=.001)

source_url('https://gist.githubusercontent.com/fawda123/7471137/raw/466c1474d0a505ff044412703516c34f1a4684a5/nnet_plot_update.r')
plot.nnet(fadiganet)

my.grid <- expand.grid(.size = 6, .decay = c(0.1))
fnet <- train(FatigueLevel ~ Performance.KDTMean + Performance.MAMean+Performance.MVMean+Performance.TBCMean+Performance.DDCMean+Performance.DMSMean+
                      Performance.AEDMean+Performance.ADMSLMean+Performance.Task, data = trainset,
                      method = "pcaNNet", maxit = 1000, tuneGrid = my.grid, trace = F, linout = 1) 

plot.nnet(fnet)
plot(fnet)


fnet.predict <- predict(fnet, newdata = total)
prestige.rmse <- sqrt(mean((fnet.predict - total$FatigueLevel)^2)) 