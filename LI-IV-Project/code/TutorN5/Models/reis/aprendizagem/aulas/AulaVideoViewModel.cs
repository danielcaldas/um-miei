using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.tutor;

namespace TutorN5.Models.reis.aprendizagem.aulas
{
    public class AulaVideoViewModel
    {
        public Aula aula { get; set; }
        public PerguntaDAO pergDAO { get; set; }

        public int SuperInteratividade { get; set; } // 1 - Activado, 0 - Desactivado
        public Dictionary<int, String> FontesExtra { get; set; } // A cada questão associar uma fonte extra

        public int PerguntaIndex { get; set; }

        public Personalidade avatar { get; set; }

        public String QuestaoAColocar { get; set; }


        // Mapas <TimeStamp,String> ordenados por time stamp
        public List<String> QuestoesPorOrdemTempo { get; set; }
        public List<String> RespostasPorOrdemTempo { get; set; }

        public List<String> FontesExtraPorOrdemTempo { get; set; }

        // Map de <0..NTimeStamps,ValorDoTimeStamp>
        public List<int> TimeStampsIndexados { get; set; }

        public int previousTimeStamp { get; set; }
        public AulaVideoViewModel(Aula aula, PerguntaDAO pergDAO, int superInt, Personalidade pe)
        {
            this.aula = aula;
            this.pergDAO = new PerguntaDAO();
            this.SuperInteratividade = superInt;
            this.avatar = pe;
            PerguntaIndex = 0;
            QuestaoAColocar = "";

            int idAula = aula.Id;
            previousTimeStamp = 0;

            QuestoesPorOrdemTempo = new List<string>();
            RespostasPorOrdemTempo = new List<string>();
            FontesExtraPorOrdemTempo = new List<string>();
            TimeStampsIndexados = new List<int>();

            // Calcular questões desta aula de vídeo
            foreach (var pergunta in pergDAO.Pergunta)
            {
                if (pergunta.Aula == idAula)
                {
                    QuestoesPorOrdemTempo.Add(pergunta.Pergunta1);
                    RespostasPorOrdemTempo.Add(pergunta.Resposta);
                    FontesExtraPorOrdemTempo.Add(pergunta.FonteExtra);
                    TimeStampsIndexados.Add((int)pergunta.TimeStamp);
                }
            }
        }

        public int VerificaResposta(int tempo, String respUser)
        {
            String respCorreta = RespostasPorOrdemTempo[tempo];

            if ( respUser.ToLower().Contains( respCorreta.ToLower() ) )
            {
                Personalidade.FALA_ATUAL = "Acertas na pergunta, muito bem"; // Lógica da Fala + Fontes etc. ...
                return 1; // Verdadeiro
            }
            else
            {
                Personalidade.FALA_ATUAL = "Que pena, erras-te";
                return 0; // Falso
            }
        }

        public int getFirstTimeStamp()
        {
            previousTimeStamp = 0;
            QuestaoAColocar = QuestoesPorOrdemTempo[0];

            return TimeStampsIndexados[0];
        }

        public int getTimeStampProximaPergunta()
        {
            if (PerguntaIndex + 1 < TimeStampsIndexados.Count)
            {
                // Guardar timestamp anterior
                previousTimeStamp = TimeStampsIndexados[PerguntaIndex];

                // Avançar para a próxima pergunta
                PerguntaIndex++;

                // Calcular valor do próximo time stamp
                int ts = TimeStampsIndexados[PerguntaIndex];

                // Calcular a próxima questão a ser colocada
                QuestaoAColocar = QuestoesPorOrdemTempo[PerguntaIndex];

                return ts;
            }
            else
            {
                return 0;
            }
        }

        public String getProximaPergunta()
        {
            return QuestoesPorOrdemTempo[PerguntaIndex];
        }

        public int naoMeEnganas()
        {
            Personalidade.FALA_ATUAL = Personalidade.NAO_ME_ENGANAS;
            return 0;
        }

        public int getPreviousTimeStamp()
        {
            return previousTimeStamp;
        }
    }
}