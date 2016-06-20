using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections;
using System.Text.RegularExpressions;
using System.Text;

namespace TutorN5.Models.reis.aprendizagem.exercicios
{
    /// <summary>
    /// Classe que representa um tipo de exercícios, do tipo de preencher espaços em branco de um texto fonte,
    /// que o utilizador deverá preencher.
    /// </summary>
    public class PreencherEspacos
    {

        public static string TIPO = "PREENCHER_ESPACOS";
        public static string CHAVE = "PALAVRA_CHAVE_";

        public int Id { get; set; }

        public ArrayList Linhas { get; set; }
        public ArrayList TextoPronto { get; set; }
        public Dictionary<int, string> Respostas { get; set; }

        public PreencherEspacos(int Id)
        {
            this.Id = Id;
            Linhas = new ArrayList();
            TextoPronto = new ArrayList();
            Respostas = new Dictionary<int, string>();
        }

        public void parseFich(string ficheiro)
        {
            int counter = 0;
            string line;

            // Read the file and display it line by line.
            System.IO.StreamReader file = new System.IO.StreamReader(ficheiro);
            while ((line = file.ReadLine()) != null)
            {
                Linhas.Add(line);
                counter++;
            }

            file.Close();
        }
        
        /// <summary>
        /// Método que identifica e mapeia as palavras chave num dicionário da instância.
        /// </summary>
        public void storeKeyWords()
        {
            Regex regex = new Regex(@"(\$)[A-Za-z0-9]+(\$)");
            int contaPerguntas = 0;

            foreach (String s in Linhas)
            {
                Match match = Regex.Match(s, @"\$[A-Za-z0-9 \.\,\:\-]+\$", RegexOptions.Compiled);

                if (match.Success)
                {
                    String key = match.Value.Substring(1, match.Value.Length - 2);

                    // Temos em key uma nova palavra chave
                    Respostas.Add(contaPerguntas, key);
                    contaPerguntas++;
                }
            }
        }

        /// <summary>
        /// Método que substitui palavras chave por dropdown lists do HTML.
        /// </summary>
        public void replaceKeyWords()
        {
            Regex regex = new Regex(@"(\$)[A-Za-z0-9]+(\$)");
            int contaPerguntas = 0;

            foreach (String s in Linhas)
            {
                Match match = Regex.Match(s, @"\$[A-Za-z0-9 \.\,\:\-]+\$", RegexOptions.Compiled);

                if (match.Success)
                {
                    String key = match.Value.Substring(1, match.Value.Length - 2);

                    StringBuilder sb = new StringBuilder();

                    sb.Append(" <select id=\"").Append(CHAVE+contaPerguntas).Append("\">\n");
                    foreach (string resp in Respostas.Values)
                    {
                        sb.Append("<option value=\"").Append(resp.ToLower()).Append("\">").Append(resp).Append("</option>\n");
                    }
                    sb.Append("</select>\n ");

                    // Temos uma nova linha
                    String newLine = Regex.Replace(s, @"\$[A-Za-z0-9 \.\,\:\-]+\$", sb.ToString());
                    contaPerguntas++;

                    // Temos uma nova linha
                    TextoPronto.Add(newLine);
                }
            }
        }

        /// <summary>
        /// Método herdado de implementação obrigatória que valida uma resposta sendo
        /// fornecidos os parâmetros de entrada pergunta e respetiva resposta dada.
        /// </summary>
        /// <param name="perg">Identificador do campo/espaço em branco </param>
        /// <param name="resp">String com a resposta escolhida pelo utilizador</param>
        /// <returns></returns>
        public bool validaResposta(string solucao, string resp)
        {
            return String.Equals(solucao.ToLower(), resp.ToLower(), StringComparison.Ordinal);
        }

        /// <summary>
        /// Dado uma chave/identificador de campo do tipo PALAVRA_CHAVE_1, devolve a resposta mapeada
        /// para a chave 1 na coleção de respostas.
        /// </summary>
        /// <param name="chave">Nome do campo ou identificador sobre o qual se quer obter resposta</param>
        /// <returns></returns>
        private string getResposta(int chave)
        {
            string resp = this.Respostas.ElementAt(chave).Value;

            if (resp != null) return resp;
            else return "";
        }

        public int getNumeroDeAlineas() { return Respostas.Count();  }

        public string getTextoSolucao()
        {
            Regex regex = new Regex(@"(\$)[A-Za-z0-9]+(\$)");
            int contaPerguntas = 0;
            StringBuilder sb = new StringBuilder();

            foreach (String s in Linhas)
            {
                Match match = Regex.Match(s, @"\$[A-Za-z0-9 \.\,\:\-]+\$", RegexOptions.Compiled);

                if (match.Success)
                {
                    String key = match.Value.Substring(1, match.Value.Length - 2);

                    // Temos uma nova linha
                    String newLine = Regex.Replace(s, @"\$[A-Za-z0-9 \.\,\:\-]+\$", "<b style=\"font-size: 22px;\">"+Respostas[contaPerguntas]+"</b>");
                    contaPerguntas++;

                    // Temos uma nova linha
                    sb.Append(newLine);
                }
            }

            return sb.ToString();
        }
    }
}