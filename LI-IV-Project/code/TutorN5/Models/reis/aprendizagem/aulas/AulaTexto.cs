using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Collections;
using System.Text.RegularExpressions;
using System.Text;

namespace TutorN5.Models.reis.aprendizagem.aulas {

    public class AulaTexto {

        public string ficheiro { get; set; }
        public int Id { get; set; }
        public int falaAtual { get; set; }
        public Dictionary<int, string> paragrafo { get; set; }
        public Dictionary<int, List<String>> falasTutor { get; set; }
        public Dictionary<int, string> curiosidades { get; set; }

        public AulaTexto(string ficheiro) {
            this.ficheiro = ficheiro;
            paragrafo = new Dictionary<int, string>();
            curiosidades = new Dictionary<int, string>();
            falasTutor = new Dictionary<int, List<String>>();
            parseFichConteudo(ficheiro);
        }

        public void parseFichConteudo(string ficheiro)
        {
            Boolean flag = false;
            string line;
            char[] delimiterChars1 = { ':' };
            char[] delimiterChars2 = { '.' };
            int aux = -1;
            List<String> lista = new List<String>();
            int BIO_ESTADO_INDEX = 0;

            // Read the file and display it line by line.
            System.IO.StreamReader file = new System.IO.StreamReader(ficheiro);
            while ((line = file.ReadLine()) != null) {
                string[] words = line.Split(delimiterChars1);

                if (words[0].Contains("T")) {
                    string[] numbers = words[0].Split(delimiterChars2);
                    this.paragrafo.Add(Int32.Parse(numbers[0]), words[2]);
                }
                else if (words[0].Contains("C")) {
                    if (flag) {
                        this.falasTutor.Add(aux, lista);
                        aux = -1;
                        flag = false;
                    }
                    string[] numbers = words[0].Split(delimiterChars2);
                    this.curiosidades.Add(Int32.Parse(numbers[0]), words[2]);
                }
                else if (words[0].Contains("F")) {
                    string[] numbers = words[0].Split(delimiterChars2);
                    if (!flag) {
                        lista = new List<String>();
                        aux = Int32.Parse(numbers[0]);
                        flag = true;
                    }
                    lista.Add(words[2]);
                }
            }
            file.Close();
        }
    }
}
