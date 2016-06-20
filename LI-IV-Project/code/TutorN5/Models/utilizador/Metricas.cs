using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TutorN5.Models.utilizador
{
    public class Metricas
    {
        public static int TE { get; set; } // Total de Exercícios
        public static int TA { get; set; } // Total de Aulas
        public static int TR { get; set; } // Total de Reis
        public static int TER { get; set; }  // TER Total de exercicios resolvidos
        public static int TAA { get; set; } // TAA Total de aulas assistidas

        // TAA Total de aulas assistidas
        // TER Total de exercicios resolvidos
        public static int getPGeral() { return ((TER / TE * 60 + (TAA / TA) * 40)); }
    }
}