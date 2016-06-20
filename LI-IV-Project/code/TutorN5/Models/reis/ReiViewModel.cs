using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TutorN5.Models.reis
{
    public class ReiViewModel
    {
        public int Id { get; set; }
        public string Biografia { get; set; }
        public DateTime DataNascimento { get; set; }
        public DateTime DataMorte { get; set; }
        public string Nome { get; set; }
        public string Imagem { get; set; }
        public int BioEstado { get; set; }

        public Dictionary<int,String> ParagrafosBiografia;
        public ReiViewModel(Rei r, int bioest)
        {
            Id = r.Id;
            Biografia = r.Biografia;
            DataNascimento = (DateTime)r.DataNascimento;
            DataMorte = (DateTime)r.DataMorte;
            Nome = r.Nome;
            Imagem = r.Imagem;
            BioEstado = bioest;
            ParagrafosBiografia = new Dictionary<int, string>();
            parseBiografia();
        }

        private void parseBiografia()
        {
            string line;
            char[] delimiterChars1 = { ':' };
            char[] delimiterChars2 = { '.' };
            List<String> lista = new List<String>();

            int BIO_ESTADO_INDEX = 0;

            // Read the file and display it line by line.
            System.IO.StreamReader file = new System.IO.StreamReader(Biografia);
            while ((line = file.ReadLine()) != null && BIO_ESTADO_INDEX < BioEstado)
            {
                string[] words = line.Split(delimiterChars1);

                if (words[0].Contains("P"))
                {
                    string[] numbers = words[0].Split(delimiterChars2);
                    this.ParagrafosBiografia.Add(Int32.Parse(numbers[0]), words[2]);
                }

                BIO_ESTADO_INDEX++;
            }
            file.Close();
        }
    }
}