using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace TutorN5.Models.reis.aprendizagem.exercicios
{
    public class RespExercicioViewModel
    {

        public int Id { get; set; }
        public string[] Respostas { get; set; }
        public Dictionary<int, string> Solucoes { get; set; }

    }
}