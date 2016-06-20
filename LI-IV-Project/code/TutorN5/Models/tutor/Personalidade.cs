using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;

namespace TutorN5.Models.tutor
{
    public class Personalidade
    {
        // Esta fala é alterada dinamicamenta, é usada para casos críticos de comunicação como por exemplo
        // comunicar a fala de uma classe ViewModel para o HomeController (caso crítico de troca de dados entre camadas)
        public static String FALA_ATUAL = null;

        public static int PAREDE_IDADE = 12; // Diferenciar discurso para maiores de 12 e menores de 12 anos
        public static String PERFIL = "Este é o teu perfil onde podes aceder às diversas áreas de aprendizagem. Clica no teu nome sempre que quiseres regressar aqui. Podes explorar os vários reis na barra do tempo, experimenta.";
        public static String PERFIL_DO_REI_I = "Este é o perfil de ";
        public static String PERFIL_DO_REI_II = ", aqui podes aceder às aulas e exercícios deste rei.";
        public static String ESTATISTICAS = "Aqui podes consultar as tuas estatísticas, os gráficos mostram-te quantos exercícios e respostas certas foste acumulando...";
        public static String HISTORICO = "Todos os teus passos ficam registados neste histórico por ordem cronológica";

        public static String NAO_ME_ENGANAS = "Não escreveste nada, pensas que me engánas? Se não queres responder, clica no botão ignorar.";
        public static String FIM_DE_AULA = "A aula terminou, já sabes mais acerca deste rei como podes ver pela biografia, tenta fazer alguns exercícios.";
        public static String MUITAS_DUVIDAS = "Parece que estás com muitas dúvidas, tenta a aula de vídeo, ou tenta resolver exercícios";

        public static String APRESENTACAO = "Olá, eu sou o Muralhas, o teu tutor. Sou eu que vou acompanhar o teu estudo ao longo das várias batalhas. ";
        public static String DEFINICOES = "Esta é a tua página de definições pessoais, onde podes escolher se queres que seja muito interventivo, ou se queres que participe menos nas tuas aventuras. Para isso tens de escolher alto, ou baixo, no campo modo de intératividade.";

        public static Boolean PRIMEIRA_AULA = false;
        public static Boolean PRIMEIRO_EXERCICIO = false;

        /// <summary>
        /// Variáveis de instância para acerdermos a utilizador com sessão iniciada, são estes
        /// dados entre outro que vão provocar alterações no comportamento do avatar.
        /// </summary>
        private int idade;
        private string nome;

        public Personalidade()
        {

        }
        public Personalidade(string nome, int idade)
        {
            this.nome = nome;
            this.idade = idade;
        }

