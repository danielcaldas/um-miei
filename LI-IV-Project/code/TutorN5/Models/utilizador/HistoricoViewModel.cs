using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.reis.aprendizagem.exercicios;
using TutorN5.Models.reis.aprendizagem.aulas;
using TutorN5.Models.reis;
using System.Text;

namespace TutorN5.Models.utilizador
{
    public class HistoricoViewModel
    {
        //Acesso às tabelas:
        //- UtilizadorExercicio
        //- SessaoAvaliacao
        //- UtilizadorAula

        public ExercicioDAO exDAO { get; set; }
        public UtilizadorExercicioDAO ueDAO { get; set; }
        public AulaDAO aulaDAO { get; set; }
        public UtilizadorAulaDAO uaDAO { get; set; }
        public ReiDAO reiDAO { get; set; }

        public List<String> EventosOrdenadosPorData;

        public int userId { get; set; }

        public HistoricoViewModel(ExercicioDAO exDAO, AulaDAO aDAO, UtilizadorAulaDAO uaDAO, UtilizadorExercicioDAO ueDAO, ReiDAO reiDAO, int userId)
        {
            this.exDAO = exDAO;
            this.uaDAO = uaDAO;
            this.ueDAO = ueDAO;
            this.reiDAO = reiDAO;
            this.userId = userId;
            this.aulaDAO = aDAO;

            EventosOrdenadosPorData = new List<String>();

            Dictionary<DateTime, String> aux = new Dictionary<DateTime, string>();

            // Construír tabela de eventos relativos a exercícios para imprimir na página do histórico
            foreach (UtilizadorExercicio ue in ueDAO.UtilizadorExercicio)
            {
                if (ue.Utilizador == userId)
                {
                    DateTime dateaux = (DateTime)ue.Data;
                    StringBuilder descricaoEvento = new StringBuilder();

                    String nomeRei = (String)reiDAO.Rei.Find(exDAO.Exercicio.Find(ue.Exercicio).Rei).Nome; // Nome do rei a que diz respeito o exercício
                    int percentagem = (((int)ue.AlineasCorretas * 100) / (int)exDAO.Exercicio.Find(ue.Exercicio).NumeroAlineas); // Pontos no exercício

                    DateTime daux = (DateTime)ue.Data;
                    String data = (daux.Day + " de " + getNomeDoMes(daux.Month) + " de " + daux.Year + ", " + daux.Hour + ":" + daux.Minute + ":" + daux.Second);

                    descricaoEvento.Append(data).Append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp ");
                    descricaoEvento.Append("Fizeste um exercício sobre <b>").Append(nomeRei).Append("</b> ");
                    descricaoEvento.Append(", o teu resultado foi de ").Append(percentagem).Append("%");

                    aux.Add((DateTime)ue.Data, descricaoEvento.ToString());
                }
            }

            // Construír tabela de eventos relativos a aulas para imprimir na página do histórico
            foreach (UtilizadorAula ua in uaDAO.UtilizadorAula)
            {
                if (ua.Utilizador == userId)
                {
                    DateTime dateaux = (DateTime)ua.Data;
                    StringBuilder descricaoEvento = new StringBuilder();

                    var aulavar = aulaDAO.Aula.Where(aula => aula.Id == ua.Aula).ToList();

                    // Se conseguirmos extrair a respetiva aula da base de dados
                    if (aulavar.Count() > 0)
                    {
                        Aula aulaux = aulavar.ElementAt(0);

                        String nomeRei = (String)reiDAO.Rei.Find(aulaux.Rei).Nome; // Nome do rei a que diz respeito o exercício
                        String nomeAula = aulaux.Titulo;

                        DateTime daux = (DateTime)ua.Data;
                        String data = (daux.Day + " de " + getNomeDoMes(daux.Month) + " de " + daux.Year + ", " + daux.Hour + ":" + daux.Minute + ":" + daux.Second);

                        descricaoEvento.Append(data).Append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp ");
                        descricaoEvento.Append("Assististe à aula <b>").Append("\"").Append(nomeAula).Append("\"</b> ")
                            .Append(" sobre <b>").Append(nomeRei).Append("</b> ");

                        aux.Add((DateTime)ua.Data, descricaoEvento.ToString());
                    }
                }
            }

            if (aux.Count > 0)
            {
                var listevents = aux.Values.OrderByDescending(data => data).ToList();

                foreach (String desc in listevents)
                {
                    EventosOrdenadosPorData.Add(desc);
                }
            }
        }

        public String getNomeDoMes(int n)
        {
            switch (n)
            {
                case 1: return "Janeiro";
                case 2: return "Fevereiro";
                case 3: return "Março";
                case 4: return "Abril";
                case 5: return "Maio";
                case 6: return "Junho";
                case 7: return "Julho";
                case 8: return "Agosto";
                case 9: return "Setembro";
                case 10: return "Outubro";
                case 11: return "Novembro";
                case 12: return "Dezembro";
                default: return "ndef";
            }
        }
    }
}