using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using TutorN5.Models.reis.aprendizagem.exercicios;
using TutorN5.Models.reis;

namespace TutorN5.Models.utilizador
{
    public class EstatisticaViewModel
    {
        public int TotalAlineasCorretas { get; set; }
        public int TotalAlineasErradas { get; set; }
        public int TotalExerciciosNegativos { get; set; }
        public int TotalExerciciosPositivos { get; set; }
        
        public EstatisticaViewModel(Utilizador u)
        {
            TotalAlineasCorretas = (int)u.TotalAlineasCorretas;
            TotalAlineasErradas = (int)((int)u.TotalAlineas - (int)u.TotalAlineasCorretas);
            TotalExerciciosPositivos = (int)u.ExerciciosPositivos;
            TotalExerciciosNegativos = (int)((int)u.TotalExercicios - (int)u.ExerciciosPositivos);
        }

    }
}