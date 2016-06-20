using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.tutor;

namespace TutorN5.Models.reis.aprendizagem.aulas
{
    public class AulaTextoViewModel
    {
        public AulaTextoViewModel()
        {

        }
        public AulaTextoViewModel(String Titulo, String Paragrafo, String Curiosidade, String Fala, int idAula, int idRei)
        {
            this.Titulo = Titulo;
            this.Paragrafo = Paragrafo;
            this.Curiosidade = Curiosidade;
            this.Fala = Fala;
            this.IdAula = idAula;
            this.IdRei = idRei;
        }
        public String Titulo { get; set; }
        public String Paragrafo { get; set; }
        public String Curiosidade { get; set; }
        public String Fala { get; set; }
        public String FonteExtra { get; set; }
        public int IdAula { get; set; }
        public int IdRei { get; set; }
    }
}