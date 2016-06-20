using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TutorN5.Models.reis.aprendizagem.aulas
{
    public class AulaTextoOrganizer
    {
        //Tabelas da base de dados:
        //- Aula
        //- Fonte
        //- Rei
        //- UtizadorAula

        public AulaDAO aulaDAO { get; set; }
        public ReiDAO kingDAO { get; set; }
        public UtilizadorAulaDAO uaDAO { get; set; }

        public AulaTexto aulaTexto { get; set; }
        public Aula estaAula { get; set; }
        public Dictionary<int, string> paragrafos { get; set; }
        public Dictionary<int, List<String>> falasTutor { get; set; }
        public Dictionary<int, string> curiosidades { get; set; }

        public String fonteExtra { get; set; }

        public int paragrafoAtual { get; set; }

        public String curiosidadeAtualString { get; set; }

        public String paragrafoAtualString { get; set; }
        public int saveParagrafoAtual { get; set; }

        public Dictionary<int,int> indiceDaFalaAtual { get; set; }

        public String falaAtual { get; set; }

        public AulaTextoOrganizer(AulaDAO a, ReiDAO r, UtilizadorAulaDAO ua, string fonte, int idAula) {
            aulaDAO = a;
            kingDAO = r;
            uaDAO = ua;

            fonteExtra = fonte;
            estaAula = aulaDAO.Aula.Find(idAula);

            aulaTexto = new AulaTexto(estaAula.Conteudo); // Parse do ficheiro que contém a aula
            paragrafos = aulaTexto.paragrafo;
            falasTutor = aulaTexto.falasTutor;
            curiosidades = aulaTexto.curiosidades;

            paragrafoAtual = 0;
            saveParagrafoAtual = -1;
            indiceDaFalaAtual = new Dictionary<int, int>();

            int i = 0;
            foreach (String s in paragrafos.Values)
            {
                indiceDaFalaAtual.Add(i, 0);
                i++;
            }
        }

        public String getTituloDaAula() { return estaAula.Titulo; }

        public string getParagrafoAtual(int par) { return paragrafos.ElementAt(par).Value; }

        public string getCuriosidadeAtual(int par) { return curiosidades.ElementAt(par).Value; }

        public string getFalaAtual(int par) {
            if (indiceDaFalaAtual.ElementAt(par).Value == 2)
            {
                indiceDaFalaAtual.Remove(par);
                indiceDaFalaAtual.Add(par, 0);
            }
            else
            {
                int x = indiceDaFalaAtual.ElementAt(par).Value + 1;
                indiceDaFalaAtual.Remove(par);
                indiceDaFalaAtual.Add(par, x);
            }
            return falasTutor.ElementAt(par).Value.ElementAt(indiceDaFalaAtual.ElementAt(par).Value);
        }
    }
}