        /// <summary>
        /// Devolver mensagem de fim de exercício em função de sucesso ou insucesso dado pelo parâmetro passado,
        /// entre outros.
        /// </summary>
        /// <param name="percentagem">Resultado do exercicio</param>
        /// <returns></returns>
        public string getFalaResultadoDeExercicio(int percentagem)
        {
            StringBuilder sb = new StringBuilder();
            Random rnd = new Random();

            if (percentagem < 10)
            {
                int z = rnd.Next(0, 3);
                switch (z)
                {
                    case 0:
                        if (idade > PAREDE_IDADE)
                        {
                            sb.Append("Com ").Append(idade).Append(" anos, já não são admissíveis resultados tão baixos.");
                        }
                        else
                        {
                            sb.Append("Não correu nada bem ").Append(nome).Append(", aconselho-te a estudar mais um bocado.");
                        }
                        break;
                    case 1:
                        sb.Append("Então que se passou?");
                        break;
                    case 2:
                        sb.Append("Que resultado baixo. Está tudo bem contigo ").Append(nome).Append("?");
                        break;
                }
            }
            else if (percentagem > 10 && percentagem <= 40)
            {
                int z = rnd.Next(0, 3);
                switch (z)
                {
                    case 0:
                        sb.Append("Não desistas ").Append(nome).Append(", juntos vamos levantar este resultado.");
                        break;
                    case 1:
                        sb.Append("Acho que senti desconcentração.");
                        break;
                    case 2:
                        sb.Append("Aconselho-te seriamente a estudar.");
                        break;
                }
            }
            else if (percentagem > 40 && percentagem < 50)
            {
                int z = rnd.Next(0, 2);
                switch (z)
                {
                    case 0:
                        sb.Append("Ficaste a um danoninho de tirar positiva.");
                        break;
                    case 1:
                        sb.Append(nome).Append(" ,podia-te dar mais uns pontinhos só para teres positiva, mas fui programado para justo e imparcial.");
                        break;
                }
            }
            else if (percentagem > 50 && percentagem <= 75)
            {
                int z = rnd.Next(0, 3);
                switch (z)
                {
                    case 0:
                        sb.Append("Queres a opinião do Muralhas? Ainda podes melhorar muito.");
                        break;
                    case 1:
                        sb.Append("Muito bem, continua assim.");
                        break;
                    case 2:
                        sb.Append("Bom resultado.");
                        break;
                }
            }
            else if (percentagem > 70 && percentagem < 100)
            {
                int z = rnd.Next(0, 3);
                switch (z)
                {
                    case 0:
                        sb.Append("Muito bom resultado.");
                        break;
                    case 1:
                        sb.Append("Parabéns ").Append(nome).Append(". Acho que podes avançar para a matéria seguinte, sem problemas.");
                        break;
                    case 2:
                        sb.Append("Como tutor, não podia estar mais orgulhoso");
                        break;
                }
            }
            else if (percentagem == 100)
            {
                int z = rnd.Next(0, 3);
                switch (z)
                {
                    case 0:
                        if (idade > PAREDE_IDADE)
                        {
                            sb.Append("Olá eu sou o Muralhas, posso aprender História de Portugal contigo?");
                        }
                        else
                        {
                            sb.Append("Começo a ficar com medo que me tires o lugar de professor.");
                        }
                        break;
                    case 1:
                        sb.Append("Afinal quem é que sabe de História sou eu, ou és tu?");
                        break;
                    case 2:
                        sb.Append("Os teus antepassados, iriam ficar orgulhosos por este teu grande feito.");
                        break;
                }
            }

            return sb.ToString();
        }

        public static String getDuvida()
        {
            Random rnd = new Random();
            int z = rnd.Next(0, 3);

            switch (z)
            {
                case 0:
                    return "Parece que estás com dúvidas, clica no texto em que tens dúvida para eu te explicar outra vez...";
                    break;
                case 1:
                    return "Vamos lá tirar essas dúvidas.. clica no texto que não percebeste do lado esquerdo para te poder ajudar";
                    break;
                case 2:
                    return "Quero que percebas bem a história, vamos lá,  clica no texto em que tens dúvida para eu te explicar outra vez...";
                    break;
            }
            return ("Clica no texto do lado esquerdo para te poder ajudar...");
        }

        public static String getPickExercicio()
        {

            if (PRIMEIRO_EXERCICIO == false)
            {
                Random rnd = new Random();
                int z = rnd.Next(0, 3);

                switch (z)
                {
                    case 0:
                        return "Então, de que estás à espera, escolhe um exercício.";
                        break;
                    case 1:
                        return "Vamos lá praticar!";
                        break;
                    case 2:
                        return "É desta que vais tirar cem por cento.";
                        break;
                }
                return ("Clica num exercício");
            }
            else
            {
                PRIMEIRO_EXERCICIO = false;
                return "Aqui tens a lista de exercícios disponíveis, podes ver nesta lista o tipo de exercício, a sua dificuldade e a aula a que diz respeito. Quando estiveres preparado clica em começar.";
            }
        }

        public static String getPickAula()
        {
            if(PRIMEIRA_AULA==false){
                Random rnd = new Random();
                int z = rnd.Next(0, 3);

                switch (z)
                {
                    case 0:
                        return "Então, de que estás à espera, escolhe uma aula.";
                        break;
                    case 1:
                        return "Está um bom dia para aprender";
                        break;
                    case 2:
                        return "Escolhe uma das aulas disponíveis, eu não tenho a tua vida";
                        break;
                }
                return ("É só escolher um aula");
            }
            else
            {
                PRIMEIRA_AULA = false;
                return "Aqui tens a lista de aulas. Elas estão ordenadas por ordem histórica, por isso aconselho-te a começar logo pela primeira.";
            }
        }

    }
}